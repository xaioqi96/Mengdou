package com.md.mq;

import com.md.mq.bean.TestMsg;
import com.md.mq.product.MdProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by ljx on 2017/3/10.
 */



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:producer.xml"})
public class ProducerTest {

    @Resource
    private MdProducer mdProducer;



    @Test
    public void testMsg() {

        TestMsg msg = new TestMsg();
        msg.setParamJson("测试消费对象");
        mdProducer.send(msg);


        String ss= "测试消费String";
        mdProducer.send(ss);
    }
}
