package com.md.mq.consumer.listener;

import com.aliyun.openservices.ons.api.*;
import com.md.utils.SerializeUtil;
import java.util.Properties;

/**
 * Created by ljx on 2017/3/8.
 */
public class ConsumerClient {
    public static void main(String[] args) {
        Properties properties = new Properties();

        properties.put(PropertyKeyConst.ConsumerId, "CID_MD_002_TEST");
        properties.put(PropertyKeyConst.AccessKey, "LTAIJ647By1Do06c");
        properties.put(PropertyKeyConst.SecretKey, "vWGNbqxwsq5yl77liKyp312IuWW7e7 ");
        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe("test_md_002", "*", new MessageListener() {
            public Action consume(Message message, ConsumeContext context) {
                System.out.println("消息: " + message);

                System.out.println("消息Body: " + SerializeUtil.unserialize(message.getBody()));

                return Action.CommitMessage;
            }
        });
        consumer.start();
        System.out.println("Consumer Started");
    }
}
