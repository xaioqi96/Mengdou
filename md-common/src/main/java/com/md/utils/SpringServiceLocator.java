package com.md.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * 获取service实例
 * <p>
 *
 * @version  V1.0.0
 */
public class SpringServiceLocator implements ApplicationContextAware {
	/**
     * 当前IOC
     */
    private static ApplicationContext applicationContext;

    /**
     * 设置当前上下文环境，此方法由spring自动装配
     */
    public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
        SpringServiceLocator.applicationContext = applicationContext;
    }

    /**
     * 从当前IOC获取bean
     * 
     * @param id
     *            bean的id
     * @return
     */
    public static Object getObject(String id) {
        Object object = null;
        object = applicationContext.getBean(id);
        return object;
    }
}
