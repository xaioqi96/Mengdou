package com.md.app.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.md.app.service.UserService;
import com.md.entity.MQuizQuestions;
import com.md.entity.MUserAccount;
import com.md.entity.MUsers;
import com.md.enums.DateTimePatternEnum;
import com.md.mapper.MQuizQuestionsMapper;
import com.md.mapper.MUserAccountMapper;
import com.md.mapper.MUsersMapper;
import com.md.mq.product.MdProducer;

import com.md.redis.RedisService;
import com.md.utils.*;
import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by ljx on 2017/2/7.
 */

@Service
public class UserServiceImpl implements UserService {

    protected Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private MUsersMapper<MUsers,MUsers> mUsersMapper;
    @Resource
    private MUserAccountMapper<MUserAccount,MUserAccount> mUserAccountMapper;

    @Resource
    private RedisService redisService;
    @Resource
    private MdProducer MdProducer;


    @Override
    public void saveUser(JSONObject requestData, AjaxResult result) {

        String phoneNo = requestData.getString("phone");
        String passWord = MD5.toMD5(requestData.getString("passWord"));


        MUsers mUsers = new MUsers();

        mUsers.setPhoneNo(phoneNo);
        List<MUsers> list = mUsersMapper.selectList(mUsers);
        if(list.size()>0){
            result.setMessage("手机号已注册！");
            result.setStatusCode(ResponseCode.OTHER.getCode());
            return;
        }


        mUsers = new MUsers();

        mUsers.setUserCode(phoneNo);
        mUsers.setPhoneNo(phoneNo);
        mUsers.setPassWord(passWord);
        mUsers.setCreateTime(DateUtils.getCurrentTime(DateTimePatternEnum.YYYY_MM_DD.getPattern()));
        mUsers.setLoginTime(DateUtils.getCurrentTime(DateTimePatternEnum.YYYY_MM_DD.getPattern()));

        mUsersMapper.insert(mUsers);


        Date now = new Date();
        MUserAccount mUserAccount = new MUserAccount();
        mUserAccount.setUserId(mUsers.getUserId());
        mUserAccount.setMdNum(1000L);
        mUserAccount.setInsertTime(now);
        mUserAccount.setUpdateTime(now);

        mUserAccountMapper.insert(mUserAccount);


        result.setMessage(ResponseCode.SUCCESS.getDesc());
        result.setStatusCode(ResponseCode.SUCCESS.getCode());

    }

    @Override
    public void login(JSONObject requestData, AjaxResult result) {

        String phoneNo = requestData.getString("userCode");
        String passWord = MD5.toMD5(requestData.getString("passWord"));


        MUsers mUsers = mUsersMapper.getMUsersByPhoneNo(phoneNo);
        if(ObjectUtils.isNull(mUsers)){
            result.setMessage("手机号还未注册。");
            result.setStatusCode(ResponseCode.OTHER.getCode());
            return;
        }
        if(!mUsers.getPassWord().equals(passWord)){
            result.setMessage("密码错误。");
            result.setStatusCode(ResponseCode.OTHER.getCode());
            return;
        }

        MUsers uu = new MUsers();
        uu.setUserId(mUsers.getUserId());
        uu.setLoginTime(DateUtils.getCurrentTime(DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));

        mUsersMapper.update(uu);



        result.setMessage(ResponseCode.SUCCESS.getDesc());
        result.setStatusCode(ResponseCode.SUCCESS.getCode());
    }

    @Override
    public void userCenter(JSONObject requestData, AjaxResult result) {
        JSONObject retJson =  new JSONObject();

        String userId = requestData.getString("userId");
        String userCode = requestData.getString("userCode");


        MUserAccount account  = mUserAccountMapper.getAccountByUserId(userId);
/*
        MUsers mUsers = mUsersMapper.getMUsersById(userId);

        if(ObjectUtils.isNull(mUsers)){
            result.setMessage(ResponseCode.USERNOTFOUND.getDesc());
            result.setStatusCode(ResponseCode.USERNOTFOUND.getCode());
            return;
        }*/

        retJson.put("userCode",userCode);           // 昵称
        retJson.put("mdNum",account.getMdNum());    // 豆数量
        retJson.put("wordLvl",1000);                // 排名




        result.setData(retJson);
        result.setMessage(ResponseCode.SUCCESS.getDesc());
        result.setStatusCode(ResponseCode.SUCCESS.getCode());
    }
}
