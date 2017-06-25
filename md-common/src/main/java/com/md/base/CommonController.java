package com.md.base;

import javax.servlet.http.HttpServletRequest;

import com.md.pages.Page;
import com.md.utils.AjaxResult;
import com.md.utils.ResponseCode;
import com.md.utils.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;


/**
 * <p>
 *
 * @version  V1.0.0
 */
public abstract class CommonController {
    /**
     * 默认每页显示数量
     */
    protected static final int DEF_PAGE_VIEW_SIZE = 10;


    /**
     * 获取request对象
     * @return
     */
    protected HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return request;
    }


    public static String callback( AjaxResult result ) {
        return JSON.toJSONString(result);
    }


    public static String callbackSuccess( AjaxResult result ) {
        result.setStatusCode(ResponseCode.SUCCESS.getCode());
        return JSON.toJSONString(result);
    }


    /**
     * 获取分页对象
     * <p>
     *
     */
    protected Page getPage() {
        return this.getPage(DEF_PAGE_VIEW_SIZE);
    }


    protected Page getPage( int pageSize ) {
        HttpServletRequest request = this.getRequest();
        int _pageNum = 1; // 当前页码
        int _pageSize = pageSize; // 每页要显示的记录数

        // 从前台动态获取当前页码、每页显示数
        String pageNum = String.valueOf(request.getParameter("page"));
        String size = String.valueOf(request.getParameter("rows"));

        if ( StringUtils.isNotEmpty(pageNum) ) {
            _pageNum = Integer.parseInt(pageNum);
        }
        if ( StringUtils.isNotEmpty(size) ) {
            _pageSize = Integer.parseInt(size);
        }

        Page page = new Page(_pageNum, _pageSize);
        return page;
    }
    protected String redirectTo( String url ) {
        StringBuffer rto = new StringBuffer("redirect:");
        rto.append(this.getRequest().getContextPath());
        rto.append(url);
        return rto.toString();
    }
}
