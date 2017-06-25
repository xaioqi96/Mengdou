package com.md.redis;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * redis服务接口
 * <p>
 *
 * @author   GPZ
 * @date	 2016年9月12日
 * @version  V1.0.0
 */
public interface RedisService {

	public Jedis getJedis();


	/**
	 * 添加key-value
	 * @param key
	 * @param value
	 */
	public void addString(String key, String value);


	/**
	 * 根据可以获取Value
	 * @param key
	 * @return
	 */
	public String getValue(String key);


	/**
	 * 根据key删除Value
	 * @param key
	 */
	public void del(String key);


	/**
	 * 添加map
	 * @param key
	 * @param map
	 */
	public void addMap(String key, Map<String, String> map);


	/**
	 * 根据key获取Map的值
	 * @param key
	 * @param mapKey
	 * @return
	 */
	public String getMapValue(String key, String mapKey);


	/**
	 * 获取Map集合中所有的key
	 * @param key
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Set getMapKeys(String key);


	/**
	 * 获取Map集合中所以的value值
	 * @param key
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getMapValues(String key);


	/**
	 * 添加指定Map的key,value
	 * @param key
	 * @param mapkey
	 * @param mapValue
	 */
	public Long addMapValue(String key, String mapkey, String mapValue);


	/**
	 * 根据指定Map的key修改value
	 * @param key
	 * @param mapkey
	 * @param mapValue
	 */
	public void updateMapValue(String key, String mapkey, String mapValue);
	/**
	 * 
	 * 根据key获取对应map
	 * <p>
	 *
	 * @param key
	 * @return Map<String, String>
	 */
	public Map<String, String> hgetAll(String key);


	/**
	 * 添加对象
	 * @param key
	 * @param list
	 */
	public String addObject(String key, Object obj);


	/**
	 * 获取对象信息
	 * @param key
	 * @return
	 */
	public Object getObject(String keyId);


	/**
	 * 检查key是否存在
	 * @param key
	 * @return
	 */
	public boolean exists(String key);


	/**
	 * 根据key自增
	 * @param key
	 * @param increment
	 * @return
	 */
	public Long incrBy(String key, long increment);


	/**
	 * 监控
	 * @param key
	 */
	public void watch(String key);


	/**
	 *设置过期时间
	 * @param key
	 * @param seconds  单位:秒
	 */
	public void expire(String key, int seconds) ;
	
	/**
	 * 
	 * 向set里面添加元素
	 * <p>
	 *
	 * @param setName
	 * @param member 
	 */
	public Long sadd(String setName, String... members);
	/**
	 * 
	 * 添加设置过期时间的key-value
	 * <p>
	 *
	 * @param key
	 * @param value
	 * @param seconds 
	 */
	public void addExpireString(String key, String value, int seconds);
	/**
	 * 
	 * 添加设置过期时间的对象
	 * <p>
	 *
	 * @param key
	 * @param obj
	 * @param seconds 
	 */
	public void addExpireObject(String key, Object obj, int seconds);



}
