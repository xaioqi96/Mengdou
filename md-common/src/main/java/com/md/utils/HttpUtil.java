/**
 * AppUtil.java <br>
 * com.wjs.utils <br>
 * Copyright (c) 2016.
 */
package com.md.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


/**
 * http工具类
 * <p>
 *
 * @version  V1.0.0
 */

public class HttpUtil {

	public static List<String> appAgentList = new ArrayList<String>();
	static {
		appAgentList.add("iphone");
		appAgentList.add("android");
		appAgentList.add("ipod");
		appAgentList.add("ipad");
		appAgentList.add("mobile");
		appAgentList.add("blackberry");
		appAgentList.add("webos");
		appAgentList.add("incognito");
		appAgentList.add("webmate");
		appAgentList.add("bada");
		appAgentList.add("nokia");
		appAgentList.add("lg");
		appAgentList.add("ucweb");
		appAgentList.add("skyfire");
	}


	/**
	 * 
	 * 获取浏览器请求类型
	 * <p>
	 *
	 * @param userAgent
	 * @return 
	 */
	public static String getRequestBrowserType( String userAgent ) {
		String requestType = "web";
		for ( String type : appAgentList ) {
			if ( userAgent.toLowerCase().indexOf(type) > 0 ) {
				requestType = "wap";
			}
		}
		return requestType;
	}


	/**
	 * 通过get方式，发送请求，返回buffer
	 * @param strUrl
	 * @return
	 */
	public static String getSend( String strUrl ) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(strUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.setUseCaches(false);
			connection.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ( (line = reader.readLine()) != null ) {
				buffer.append(line);
			}

			reader.close();
			return buffer.toString();
		} catch ( IOException e ) {
			e.printStackTrace();
			return null;
		} finally {
			if ( connection != null ) {
				connection.disconnect();
			}
		}
	}


	/**
	 * 通过get方式，发送请求，返回buffer
	 * @param strUrl
	 * @param encode
	 * @return
	 */
	public static String getSend( String strUrl, String encode ) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(strUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.setUseCaches(false);
			connection.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encode));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ( (line = reader.readLine()) != null ) {
				buffer.append(line);
			}

			reader.close();
			return buffer.toString();
		} catch ( IOException e ) {
			e.printStackTrace();
			return null;
		} finally {
			if ( connection != null ) {
				connection.disconnect();
			}
		}
	}


	/**
	 * 通过post方式发送请求，返回buffer
	 * @param strUrl
	 * @param param
	 * @return
	 */
	public static String postSend( String strUrl, String param ) {
		URL url = null;
		HttpURLConnection connection = null;

		try {
			url = new URL(strUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.connect();

			//POST方法时使用
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(param);
			out.flush();
			out.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ( (line = reader.readLine()) != null ) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		} catch ( IOException e ) {
			e.printStackTrace();
			return null;
		} finally {
			if ( connection != null ) {
				connection.disconnect();
			}
		}
	}
	


	/**
	 * 获取客户端IP
	 * @param request
	 * @return
	 */
	public static String getIPAddr( HttpServletRequest request ) {
		String ip = null;
		Enumeration<?> enu = request.getHeaderNames();
		while ( enu.hasMoreElements() ) {
			String name = (String) enu.nextElement();
			if ( name.equalsIgnoreCase("X-Forwarded-For") ) {
				ip = getRealIp(request.getHeader(name));
			} else if ( name.equalsIgnoreCase("Proxy-Client-IP") ) {
				ip = getRealIp(request.getHeader(name));
			} else if ( name.equalsIgnoreCase("WL-Proxy-Client-IP") ) {
				ip = getRealIp(request.getHeader(name));
			}

			if ( (ip != null) && (ip.length() != 0) ) {
				break;
			}
		}

		if ( (ip == null) || (ip.length() == 0) ) {
			ip = getRealIp(request.getRemoteAddr());
		}

		return ip;
	}


	/**
	 * 多个IP的情况则获取有效的IP
	 * @param ip
	 * @return
	 */
	public static String getRealIp( String ip ) {
		if ( ip != null && !"".equals(ip) ) {
			int index = ip.indexOf(",");
			if ( index != -1 ) {
				ip = ip.substring(0, index);
			}
		}
		return ip;
	}
	
	public static void main( String[] args ) {
		
	}
}
