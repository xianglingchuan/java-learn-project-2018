package com.demo.session;

import com.demo.util.JedisUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;



/**
 *
 * RedisSessionDao操作类
 *
 */
public class RedisSessionDao extends AbstractSessionDAO {
	
	@Resource
	private JedisUtil jedisUtil;

	/**
	 * session缓存前端标记
	 */
	private final String SHIRO_SESSION_PREFIX = "demo-session:";
	

	/**
	 * 获取SessionKey值信息
	 * @param key
	 * @return byte[]
	 */
	private byte[] getKey(String key){
		return (SHIRO_SESSION_PREFIX+key).getBytes();
	}

	/**
	 * 保存session
	 * @param session
	 * @return void
	 */
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


	/**
	 * 序列化session信息
	 * @param session
	 * @return Serializable
	 */
	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		saveSession(session);
		return sessionId;
	}

	/**
	 * 反序列化session信息
	 * @param sessionId
	 * @return Session
	 */
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






