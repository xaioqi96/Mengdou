package com.md.base;

import com.md.pages.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 所有的Mapper继承这个接口
 * 已经实现基本的增、删、改、查接口,不需要重复写
 *
 * @date	 207年3月14日
 * @version  V1.0.0
 */
public interface BaseMapper<T, Q> {

	public int insert(T t);


	public int update(T t);


	public int delete(Q query);


	public List<T> selectList(Q query);


	public List<T> selectPageList(@Param("query") Q query, @Param("page") Page page);

}
