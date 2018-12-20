package com.demo.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * 角色过滤器类
 *
 */
public class RolesOrFilter extends AuthorizationFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object arg2) throws Exception {
		System.out.println("isAccessAllowed........");
		Subject subject = getSubject(servletRequest, servletResponse);
		String[] roles = (String[]) arg2;
		//如果没有角色直接返回真
		System.out.println( "roles.length====>"+roles.length);
		System.out.println( "subject===>"+subject);
		if(roles==null || roles.length == 0){
			return true;
		}
		for (String role : roles) {
			System.out.println( "role=====>"+role );
			if(!subject.hasRole(role)){
				System.out.println( "角色验证失败." );
				return false;
			}else{
				System.out.println( "角色验证成功." );
			}
		}
		return true;
	}
}
