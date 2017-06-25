/**
 * MyTest.java <br>
 * com.wjs.db.utils <br>
 * Copyright (c) 2016.
 */
package com.md.mq.db.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 *
 * @date	 2017年3月5日
 * @version  V1.0.0
 */

public class MyTest {

	public static void main( String[] args ) {
		String str = "19921024";
		try {
			byte[] encodeBase64 = Base64.encodeBase64(str.getBytes("UTF-8"));
			System.out.println("加密：" + new String(encodeBase64));
		} catch ( UnsupportedEncodingException e ) {
			e.printStackTrace();
		}

		String code = "MTk5MjEwMjQ=";
		byte[] result = Base64.decodeBase64(code);
		System.out.println("解码：" + new String(result));
	}

}
