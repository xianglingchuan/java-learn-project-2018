package com.demo.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import javax.annotation.Resource;

/**
 *
 * 缓存权限信息 - Redis缓存类
 *
 */
public class RedisCacheManager implements CacheManager {

	/**
	 *
	 * Redis资源操作类
	 *
	 */
	@Resource
	private RedisCache redisCache;

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return redisCache;
	}

}
