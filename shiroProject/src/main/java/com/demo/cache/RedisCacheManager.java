package com.demo.cache;

import javax.annotation.Resource;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

public class RedisCacheManager implements CacheManager {

	@Resource
	private RedisCache redisCache;


	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return redisCache;
	}

}
