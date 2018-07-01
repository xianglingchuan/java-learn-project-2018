package com.demo.cache;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import com.demo.util.JedisUtil;

@Component
public class RedisCache<K,V> implements Cache<K, V> {
	
	@Resource
	private JedisUtil jedisUtil;
	
	
	private final String CACHE_PREFIX = "demo-cache";
	
	private byte[] getKey(K k){
		if(k instanceof String){
			return (CACHE_PREFIX+k).getBytes();
		}
		return SerializationUtils.serialize(k);
		
	}
	

	@Override
	public V get(K key) throws CacheException {
		System.out.println("从redis获取权限数据");
		byte[] value = jedisUtil.get(getKey(key));
		if(value!=null){
			return (V) SerializationUtils.deserialize(value);
		}
		return null;
	}

	@Override
	public V put(K key, V value) throws CacheException {
		byte[] keyByte = getKey(key);
		byte[] valueByte = SerializationUtils.serialize(value);
		jedisUtil.set(keyByte, valueByte);
		jedisUtil.expire(keyByte, 600);
		return value;
	}

	@Override
	public V remove(K key) throws CacheException {
		byte[] keyByte = getKey(key);
		byte[] valueByte = jedisUtil.get(keyByte);
		jedisUtil.del(keyByte);
		if(valueByte!=null){
			return (V) SerializationUtils.deserialize(valueByte);
		}
		return null;
	}

	@Override
	public void clear() throws CacheException {
		//最好不要实现，因为这里清空的是整个Reids中的内容
		
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public Set<K> keys() {
		return null;
	}

	@Override
	public Collection<V> values() {
		return null;
	}

}
