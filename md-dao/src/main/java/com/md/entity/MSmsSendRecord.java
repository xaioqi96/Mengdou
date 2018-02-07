package com.md.entity;

import java.io.Serializable;

/**
 * 短信发送记录表（只有发送失败的短信才记录）
 */
public class MSmsSendRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	private Long id;

	/** 发送的手机号 */
	private String phone;

	/** 发送的内容 */
	private String content;

	/** 发送的时间 */
	private java.util.Date sendTime;

	/** 0表示无效，1表示有效 */
	private Integer status;

	/** 重试次数 */
	private Integer retryTimes;

	/** 版本 */
	private Long version;

	/** 应用场景 */
	private String scenarios;

	/** 短信渠道 */
	private String channel;


	public void setId(Long id) {
		this.id = id;
	}


	public Long getId() {
		return this.id;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getPhone() {
		return this.phone;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getContent() {
		return this.content;
	}


	public void setSendTime(java.util.Date sendTime) {
		this.sendTime = sendTime;
	}


	public java.util.Date getSendTime() {
		return this.sendTime;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public Integer getStatus() {
		return this.status;
	}


	public void setRetryTimes(Integer retryTimes) {
		this.retryTimes = retryTimes;
	}


	public Integer getRetryTimes() {
		return this.retryTimes;
	}


	public void setVersion(Long version) {
		this.version = version;
	}


	public Long getVersion() {
		return this.version;
	}


	public void setScenarios(String scenarios) {
		this.scenarios = scenarios;
	}


	public String getScenarios() {
		return this.scenarios;
	}


	public void setChannel(String channel) {
		this.channel = channel;
	}


	public String getChannel() {
		return this.channel;
	}

}
