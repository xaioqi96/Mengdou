package com.md.redis;

import com.md.utils.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Lazy
@Service
public class RedisServiceImpl implements RedisService {

	@Autowired
	public JedisPool jedisPool;


	public JedisPool getJedisPool() {

		return jedisPool;
	}

	public void setJedisPool( JedisPool jedisPool ) {
		this.jedisPool = jedisPool;
	}




	@Override
	public String getValue( String key ) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.get(key);
		} catch ( Exception e ) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
			return null;
		} finally {
			returnResource(jedisPool, jedis);
		}
	}


	@Override
	public void del( String key ) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(key);
		} catch ( Exception e ) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			returnResource(jedisPool, jedis);
		}
	}


	@Override
	public void addMap( String key, Map<String, String> map ) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.hmset(key, map);
		} catch ( Exception e ) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			returnResource(jedisPool, jedis);
		}
	}


	@Override
	public String getMapValue( String key, String mapKey ) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.hget(key, mapKey);
		} catch ( Exception e ) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
			return null;
		} finally {
			returnResource(jedisPool, jedis);
		}
	}


	@Override
	public Long addMapValue( String key, String mapkey, String mapValue ) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.hset(key, mapkey, mapValue);
		} catch ( Exception e ) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
			return null;
		} finally {
			returnResource(jedisPool, jedis);
		}
	}


	@Override
	public Set getMapKeys( String key ) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.hgetAll(key).keySet();
		} catch ( Exception e ) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
			return null;
		} finally {
			returnResource(jedisPool, jedis);
		}
	}


	@Override
	public List getMapValues( String key ) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void updateMapValue( String key, String mapkey, String mapValue ) {
		// TODO Auto-generated method stub

	}


	@Override
	public String addObject( String key, Object obj ) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.set(key.getBytes(), SerializeUtil.serialize(obj));
		} catch ( Exception e ) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
			return null;
		} finally {
			returnResource(jedisPool, jedis);
		}
	}


	@Override
	public Object getObject( String key ) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return SerializeUtil.unserialize(jedis.get(key.getBytes()));
		} catch ( Exception e ) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
			return null;
		} finally {
			returnResource(jedisPool, jedis);
		}
	}


	@Override
	public boolean exists( String key ) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.exists(key);
		} catch ( Exception e ) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
			return false;
		} finally {
			returnResource(jedisPool, jedis);
		}
	}


	@Override
	public Long incrBy( String key, long increment ) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.incrBy(key, increment);
		} catch ( Exception e ) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
			return null;
		} finally {
			returnResource(jedisPool, jedis);
		}
	}


	@Override
	public void watch( String key ) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.watch(key);
		} catch ( Exception e ) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			returnResource(jedisPool, jedis);
		}
	}


	@Override
	public Jedis getJedis() {
		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();
		} catch ( Exception e ) {

			System.out.println("#############################");
			System.out.println("jedisPool异常");
			System.out.println("#############################");

			e.printStackTrace();
		} finally {
			jedisPool.returnResource(jedis);
		}

		return jedis;
	}

	@Override
	public void addString( String key, String value ) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value);
		} catch ( Exception e ) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			returnResource(jedisPool, jedis);
		}

	}



	@Override
	public void expire( String key, int seconds ) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.expire(key, seconds);
		} catch ( Exception e ) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			returnResource(jedisPool, jedis);
		}
	}


	@Override
	public Long sadd( String setName, String... members ) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.sadd(setName, members);
		} catch ( Exception e ) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
			return null;
		} finally {
			returnResource(jedisPool, jedis);
		}
	}


	@Override
	public Map<String, String> hgetAll( String key ) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.hgetAll(key);
		} catch ( Exception e ) {
			jedisPool.returnBrokenResource(jedis);
			e.printStackTrace();
			return null;
		} finally {
			returnResource(jedisPool, jedis);
		}
	}


	/**
	 * 返还到连接池
	 *
	 * @param pool
	 * @param redis
	 */
	public static void returnResource( JedisPool pool, Jedis redis ) {
		if ( redis != null ) {
			pool.returnResource(redis);
		}
	}



	@Override
	public void addExpireString( String key, String value, int seconds ) {
		addString(key, value);
		expire(key, seconds);
	}



	@Override
	public void addExpireObject( String key, Object obj, int seconds ) {
		addObject(key, obj);
		expire(key, seconds);
	}

}
