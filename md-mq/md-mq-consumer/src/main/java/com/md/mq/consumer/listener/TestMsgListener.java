package com.md.mq.consumer.listener;

import com.md.mq.bean.TestMsg;
import com.md.mq.consumer.business.TestMsgBusiness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 测试消息队列
 * Created by ljx on 2017/3/10.
 */
@Component
public class TestMsgListener extends AbsMsgListener<TestMsg> {
    private final static Logger LOG = LoggerFactory.getLogger(TestMsgListener.class);
    @Resource
    private TestMsgBusiness testMsgBusiness;

    @Override
    protected boolean doHandler(TestMsg testMsg) {
        try {
            LOG.error("进入消费了");
            testMsgBusiness.doSomeThing(testMsg.getParamJson());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("消费测试异常", e);
        }
        return true;
    }
}
