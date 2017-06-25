/**
 * AjaxResult.java <br>
 * com.wjs.utils <br>
 * Copyright (c) 2016.
 */
package com.md.utils;

/**
 * Ajax返回结果信息,用此对象封装后转成json,传到浏览器
 * <p>
 *
 * @date	 2017年3月05日
 * @version  V1.0.0
 */
public class AjaxResult {

	/**
	 * 必选。状态码。
	 */
	private int statusCode;

	/**
	 * 失败或成功的提示信息
	 */
	private String message;

	/**
	 * 返回的数据
	 */
	private Object data;


	public int getStatusCode() {
		return statusCode;
	}


	public void setStatusCode( int statusCode ) {
		this.statusCode = statusCode;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage( String message ) {
		this.message = message;
	}


	public Object getData() {
		return data;
	}


	public void setData( Object data ) {
		this.data = data;
	}


}
