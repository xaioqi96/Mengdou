package com.md.mq.product;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.OnExceptionContext;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.md.utils.SerializeUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ljx on 2017/3/9.
 */
public class MdProducer<T> extends ProducerBean {

    private static final Logger LOG = LoggerFactory.getLogger(MdProducer.class);
    private String topicPrefix;
    private String topic;


    public void sendAsync(T t) {
        final Message msg = createMessage(t);
        super.sendAsync(msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("向MQ队列中发送消息成功：{}", sendResult);
                }
            }

            @Override
            public void onException(OnExceptionContext context) {
                LOG.error("向阿里云MQ发送消息时出错", context.getException());
            }
        });
    }

    public void send(T t) {
        final Message msg = createMessage(t);
        SendResult sendResult = super.send(msg);
        if (LOG.isDebugEnabled()) {
            LOG.debug("向MQ队列中发送消息成功：{}", sendResult);
        }
    }

    private Message createMessage(T t) {
        if (topic == null || topic.trim().length() == 0) {
            topic = topicPrefix + t.getClass().getSimpleName();
        }

        String tags = t.getClass().getName();
        if (LOG.isDebugEnabled()) {
            LOG.debug("创建MQ消息,Topic：{},tags：{},消息内容：{}", topic, tags, ToStringBuilder.reflectionToString(t));
        }
        byte[] body = SerializeUtil.serialize(t);
        return new Message(topic, tags, body);
    }

    public String getTopicPrefix() {
        return topicPrefix;
    }

    public void setTopicPrefix(String topicPrefix) {
        this.topicPrefix = topicPrefix;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
