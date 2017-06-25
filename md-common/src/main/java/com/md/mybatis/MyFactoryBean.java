/**
 * MyFactoryBean.java <br>
 * com.wjs.mybatis <br>
 * Copyright (c) 2017.
 */
package com.md.mybatis;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.NestedIOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;


/**
 * 解决MyBatis的Mapper XML错误，系统起不来，也不报错问题
 * <p>
 *
 * @author   WYS
 * @date	 2017年1月5日
 * @version  V1.0.0
 */
public class MyFactoryBean extends SqlSessionFactoryBean {

	private static Logger logger = LoggerFactory.getLogger(MyFactoryBean.class);


	/**
	 * 重写buildSqlSessionFactory()方法，抛出MyBatis XML编译中的错误。
	 * <p>
	 *
	 * @return
	 * @throws IOException 
	 */
	@Override
	protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
		try {
			return super.buildSqlSessionFactory();
		} catch ( NestedIOException e ) {
			System.out.println("编译MyBatis的XML出现错误：" + e.getMessage());
			logger.error("编译MyBatis的XML出现错误：{}", e);// XML 有错误时打印异常。  
			throw new NestedIOException("Failed to parse mapping resource: ", e.getCause());
		} finally {
			ErrorContext.instance().reset();
		}
	}

}
