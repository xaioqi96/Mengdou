package com.md.entity;

import java.io.Serializable;

/**
 *
 */
public class MUsers implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键ID */
	private Long userId;

	/** 用户ID */
	private String userCode;

	/** 用户名 */
	private String userName;

	/** 手机号 */
	private String phoneNo;

	/** 密码 */
	private String passWord;

	/** 头像地址 */
	private String headRul;

	/** 注册时间 */
	private String createTime;

	/** 最近一次登陆时间 */
	private String loginTime;


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Long getUserId() {
		return this.userId;
	}


	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}


	public String getUserCode() {
		return this.userCode;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getUserName() {
		return this.userName;
	}


	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}


	public String getPhoneNo() {
		return this.phoneNo;
	}


	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}


	public String getPassWord() {
		return this.passWord;
	}


	public void setHeadRul(String headRul) {
		this.headRul = headRul;
	}


	public String getHeadRul() {
		return this.headRul;
	}


	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public String getCreateTime() {
		return this.createTime;
	}


	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}


	public String getLoginTime() {
		return this.loginTime;
	}

}
