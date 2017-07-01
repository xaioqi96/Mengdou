package com.md.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.md.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;

/**
 * 用户权限认证拦截处理
 * <p>
 *
 * @author WYS
 * @date 2016年9月25日
 * @version V1.0.0
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthInterceptor.class);

	//@Autowired
	//private RedisService redisService;

	@Override
	public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView ) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception {
		String url = request.getRequestURI();
		System.out.println("请求URL---------------" + request.getRequestURL());


		if ( LOGGER.isDebugEnabled() ) {
			Map<String, ?> params = request.getParameterMap();
			Cookie[] cookies = request.getCookies();
			LOGGER.debug("request url:{}", url);
			for ( String key : params.keySet() ) {
				LOGGER.debug("request params:{}=>{}", key, params.get(key));

			}
			if ( cookies != null ) {
				for ( Cookie cookie : cookies ) {
					LOGGER.debug("cookies: {}=>{}", cookie.getName(), cookie.getValue());
				}
			}
		}
		if ( handler instanceof HandlerMethod ) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;

			Integer userId = null;
			String appver = "";
			String source = "";

			int statusCode = 200;
			String message = "";

			Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
			if ( ObjectUtils.isNotNull(auth) ) {
				String authValue = auth.value();
				String userSessionToken = request.getParameter("token");
				if ( StringUtils.isNotEmpty(userSessionToken) ) {
					Integer sessionUserId = this.getUserIdByToken(userSessionToken);
					source = this.getSourceByToken(userSessionToken);
					appver = this.getAppVersionByToken(userSessionToken);
					String redisUserSession = "";
					// todo 需要根据REDIS判断是否登录
					//redisService.getValue(RedisConstant.API_USER_SESSION_KEY_PREFIX + sessionUserId);
					// 判断redis中是否存在当前用户数据
					if ( StringUtils.isNotEmpty(redisUserSession) ) {
						if ( userSessionToken.equals(redisUserSession) ) {
							userId = sessionUserId;
						} else {
							if ( StringUtils.isNotEmpty(authValue) && "login".equalsIgnoreCase(authValue) ) {
								statusCode = ResponseCode.LOGIN_SINGLE.getCode();
								message = ResponseCode.LOGIN_SINGLE.getDesc();
							}
						}
					} else {
						if ( StringUtils.isNotEmpty(authValue) && "login".equalsIgnoreCase(authValue) ) {
							statusCode = ResponseCode.LOGINTIMEOUT.getCode();
							message = "会话超时，请重新登录";
						}

					}
				} else {
					if ( StringUtils.isNotEmpty(authValue) && "login".equalsIgnoreCase(authValue) ) {
						statusCode = ResponseCode.LOGIN_ILLEGAL.getCode();
						message = ResponseCode.LOGIN_ILLEGAL.getDesc();
					}
				}
			}
			// 校验通过则包装用户数据
			this.packageRequestData(request, response, userId, appver, source, statusCode, message);
			if ( statusCode == ResponseCode.SUCCESS.getCode() ) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	public void packageRequestData( HttpServletRequest request, HttpServletResponse response, Integer userId, String appver, String source, int statusCode, String message ) {
		String requestType = request.getMethod();
		JSONObject paramObj = new JSONObject();
		if ( statusCode == ResponseCode.SUCCESS.getCode() ) {
			if ( "POST".equalsIgnoreCase(requestType) ) {
				// auth = "{"osver":"10.0","appver":"2.3.9","source":"4","version":"20"}";
				SmartMap<String, Object> paramMap = this.getParameterMap(request);
				paramObj = (JSONObject) JSONObject.toJSON(paramMap);
			}
			if ( ObjectUtils.isNotNull(userId) ) {
				paramObj.put("userId", userId);
			} else {
				paramObj.put("userId", 0);
			}

			if ( StringUtils.isNotEmpty(appver) ) {
				paramObj.put("appver", appver);
			}

			if ( StringUtils.isNotEmpty(source) ) {
				paramObj.put("source", source);
			}

			request.setAttribute("requestData", paramObj);
		} else {
			paramObj = new JSONObject();
			paramObj.put("statusCode", statusCode);
			paramObj.put("message", message);
			try {
				PrintWriter out = response.getWriter();
				out.write(paramObj.toString());
			} catch ( IOException e ) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * 根据Token获取用户Id
	 * <p>
	 *
	 * @param token
	 * @return 返回用户Id
	 */
	public Integer getUserIdByToken( String token ) {
		String str = "";
		Integer userId = -1;
		try {
			str = new String(Base64Util.decode(token));
			String[] arrays = str.split("#");
			userId = Integer.valueOf(arrays[0]);
		} catch ( Exception e ) {
			LOGGER.debug("获取用户Id出现错误,{}" + e.getMessage());
		}

		return userId;
	}

	/**
	 * 根据Token获取请求source
	 * <p>
	 *
	 * @param token
	 * @return
	 */
	public String getSourceByToken( String token ) {
		String str = "";
		String source = "";
		try {
			str = new String(Base64Util.decode(token));
			String[] arrays = str.split("#");
			source = arrays[3];
		} catch ( Exception e ) {
			LOGGER.debug("获取请求source出现错误,{}" + e.getMessage());
		}
		return source;
	}

	/**
	 * 根据Token获取app版本
	 * <p>
	 *
	 * @param token
	 * @return
	 */
	public String getAppVersionByToken( String token ) {
		String str = "";
		String appver = "";
		try {
			str = new String(Base64Util.decode(token));
			String[] arrays = str.split("#");
			appver = arrays[2];
		} catch ( Exception e ) {
			LOGGER.debug("获取请求source出现错误,{}" + e.getMessage());
		}
		return appver;
	}

	protected SmartMap<String, Object> getParameterMap(HttpServletRequest request ) {
		SmartMap<String, Object> result = new SmartMap<String, Object>();
		Map<String, String[]> param = request.getParameterMap();
		for ( Map.Entry<String, String[]> ele : param.entrySet() ) {
			if ( ele.getValue().length == 1 ) {
				result.put(ele.getKey(), ele.getValue()[0]);
			} else {
				result.put(ele.getKey(), ele.getValue());
			}
		}
		return result;
	}

}
