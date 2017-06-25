package com.md.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.md.base.BaseMapper;

/**
 * MUsersMapper数据库操作接口类
 */
@Repository
public interface MUsersMapper<T, Q> extends BaseMapper<T, Q> {


    public Q getMUsersByPhoneNo(@Param("phoneNo") String phoneNo);

    public Q getMUsersById(@Param("userId") String userId);
}