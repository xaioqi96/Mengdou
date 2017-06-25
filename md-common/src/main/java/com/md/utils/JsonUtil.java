/**
 * JsonUtil.java <br>
 * com.wjs.website.controller <br>
 * Copyright (c) 2016.
*/
package com.md.utils;


import com.alibaba.fastjson.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;

/**
 * json工具类
 * <p>
 *
 * @version  V1.0.0
 */

public class JsonUtil {
	/**
	 * 
	 * 将数据转换为json格式(用于app)
	 * <p>
	 *
	 * @param model
	 * @param response
	 * @return 
	 */
	public static void convertPageToJson(Object model, HttpServletResponse response){
		MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();  
		MediaType jsonMimeType = MediaType.APPLICATION_JSON; 
		 try {
			jsonConverter.write(model, jsonMimeType, new ServletServerHttpResponse(response));
		} catch ( Exception e ) {
			e.printStackTrace();
		} 
	}


	/**
	 * 将 JSON对象 或是jsonString 转换成  HashMap<String,Object>
	 * <p>
	 *
	 * @param json
	 * @return HashMap<String,Object>
	 */
	public static HashMap<String, Object> toHashMap(JSONObject json) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		// 将json字符串转换成jsonObject
		JSONObject jsonObject = json;
		Iterator it = jsonObject.keySet().iterator();
		// 遍历jsonObject数据，添加到Map对象
		while (it.hasNext())
		{
			String key = String.valueOf(it.next());
			Object value = jsonObject.get(key);
			data.put(key, value);
		}
		return data;
	}

}
