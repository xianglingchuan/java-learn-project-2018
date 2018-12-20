package com.demo;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;


public class AuthorizerTest {
	
	public SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
	

	public void addUser(){
		//给用户赋与两个角色
		simpleAccountRealm.addAccount("Make", "123456","admin","superAdmin");
	}
	

	public void testAuthentication(){
		//1、构建SecurityManager环境
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		defaultSecurityManager.setRealm(simpleAccountRealm);
		
		//2、主体提交认证请求
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token = new UsernamePasswordToken("Make","123456");
		//用户名错误异常
		//org.apache.shiro.authc.UnknownAccountException: Realm [org.apache.shiro.realm.SimpleAccountRealm@5479e3f] was unable to find account data for the submitted AuthenticationToken [org.apache.shiro.authc.UsernamePasswordToken - Make1, rememberMe=false].
		//密码错误异常
		//org.apache.shiro.authc.IncorrectCredentialsException: Submitted credentials for token [org.apache.shiro.authc.UsernamePasswordToken - Make, rememberMe=false] did not match the expected credentials.
		subject.login(token);
		System.out.println("isAuthenticated:"+subject.isAuthenticated());
		
		
		subject.checkRoles("admin","superAdmin11");
		//角色验证失败异常
		//org.apache.shiro.authz.UnauthorizedException: Subject does not have role [superAdmin11]
		
		//注销用户
		//subject.logout();
		//System.out.println("isAuthenticated:"+subject.isAuthenticated());
	}
}
