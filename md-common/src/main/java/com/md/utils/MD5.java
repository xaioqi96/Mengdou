package com.md.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.*;
import java.util.Map.Entry;

/**
 * MD5加密工具类
 * <p>
 *
 * @version  V1.0.0
 */
public class MD5 {

	private final static Logger logger = LoggerFactory.getLogger(MD5.class); 


	/**
	 * @Description 字符串加密为MD5 中文加密一致通用,必须转码处理： plainText.getBytes("UTF-8")
	 * @param plainText
	 *            需要加密的字符串
	 * @return
	 */
	public static String toMD5( String plainText ) { 
		StringBuffer rlt = new StringBuffer();
		try {
			rlt.append(md5String(plainText.getBytes("UTF-8")));
		} catch ( UnsupportedEncodingException e ) {
			logger.error(" CipherHelper toMD5 exception:", e.toString());
		}
		return rlt.toString();
	}


	/**
	 * MD5 参数签名生成算法
	 *
	 * @param HashMap
	 *            <String,String> params 请求参数集，所有参数必须已转换为字符串类型
	 * @param String
	 *            secret 签名密钥
	 * @return 签名
	 * @throws IOException
	 */
	public static String getSignature( HashMap<String, String> params, String secret ) {
		Map<String, String> sortedParams = new TreeMap<String, String>(params);
		Set<Entry<String, String>> entrys = sortedParams.entrySet();
		StringBuilder basestring = new StringBuilder();
		for ( Entry<String, String> param : entrys ) {
			basestring.append(param.getKey()).append("=").append(param.getValue());
		}
		return getSignature(basestring.toString(), secret);
	}


	/**
	 * MD5 参数签名生成算法
	 *
	 * @param String
	 *            sigstr 签名字符串
	 * @param String
	 *            secret 签名密钥
	 * @return 签名
	 * @throws IOException
	 */
	public static String getSignature( String sigstr, String secret ) {
		StringBuilder basestring = new StringBuilder(sigstr);
		basestring.append("#");
		basestring.append(toMD5(secret));
		return toMD5(basestring.toString());
	}


	public static byte[] md5Raw( byte[] data ) {
		byte[] md5buf = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("md5");
			md5buf = md5.digest(data);
		} catch ( Exception e ) {
			md5buf = null;
			logger.error(e.getMessage(), e);
		}
		return md5buf;
	}


	public static String md5String( byte[] data ) {
		String md5Str = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("md5");
			md5Str = "";
			byte[] buf = md5.digest(data);
			for ( byte element : buf ) {
				md5Str += byte2Hex(element);
			}
		} catch ( Exception e ) {
			md5Str = null;
			logger.error(e.getMessage(), e);
		}
		return md5Str;
	}


	public static String byte2Hex( byte b ) {
		String hex = Integer.toHexString(b);
		if ( hex.length() > 2 ) {
			hex = hex.substring(hex.length() - 2);
		}
		while ( hex.length() < 2 ) {
			hex = "0" + hex;
		}
		return hex;
	}


	public static String byte2Hex( byte[] bytes ) {
		Formatter formatter = new Formatter();
		for ( byte b : bytes ) {
			formatter.format("%02x", b);
		}
		String hash = formatter.toString();
		formatter.close();
		return hash;
	}

}
