/**
 * Project Name:frame-common File Name:DateTimePatternEnum.java Package Name:com.wuys.enums Date:2016年1月7日
 * 下午1:24:54 Copyright (c) 2016, WYS All Rights Reserved.
 *
 */

package com.md.enums;

/**
 * Created by ljx on 2017/2/7.
 */
public enum DateTimePatternEnum {

	YYYY("yyyy"), 
	YYYY_MM("yyyy-MM"), 
	YYYYMM("yyyyMM"), 
	YYYYMMDDHHMMSS("yyyyMMddHHmmss"),
	MM_POINT_DD("MM.dd"), 
	MM_DD("MM-dd"), 
	MMdd("MMdd"), 
	YYYYMMDD("yyyyMMdd"), 
	YYYY_MM_DD("yyyy-MM-dd"),
	YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
	YYYY_MM_DD_HH_MM_SS_SSS("yyyy-MM-dd HH:mm:ss.SSS"),
	YYYYMMDD_DIAGONAL("yyyy/MM/dd");
	
	private String pattern;


	private DateTimePatternEnum( String pattern ) {
		this.pattern = pattern;
	}


	public String getPattern() {
		return pattern;
	}


	public void setPattern( String pattern ) {
		this.pattern = pattern;
	}

}
