package com.md.entity;

import java.io.Serializable;

/**
 * 
 */
public class MQuizQuestions implements Serializable {

	private static final long serialVersionUID = 1L;

	/**  */
	private Long ID;

	/**  */
	private String qTitle;

	/**  */
	private String qContent;

	/**  */
	private String qAnswer;

	/**  */
	private Integer qNumber;

	/**  */
	private Integer qRewardnum;

	/**  */
	private Integer userid;

	/** 1、普通竞猜 2、高级竞猜 */
	private String qLevel;

	/**  */
	private java.util.Date inserttime;

	/** 1、显示  0、不显示 */
	private String qIshomepage;

	/**  */
	private java.util.Date updatetime;

	/** 1、有效 2、无效 3、竞猜完毕 */
	private String qIsflag;


	public void setID(Long ID) {
		this.ID = ID;
	}


	public Long getID() {
		return this.ID;
	}


	public void setQTitle(String qTitle) {
		this.qTitle = qTitle;
	}


	public String getQTitle() {
		return this.qTitle;
	}


	public void setQContent(String qContent) {
		this.qContent = qContent;
	}


	public String getQContent() {
		return this.qContent;
	}


	public void setQAnswer(String qAnswer) {
		this.qAnswer = qAnswer;
	}


	public String getQAnswer() {
		return this.qAnswer;
	}


	public void setQNumber(Integer qNumber) {
		this.qNumber = qNumber;
	}


	public Integer getQNumber() {
		return this.qNumber;
	}


	public void setQRewardnum(Integer qRewardnum) {
		this.qRewardnum = qRewardnum;
	}


	public Integer getQRewardnum() {
		return this.qRewardnum;
	}


	public void setUserid(Integer userid) {
		this.userid = userid;
	}


	public Integer getUserid() {
		return this.userid;
	}


	public void setQLevel(String qLevel) {
		this.qLevel = qLevel;
	}


	public String getQLevel() {
		return this.qLevel;
	}


	public void setInserttime(java.util.Date inserttime) {
		this.inserttime = inserttime;
	}


	public java.util.Date getInserttime() {
		return this.inserttime;
	}


	public void setQIshomepage(String qIshomepage) {
		this.qIshomepage = qIshomepage;
	}


	public String getQIshomepage() {
		return this.qIshomepage;
	}


	public void setUpdatetime(java.util.Date updatetime) {
		this.updatetime = updatetime;
	}


	public java.util.Date getUpdatetime() {
		return this.updatetime;
	}


	public void setQIsflag(String qIsflag) {
		this.qIsflag = qIsflag;
	}


	public String getQIsflag() {
		return this.qIsflag;
	}

}
