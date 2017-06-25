package com.md.controller;

import com.alibaba.fastjson.JSONObject;
import com.md.app.service.IndexService;
import com.md.app.service.UserService;
import com.md.pages.Page;
import com.md.utils.AjaxResult;
import com.md.utils.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * 初始页面方法
 *
 * @date 2017年3月14日
 * @version V1.0.0
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

    protected Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;


    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(HttpServletRequest request ) {
        AjaxResult result = new AjaxResult();

        try {
            JSONObject requestData = (JSONObject) request.getAttribute("requestData");
            userService.saveUser(requestData,result);

        } catch ( Exception e ) {
            logger.error("注册-/user/save", e);
            result.setMessage(ResponseCode.REQUEST_ERROR.getDesc());
            result.setStatusCode(ResponseCode.REQUEST_ERROR.getCode());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public AjaxResult login(HttpServletRequest request ) {
        AjaxResult result = new AjaxResult();

        try {
            JSONObject requestData = (JSONObject) request.getAttribute("requestData");
            userService.login(requestData,result);

        } catch ( Exception e ) {
            logger.error("注册-/user/save", e);
            result.setMessage(ResponseCode.REQUEST_ERROR.getDesc());
            result.setStatusCode(ResponseCode.REQUEST_ERROR.getCode());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/center", method = RequestMethod.POST)
    public AjaxResult userCenter(HttpServletRequest request ) {
        AjaxResult result = new AjaxResult();

        try {
            JSONObject requestData = (JSONObject) request.getAttribute("requestData");
            userService.userCenter(requestData,result);

        } catch ( Exception e ) {
            logger.error("个人中心-/user/center", e);
            result.setMessage(ResponseCode.REQUEST_ERROR.getDesc());
            result.setStatusCode(ResponseCode.REQUEST_ERROR.getCode());
        }
        return result;
    }


}
