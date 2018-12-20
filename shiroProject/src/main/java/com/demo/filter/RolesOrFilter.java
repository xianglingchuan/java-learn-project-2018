package com.demo.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class RolesOrFilter extends AuthorizationFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object arg2) throws Exception {

		System.out.println("isAccessAllowed........");
		Subject subject = getSubject(servletRequest, servletResponse);
		String[] roles = (String[]) arg2;
		//如果没有角色直接返回真
		if(roles==null || roles.length == 0){
			return true;
		}
		for (String role : roles) {
			if(!subject.hasRole(role)){
				return false;
			}
		}
		return true;
	}

}
