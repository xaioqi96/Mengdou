package com.md.app.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.md.app.service.SmsSendService;
import com.md.app.service.UserService;
import com.md.entity.MUserAccount;
import com.md.entity.MVerifyCode;
import com.md.entity.MUsers;
import com.md.entity.MSmsSendRecord;
import com.md.enums.DateTimePatternEnum;
import com.md.mapper.MSmsSendRecordMapper;
import com.md.mapper.MUserAccountMapper;
import com.md.mapper.MUsersMapper;
import com.md.mapper.MVerifyCodeMapper;
import com.md.mq.product.MdProducer;

import com.md.redis.RedisService;
import com.md.utils.*;
import com.md.utils.model.MsgBean;

import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	private MUsersMapper<MUsers, MUsers> mUsersMapper;
	@Resource
	private MUserAccountMapper<MUserAccount, MUserAccount> mUserAccountMapper;
	@Resource
	private MVerifyCodeMapper<MVerifyCode,MVerifyCode> mVerifyCodeMapper;
	@Resource
	private MSmsSendRecordMapper<MSmsSendRecord,MSmsSendRecord> mSmsSendRecordMapper;
	

	@Resource
	private RedisService redisService;
	
	@Resource
	private SmsSendService smsSendService;
	
	@Resource
	private MdProducer MdProducer;

	@Override
	public void saveUser(JSONObject requestData, AjaxResult result) {

		String phoneNo = requestData.getString("phone");
		String passWord = MD5.toMD5(requestData.getString("passWord"));
		String codep = requestData.getString("code");
		redisService.addString("test_aaa", "11111111111111");
		Date now = new Date();
		MUsers mUsers = new MUsers();

		mUsers.setPhoneNo(phoneNo);
		List<MUsers> list = mUsersMapper.selectList(mUsers);
		if (list.size() > 0) {
			result.setMessage("手机号已注册！");
			result.setStatusCode(ResponseCode.OTHER.getCode());
			return;
		}
		
		MVerifyCode codeinfo = new MVerifyCode();
		codeinfo.setAccount(phoneNo);
		
		List<MVerifyCode> codeList = mVerifyCodeMapper.selectList(codeinfo);
		String code = codeList.get(0).getCode();
		Date codetime = codeList.get(0).getCodeTime();
		long diff = now.getTime() - codetime.getTime();//这样得到的差值是微秒级别
		long days = diff / (1000 * 60 * 60 * 24);  
		long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);  
		long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);  
		if(minutes>5){
			result.setMessage(ResponseCode.MESSAGECODEEXPIRE.getDesc());
			result.setStatusCode(ResponseCode.MESSAGECODEEXPIRE.getCode());
			return;
		}
		
		if(!codep.equals(code)){
			result.setMessage(ResponseCode.MESSAGECODEERROR.getDesc());
			result.setStatusCode(ResponseCode.MESSAGECODEERROR.getCode());
			return;
		}

		mUsers = new MUsers();
		
		mUsers.setUserCode(phoneNo);
		mUsers.setPhoneNo(phoneNo);
		mUsers.setPassWord(passWord);
		mUsers.setCreateTime(now);
		mUsers.setLoginTime(now);

		mUsersMapper.insert(mUsers);

		MUserAccount mUserAccount = new MUserAccount();
		mUserAccount.setUserId(mUsers.getUserId());
		mUserAccount.setMdNum(1000L);
		mUserAccount.setInsertTime(now);
		mUserAccount.setUpdateTime(now);

		mUserAccountMapper.insert(mUserAccount);
		String aa = redisService.getValue("test_aaa");

		System.out.println("redistest==========================" + aa);

		result.setMessage(ResponseCode.SUCCESS.getDesc());
		result.setStatusCode(ResponseCode.SUCCESS.getCode());

	}

	@Override
	public void login(JSONObject requestData, AjaxResult result) {

		String phoneNo = requestData.getString("userCode");
		String passWord = MD5.toMD5(requestData.getString("passWord"));

		MUsers mUsers = mUsersMapper.getMUsersByPhoneNo(phoneNo);
		if (ObjectUtils.isNull(mUsers)) {
			result.setMessage("手机号还未注册。");
			result.setStatusCode(ResponseCode.OTHER.getCode());
			return;
		}
		if (!mUsers.getPassWord().equals(passWord)) {
			result.setMessage("密码错误。");
			result.setStatusCode(ResponseCode.OTHER.getCode());
			return;
		}
		Date now = new Date();
		MUsers uu = new MUsers();
		uu.setUserId(mUsers.getUserId());
		uu.setLoginTime(now);

		mUsersMapper.update(uu);

		result.setMessage(ResponseCode.SUCCESS.getDesc());
		result.setStatusCode(ResponseCode.SUCCESS.getCode());
	}

	@Override
	public void userCenter(JSONObject requestData, AjaxResult result) {
		JSONObject retJson = new JSONObject();

		String userId = requestData.getString("userId");
		String userCode = requestData.getString("userCode");

		MUserAccount account = mUserAccountMapper.getAccountByUserId(userId);
		/*
		 * MUsers mUsers = mUsersMapper.getMUsersById(userId);
		 * 
		 * if(ObjectUtils.isNull(mUsers)){
		 * result.setMessage(ResponseCode.USERNOTFOUND.getDesc());
		 * result.setStatusCode(ResponseCode.USERNOTFOUND.getCode()); return; }
		 */

		retJson.put("userCode", userCode); // 昵称
		retJson.put("mdNum", account.getMdNum()); // 豆数量
		retJson.put("wordLvl", 1000); // 排名

		result.setData(retJson);
		result.setMessage(ResponseCode.SUCCESS.getDesc());
		result.setStatusCode(ResponseCode.SUCCESS.getCode());
	}

	@Override
	public void sendsms(JSONObject requestData, AjaxResult result) {
		// TODO Auto-generated method stub
		String phoneNo = requestData.getString("phone");
		MUsers mUsers = new MUsers();
		mUsers.setPhoneNo(phoneNo);
		List<MUsers> list = mUsersMapper.selectList(mUsers);
		if (list.size() > 0) {
			result.setMessage("手机号已注册！");
			result.setStatusCode(ResponseCode.OTHER.getCode());
			return;
		}
		smsSendService.smssendpub(phoneNo, "您好，您的验证码是:",result);
		if(result.getStatusCode()==ResponseCode.SUCCESS.getCode()){
			result.setMessage(ResponseCode.SUCCESS.getDesc());
			result.setStatusCode(ResponseCode.SUCCESS.getCode());
		}
	}
	
	public static void main(String[] args){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    try  
	    {  
	      Date d1 = df.parse("2017-07-06 14:33:40");  
	      Date d2 = df.parse("2017-07-06 14:31:24");  
	      long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别  
	      long days = diff / (1000 * 60 * 60 * 24);  
	   
	      long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);  
	      long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);  
	      System.out.println(""+days+"天"+hours+"小时"+minutes+"分");  
	    }catch (Exception e)  
	    {  
	    }  
	}
}
