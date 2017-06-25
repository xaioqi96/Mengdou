package com.md.mq;


import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.md.mq.consumer.listener.TestMsgListener;
import com.md.utils.SerializeUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;
import java.util.Set;

/**
 * Created by ljx on 2017/3/10.
 */

public class ConsumerTest {



    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring.xml");



        System.out.println("Consumer Started");
    }
}
