package com.demo.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.util.SerializationUtils;

import com.demo.util.JedisUtil;

public class RedisSessionDao extends AbstractSessionDAO {
	
	@Resource
	private JedisUtil jedisUtil;
	
	private final String SHIRO_SESSION_PREFIX = "demo-session:";
	
	//获取SessionKey值信息
	private byte[] getKey(String key){
		return (SHIRO_SESSION_PREFIX+key).getBytes();
	}
	
	//保存session
	private void saveSession(Session session){
		if(session!=null && session.getId()!=null){
			byte[] key = getKey(session.getId().toString());
			byte[] value = SerializationUtils.serialize(session);
			jedisUtil.set(key, value);
			jedisUtil.expire(key, 600);
		}
	}
	
	

	@Override
	public void update(Session session) throws UnknownSessionException {
		saveSession(session);		
	}

	@Override
	public void delete(Session session) {
		if(session==null || session.getId()==null){
			return;
		}
		byte[] key = getKey(session.getId().toString());
		jedisUtil.del(key);
	}

	@Override
	public Collection<Session> getActiveSessions() {
		Set<byte[]> keys = jedisUtil.keys(SHIRO_SESSION_PREFIX);
		Set<Session> sessions = new HashSet<Session>();
		if(CollectionUtils.isEmpty(sessions)){
			return sessions;
		}
		for(byte[] key : keys){
			Session session = (Session) SerializationUtils.deserialize(jedisUtil.get(key));
			sessions.add(session);
		}
		return sessions;
	}

	//序列化session信息
	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		saveSession(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
	     System.out.println("doReadSession ");
	     if(sessionId == null){
	    	 return null;
	     }
	     byte[] key = getKey(sessionId.toString());
	     byte[] value = jedisUtil.get(key);
	     return (Session) SerializationUtils.deserialize(value);
	}

}






