package com.md.mq.consumer.listener;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.md.utils.SerializeUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ljx on 2017/3/10.
 */
public abstract class AbsMsgListener<T> implements MessageListener {
    private final static Logger LOG = LoggerFactory.getLogger(AbsMsgListener.class);

    /**
     * 处理业务逻辑
     *
     * @param t
     * @return
     */
    protected abstract boolean doHandler(T t);

    @Override
    public Action consume(Message message, ConsumeContext context) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("监听器接收到MQ消息：{}", message);
        }
        byte[] body = message.getBody();
        T t = (T) SerializeUtil.unserialize(body);
        if (LOG.isInfoEnabled()) {
            LOG.info("监听器接收到MQ消息内容：{}", ToStringBuilder.reflectionToString(t));
        }
        boolean result = doHandler(t);
        return result ? Action.CommitMessage : Action.ReconsumeLater;
    }
}
