package com.md.mq.consumer.listener;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.md.mq.bean.TestMsg;
import com.md.utils.SerializeUtil;

/**
 * Created by ljx on 2017/3/14.
 */
public class DemoMessageListener implements MessageListener {

    public Action consume(Message message, ConsumeContext context) {
        System.out.println("Receive: " + message.getMsgID());
        try {


            String TestMsg = (String)SerializeUtil.unserialize(message.getBody());

            System.out.println("###################################################");
            System.out.println("[消费者2]"+message.getTag());
            System.out.println(TestMsg);

            System.out.println("###################################################");
            //do something..
            return Action.CommitMessage;
        }catch (Exception e) {
            //消费失败
            return Action.ReconsumeLater;
        }
    }
}
