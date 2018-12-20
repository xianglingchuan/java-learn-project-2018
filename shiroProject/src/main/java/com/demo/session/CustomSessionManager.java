package com.demo.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import java.io.Serializable;

/**
 *
 * 自定义WebSessionManager
 *
 */
public class CustomSessionManager extends DefaultWebSessionManager {

    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
    	
    	Serializable sessionId = getSessionId(sessionKey);
    	ServletRequest request = null;
    	
    	if(sessionKey instanceof WebSessionKey){
        	request = ((WebSessionKey)sessionKey).getServletRequest();    		
    	}
    	if(request!=null && sessionId!=null){
    		Session session =  (Session) request.getAttribute(sessionId.toString());
    		if(session!=null){
    			return session;
    		}
    	}
    	Session session = super.retrieveSession(sessionKey);
    	if(request!=null && sessionId!=null){
    		request.setAttribute(sessionId.toString(), session);
    	}
    	return session;
    }
}

