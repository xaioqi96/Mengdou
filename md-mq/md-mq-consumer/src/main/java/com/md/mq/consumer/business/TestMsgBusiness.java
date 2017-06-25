package com.md.mq.consumer.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * 消费者测试
 *
 * @date 2017年3月14日
 * @version V1.0.0
 */
@Service
public class TestMsgBusiness {
    private static final Logger LOG = LoggerFactory.getLogger(TestMsgBusiness.class);



    public void doSomeThing(String jsonStr){

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(jsonStr);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }

}
