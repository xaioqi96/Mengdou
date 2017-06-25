package com.md.app.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.md.entity.MQuizQuestions;
import com.md.mapper.MQuizQuestionsMapper;
import com.md.mq.bean.TestMsg;
import com.md.pages.Page;
import com.md.redis.RedisService;
import com.md.utils.AjaxResult;
import com.md.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.md.mq.product.MdProducer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by ljx on 2017/2/7.
 */

@Service
public class IndexServiceImpl implements IndexService {

    protected Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);

    @Resource
    private MQuizQuestionsMapper<MQuizQuestions,MQuizQuestions> mQuizQuestionsMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private MdProducer MdProducer;

    public void getHome() {

      /*  TestMsg msg = new TestMsg();
        msg.setParamJson("测试消费");
        MdProducer.send(msg);
*/
      try {
          //redisService.addString("test_aaa","11111111111111");
          MQuizQuestions query = new MQuizQuestions();

          List<MQuizQuestions> list = mQuizQuestionsMapper.selectList(query);

          String sss = JSON.toJSONString(list);

          System.out.println(sss);
          // String aa = redisService.getValue("test_aaa");
          logger.debug(sss);
      }catch (Exception e){
          e.printStackTrace();
      }
    }

    @Override
    public void getHome(JSONObject requestData, Page page, AjaxResult result) {

        Map<String,Object> paramMap = JsonUtil.toHashMap(requestData);


        List<MQuizQuestions> queList = mQuizQuestionsMapper.getQuestionsPageList(paramMap,page);
    }
}
