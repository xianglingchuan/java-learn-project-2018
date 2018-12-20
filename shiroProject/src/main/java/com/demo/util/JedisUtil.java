package com.demo.util;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class JedisUtil {

	@Resource
	private JedisPool jedisPool;


	private Jedis getResource(){
		return jedisPool.getResource();
	}


	//设置内容
	public byte[] set(byte[] key,byte[] value){
		Jedis jedis = getResource();
		try{
			jedis.set(key, value);
			return value;
		}finally{
			jedis.close();
		}
	}

	//设置过期时间,单位秒
	public void expire(byte[] key,int i){
		Jedis jedis = getResource();
		try{
			jedis.expire(key,i);
		}finally{
			jedis.close();
		}
	}

	//根据Key获取内容
	public byte[] get(byte[] key){
		Jedis jedis = getResource();
		try{
			return jedis.get(key);
		}finally{
			jedis.close();
		}
	}

	//删除内容 - 这个方法存在问题
	public byte[] del(byte[] key){
		Jedis jedis = getResource();
		try{
			return jedis.get(key);
		}finally{
			jedis.close();
		}
	}

	//获取所有session前端的redis值
	public Set<byte[]> keys(String sessionPrefix){
		Jedis jedis = getResource();
		try{
			return jedis.keys((sessionPrefix+"*").getBytes());
		}finally{
			jedis.close();
		}
	}

}
