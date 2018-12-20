package com.demo;

import cmd.shiro.CustomRealmMD5;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

public class CustomRealmMD5Test {
	

	public void testAuthentication(){
		
		//自定义Reaml
		CustomRealmMD5 customRealm = new CustomRealmMD5();
		
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		//defaultSecurityManager.setRealm(iniRealm);
		//defaultSecurityManager.setRealm(jdbcRealm);
		defaultSecurityManager.setRealm(customRealm);
		
		//对Shiro进行加密
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName("md5");
		matcher.setHashIterations(1);
		customRealm.setCredentialsMatcher(matcher);
		
		
		
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
    public static void main(String[] args) {
		//Md5Hash hash = new Md5Hash("111111");
		//对MD5进行加盐
		Md5Hash hash = new Md5Hash("111111","mark");
		System.out.println(hash.toString());
	}
}
