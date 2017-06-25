package com.md.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.md.base.CommonController;
import com.md.pages.Page;
import com.md.utils.ObjectUtils;
import com.md.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 16525 on 2017/5/23.
 */
public class BaseController extends CommonController{


    /**
     * 获取分页对象
     * <p>
     *
     */
    @Override
    protected Page getPage() {
        return this.getPage(DEF_PAGE_VIEW_SIZE);
    }
    @Override
    protected Page getPage( int pageSize ) {
        HttpServletRequest request = this.getRequest();
        int _pageNum = 1; // 当前页码
        int _pageSize = pageSize; // 每页要显示的记录数

        String info = request.getParameter("info");
        JSONObject infoJson = null;
        // 从前台动态获取当前页码、每页显示数
        Integer pageNum = 1;
        Integer size = DEF_PAGE_VIEW_SIZE;
        if ( StringUtils.isNotEmpty(info) ) {
            infoJson = JSON.parseObject(info);
            pageNum = infoJson.getInteger("pageIndex");
            size = infoJson.getInteger("pageSize");
        }

        if ( ObjectUtils.isNotNull(pageNum) ) {
            _pageNum = pageNum;
        }
        if ( ObjectUtils.isNotNull(size) ) {
            _pageSize = size;
        }

        Page page = new Page(_pageNum, _pageSize);
        return page;
    }

}
