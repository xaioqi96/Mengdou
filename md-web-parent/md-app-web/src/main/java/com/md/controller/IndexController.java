package com.md.controller;

import com.alibaba.fastjson.JSONObject;
import com.md.app.service.IndexService;
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
@RequestMapping("/index/")
public class IndexController extends BaseController{

    protected Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Resource
    private IndexService indexService;

    @RequestMapping("home")
    public String getHome(){
        System.out.println("1111111111111111111111");
        indexService.getHome();
        return "home";
    }

    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public AjaxResult test(HttpServletRequest request ) {
        AjaxResult result = new AjaxResult();

        try {
            Page page = this.getPage();
            JSONObject requestData = (JSONObject) request.getAttribute("requestData");
            indexService.getHome(requestData,page,result);

        } catch ( Exception e ) {
            logger.error("test", e);
            result.setMessage(ResponseCode.REQUEST_ERROR.getDesc());
            result.setStatusCode(ResponseCode.REQUEST_ERROR.getCode());
        }
        return result;
    }


}
