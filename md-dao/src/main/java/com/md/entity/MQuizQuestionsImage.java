package com.md.entity;

import java.io.Serializable;

/**
 * 
 */
public class MQuizQuestionsImage implements Serializable {

	private static final long serialVersionUID = 1L;

	/** ID */
	private Long id;

	/** 题目ID */
	private Long qId;

	/** 图片路径 */
	private String rul;

	/** 状态（1：有效、0：无效） */
	private String status;

	/** 插入时间 */
	private String insertTime;

	/** 修改时间 */
	private String updateTime;


	public void setId(Long id) {
		this.id = id;
	}


	public Long getId() {
		return this.id;
	}


	public void setQId(Long qId) {
		this.qId = qId;
	}


	public Long getQId() {
		return this.qId;
	}


	public void setRul(String rul) {
		this.rul = rul;
	}


	public String getRul() {
		return this.rul;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getStatus() {
		return this.status;
	}


	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}


	public String getInsertTime() {
		return this.insertTime;
	}


	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}


	public String getUpdateTime() {
		return this.updateTime;
	}

}
