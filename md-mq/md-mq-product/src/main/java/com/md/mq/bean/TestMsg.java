package com.md.mq.bean;

import java.io.Serializable;

/**
 * Created by ljx on 2017/3/9.
 */
public class TestMsg implements Serializable {


    private String paramJson;

    public TestMsg(){}


    public String getParamJson() {
        return paramJson;
    }

    public void setParamJson(String paramJson) {
        this.paramJson = paramJson;
    }
}
