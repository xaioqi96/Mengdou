/**
 * ResponseCode.java <br>
 * com.wuys.base <br>
 * Copyright (c) 2016.
 */
package com.md.utils;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 状态码
 * <p>
 *
 * @author   WYS
 * @date	 2016年5月19日
 * @version  V1.0.0
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseCode {

	SUCCESS(200, "请求成功"), 
	NOPERMISSION(403, "没有权限"), 
	BUSINESSERROR(415, "业务异常"), 
	CODEERROR(425, "验证码错误"), 
	CUSTREPEAT(426,"客户信息重复"), 
	PASSWORDERROR(427, "密码不正确"), 
	SERVERERROR(500, "服务器错误"), 
	LOGINTIMEOUT(-1, "登录超时，请重新登录"), 
	LOGIN_SINGLE(-2,"该账号已在异地登录，您将退出本系统，请重新登录"),
	LOGIN_ILLEGAL(-3, "请重新登录"), 
	USERNOTFOUND(600, "用户不存在"), 
	USER_LOCK(601,"用户被锁定"),
	WECHATNOTBINDING(601, "未绑定微信"), 
	ILLEGALTOKEN(700, "非法的Token"), 
	
	MESSAGECODEERROR(800,"短信码错误"),
	MESSAGECODEEXPIRE(801,"短信码已过期"),
	
	WRONG_REQUEST_PARAM(1, "请求参数错误"),
	
	REQUEST_ERROR(416, "请求失败"),
	
	CHECK_USER_BASE_ERROR(701,"用户基础信息校验失败"),
	CHECK_USER_IDCARD_ERROR(702,"用户实名认证校验失败"),
	CHECK_USER_BANK_ERROR(703,"用户银行卡绑定校验失败"),
	CHECK_USER_EMAIL_ERROR(704,"用户邮箱绑定校验失败"),
	CHECK_USER_CASHPWD_ERROR(706,"用户未设置交易密码"),
	CHECK_USER_VERIFIED_FAIL(707,"用户未实名认证"),
	OTHER(999, "其他异常");
	
	

	private int code;

	private String desc;


	/**
	 * 创建 ResponseCode对象.
	 *
	 * @param code
	 * @param desc
	 */

	private ResponseCode(int code, String desc ) {
		this.code = code;
		this.desc = desc;
	}


	public int getCode() {
		return code;
	}


	public void setCode( int code ) {
		this.code = code;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc( String desc ) {
		this.desc = desc;
	}

}
