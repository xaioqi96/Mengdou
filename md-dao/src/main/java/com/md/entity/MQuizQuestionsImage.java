package com.md.entity;

import java.io.Serializable;

/**
 * 
 */
public class MQuizQuestionsImage implements Serializable {

	private static final long serialVersionUID = 1L;

	/**  */
	private Long ID;

	/**  */
	private Integer qId;

	/**  */
	private String qName;

	/**  */
	private String qUrl;

	/** 1、普通竞猜 2、高级竞猜 */
	private String qType;

	/** 1、有效 2、无效 3、竞猜完毕 */
	private String qIsflag;

	/**  */
	private java.util.Date inserttime;

	/**  */
	private java.util.Date updatetime;


	public void setID(Long ID) {
		this.ID = ID;
	}


	public Long getID() {
		return this.ID;
	}


	public void setQId(Integer qId) {
		this.qId = qId;
	}


	public Integer getQId() {
		return this.qId;
	}


	public void setQName(String qName) {
		this.qName = qName;
	}


	public String getQName() {
		return this.qName;
	}


	public void setQUrl(String qUrl) {
		this.qUrl = qUrl;
	}


	public String getQUrl() {
		return this.qUrl;
	}


	public void setQType(String qType) {
		this.qType = qType;
	}


	public String getQType() {
		return this.qType;
	}


	public void setQIsflag(String qIsflag) {
		this.qIsflag = qIsflag;
	}


	public String getQIsflag() {
		return this.qIsflag;
	}


	public void setInserttime(java.util.Date inserttime) {
		this.inserttime = inserttime;
	}


	public java.util.Date getInserttime() {
		return this.inserttime;
	}


	public void setUpdatetime(java.util.Date updatetime) {
		this.updatetime = updatetime;
	}


	public java.util.Date getUpdatetime() {
		return this.updatetime;
	}

}
