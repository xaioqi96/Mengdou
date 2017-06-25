package com.md.app.service;

import com.alibaba.fastjson.JSONObject;
import com.md.pages.Page;
import com.md.utils.AjaxResult;

/**
 *
 * 初始页面方法
 *
 * @date 2017年3月14日
 * @version V1.0.0
 */
public interface IndexService {

    public void getHome();

    /**
     * 首页获得信息
     * */
    public void getHome(JSONObject requestData, Page page, AjaxResult result);
}
