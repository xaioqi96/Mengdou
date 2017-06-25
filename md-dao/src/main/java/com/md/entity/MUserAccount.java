package com.md.entity;

import java.io.Serializable;

/**
 *
 */
public class MUserAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	private Long id;

	/** 用户代码 */
	private Long userId;

	/** 蒙逗数量 */
	private Long mdNum;

	/** 插入时间 */
	private java.util.Date insertTime;

	/** 修改时间 */
	private java.util.Date updateTime;


	public void setId(Long id) {
		this.id = id;
	}


	public Long getId() {
		return this.id;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Long getUserId() {
		return this.userId;
	}


	public void setMdNum(Long mdNum) {
		this.mdNum = mdNum;
	}


	public Long getMdNum() {
		return this.mdNum;
	}


	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}


	public java.util.Date getInsertTime() {
		return this.insertTime;
	}


	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}


	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

}
