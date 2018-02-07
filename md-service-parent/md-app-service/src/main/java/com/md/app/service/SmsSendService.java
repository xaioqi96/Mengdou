package com.md.app.service;

import com.md.utils.AjaxResult;

public interface SmsSendService {
	/**
	 * 短信发送公用方法
	 * @param phone
	 * @param smscontent
	 */
	public void smssendpub(String phone,String smscontent,AjaxResult result);
}
