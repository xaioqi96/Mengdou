package com.md.app.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.md.app.service.SmsSendService;
import com.md.entity.MSmsSendRecord;
import com.md.entity.MUsers;
import com.md.entity.MVerifyCode;
import com.md.mapper.MSmsSendRecordMapper;
import com.md.mapper.MVerifyCodeMapper;
import com.md.utils.AjaxResult;
import com.md.utils.ResponseCode;
import com.md.utils.VdCodeUtil;
import com.md.utils.ZMsgClient;
import com.md.utils.model.MsgBean;

import props.ApkProps;

@Service
public class SmsSendServiceImp implements SmsSendService{

	@Resource
	private MVerifyCodeMapper<MVerifyCode,MVerifyCode> mVerifyCodeMapper;
	@Resource
	private MSmsSendRecordMapper<MSmsSendRecord,MSmsSendRecord> mSmsSendRecordMapper;
	
	@Override
	public void smssendpub(String phone, String smscontent,AjaxResult result) {
		// TODO Auto-generated method stub
		int smssendnum = Integer.valueOf(ApkProps.apkProps.getProperty("smssendnum"));
		Date now = new Date();
		MVerifyCode codeinfo = new MVerifyCode();
		codeinfo.setAccount(phone);
		codeinfo.setCodeDate("now");
		List<MVerifyCode> codeList = mVerifyCodeMapper.selectList(codeinfo);
		if(codeList.size()>smssendnum){
			result.setMessage(ResponseCode.MESSAGECODENUMUP.getDesc());
			result.setStatusCode(ResponseCode.MESSAGECODENUMUP.getCode());
			return;
		}
		
		String code = VdCodeUtil.geneVdCode();
		codeinfo.setCode(code);
		codeinfo.setType("1");
		codeinfo.setCodeTime(now);
		mVerifyCodeMapper.insert(codeinfo);
		
		String content = smscontent+code;
		MSmsSendRecord sendinfo = new MSmsSendRecord();
		sendinfo.setPhone(phone);
		sendinfo.setContent(content);
		sendinfo.setSendTime(now);
		mSmsSendRecordMapper.insert(sendinfo);
		
		/*发送短信验证码*/
		MsgBean bean = new MsgBean(phone,content);
		ZMsgClient.sendMsg(bean);
	
	}
	
	public static void main(String args[]){
		String code = VdCodeUtil.geneVdCode();
		System.out.println(code);
	}

}
