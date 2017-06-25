package com.md.entity;

import java.io.Serializable;

/**
 * 
 */
public class MQuizQuestions implements Serializable {

	private static final long serialVersionUID = 1L;

	/**  */
	private Long id;

	/** 标题 */
	private String title;

	/** 内容 */
	private String content;

	/** 答案 */
	private String answer;

	/** 竞猜人数 */
	private Integer askNum;

	/** 奖励数量 */
	private Integer rewardNum;

	/** 题目级别 */
	private Integer level;

	/** 是否首页显示 */
	private String isHome;

	/** 状态（1：有效，0：无效） */
	private String status;

	/** 发布人ID */
	private Long userId;

	/** 插入时间 */
	private String insertTime;

	/** 最近修改时间 */
	private String updateTime;


	public void setId(Long id) {
		this.id = id;
	}


	public Long getId() {
		return this.id;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getTitle() {
		return this.title;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getContent() {
		return this.content;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}


	public String getAnswer() {
		return this.answer;
	}


	public void setAskNum(Integer askNum) {
		this.askNum = askNum;
	}


	public Integer getAskNum() {
		return this.askNum;
	}


	public void setRewardNum(Integer rewardNum) {
		this.rewardNum = rewardNum;
	}


	public Integer getRewardNum() {
		return this.rewardNum;
	}


	public void setLevel(Integer level) {
		this.level = level;
	}


	public Integer getLevel() {
		return this.level;
	}


	public void setIsHome(String isHome) {
		this.isHome = isHome;
	}


	public String getIsHome() {
		return this.isHome;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getStatus() {
		return this.status;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Long getUserId() {
		return this.userId;
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
