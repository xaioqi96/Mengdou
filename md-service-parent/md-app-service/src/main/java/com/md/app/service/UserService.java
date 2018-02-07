package com.md.app.service;

import com.alibaba.fastjson.JSONObject;
import com.md.pages.Page;
import com.md.utils.AjaxResult;

/**
 *
 * 用户相关
 *
 * @date 2017年3月14日
 * @version V1.0.0
 */
public interface UserService {


    /**
     * 注册
     * */
    public void saveUser(JSONObject requestData, AjaxResult result);


    /**
     * 登陆
     * */
    public void login(JSONObject requestData, AjaxResult result);

    /**
     * 个人中心
     * */
    public void userCenter(JSONObject requestData, AjaxResult result);
    
    /**
     * 注册短信验证码接口
     * @param requestData
     * @param result
     */
    public void sendsms(JSONObject requestData, AjaxResult result);
}
