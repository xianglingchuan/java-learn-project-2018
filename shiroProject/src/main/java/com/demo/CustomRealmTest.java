package com.demo;

import cmd.shiro.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

public class CustomRealmTest {
	

	public void testAuthentication(){
		
		//自定义Reaml
		CustomRealm customRealm = new CustomRealm();
		
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		//defaultSecurityManager.setRealm(iniRealm);
		//defaultSecurityManager.setRealm(jdbcRealm);
		defaultSecurityManager.setRealm(customRealm);
		
		
		
		//2、主体提交认证请求
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token = new UsernamePasswordToken("Mark","111111");
		//用户名错误异常
		//org.apache.shiro.authc.UnknownAccountException: Realm [org.apache.shiro.realm.SimpleAccountRealm@5479e3f] was unable to find account data for the submitted AuthenticationToken [org.apache.shiro.authc.UsernamePasswordToken - Make1, rememberMe=false].
		//密码错误异常
		//org.apache.shiro.authc.IncorrectCredentialsException: Submitted credentials for token [org.apache.shiro.authc.UsernamePasswordToken - Make, rememberMe=false] did not match the expected credentials.
		subject.login(token);
		System.out.println("isAuthenticated:"+subject.isAuthenticated());
		
		//验证角色
		//subject.checkRoles("admin");
		subject.checkRoles("admin","user");
		
		//验证功能
		subject.checkPermissions("user:list", "admin:list");
	}	
}
