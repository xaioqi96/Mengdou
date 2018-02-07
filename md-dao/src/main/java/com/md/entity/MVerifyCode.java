package com.md.entity;

import java.io.Serializable;

/**
 * 
 */
public class MVerifyCode implements Serializable {

	private static final long serialVersionUID = 1L;

	/**  */
	private Integer id;

	/**  */
	private String code;

	/**  */
	private java.util.Date codeTime;
	
	private String codeDate;

	/**  */
	private String type;

	/**  */
	private String account;

	/**  */
	private String memo;

	/**  */
	private String email;

	/**  */
	private String custNo;


	public String getCodeDate() {
		return codeDate;
	}


	public void setCodeDate(String codeDate) {
		this.codeDate = codeDate;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getId() {
		return this.id;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getCode() {
		return this.code;
	}


	public void setCodeTime(java.util.Date codeTime) {
		this.codeTime = codeTime;
	}


	public java.util.Date getCodeTime() {
		return this.codeTime;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getType() {
		return this.type;
	}


	public void setAccount(String account) {
		this.account = account;
	}


	public String getAccount() {
		return this.account;
	}


	public void setMemo(String memo) {
		this.memo = memo;
	}


	public String getMemo() {
		return this.memo;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getEmail() {
		return this.email;
	}


	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}


	public String getCustNo() {
		return this.custNo;
	}

}
