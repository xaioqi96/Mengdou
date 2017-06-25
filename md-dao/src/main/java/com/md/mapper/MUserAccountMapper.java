package com.md.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.md.base.BaseMapper;

/**
 * MUserAccountMapper数据库操作接口类
 */
@Repository
public interface MUserAccountMapper<T, Q> extends BaseMapper<T, Q> {


    public Q getAccountByUserId(@Param("userId") String userId);
}