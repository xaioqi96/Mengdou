package com.md.mapper;

import com.md.entity.MQuizQuestions;
import com.md.pages.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.md.base.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * MQuizQuestionsMapper数据库操作接口类
 */
@Repository
public interface MQuizQuestionsMapper<T, Q> extends BaseMapper<T, Q> {


    /**
     * 获得首页信息
     * */
    public List<MQuizQuestions> getQuestionsPageList(@Param("map")Map<String, Object> map, @Param("page")Page Page);
}