package com.mmall.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mmall.common.RedisPool;

import redis.clients.jedis.Jedis;

public class RedisPoolUtil {
	private static final Logger logger = LoggerFactory.getLogger(RedisPoolUtil.class);

	/**
	 * 设置key的有效期，单位秒
	 * 
	 * @param key
	 * @param exTime
	 * @return
	 */
	public static Long expire(String key, int exTime) {
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = RedisPool.getJedis();
			result = jedis.expire(key, exTime);
		} catch (Exception e) {
			logger.error("expire key:{}", key, e);
			RedisPool.returnBrokenResource(jedis);
			return result;
		}
		RedisPool.returnResource(jedis);
		return result;
	}

	public static String setEx(String key, String value, int exTime) {
		Jedis jedis = null;
		String result = null;
		try {
			jedis = RedisPool.getJedis();
			result = jedis.setex(key, exTime, value);
		} catch (Exception e) {
			logger.error("setex key:{} value:{} error", key, value, e);
			RedisPool.returnBrokenResource(jedis);
			return result;
		}

		RedisPool.returnResource(jedis);
		return result;
	}

	public static String set(String key, String value) {
		Jedis jedis = null;
		String result = null;
		try {
			jedis = RedisPool.getJedis();
			result = jedis.set(key, value);
		} catch (Exception e) {
			logger.error("setex key:{} value:{} error", key, value, e);
			RedisPool.returnBrokenResource(jedis);
			return result;
		}

		RedisPool.returnResource(jedis);
		return result;
	}

	public static String get(String key) {
		Jedis jedis = null;
		String result = null;
		try {
			jedis = RedisPool.getJedis();
			result = jedis.get(key);
		} catch (Exception e) {
			logger.error("get key:{} error", key, e);
			RedisPool.returnBrokenResource(jedis);
			return result;
		}
		RedisPool.returnResource(jedis);
		return result;
	}
	/**
	 * 根据key删除操作
	 * @param key
	 * @return
	 */
	public static Long del(String key){
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            logger.error("del key:{} error",key,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }
	
	public static void main(String[] args) {
		Jedis jedis = RedisPool.getJedis();
		RedisPoolUtil.set("keyTest", "value");
		String string = RedisPoolUtil.get("keyTest");
		System.out.println(string);
		
	}

}