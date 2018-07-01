package com.demo.controller;


import com.demo.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class BookController {
	
	
	@RequestMapping(value="sumbitLogin", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	@ResponseBody
	public String sumbitLogin(User user){
		Subject subject = SecurityUtils.getSubject();
		System.out.println(user.toString());
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
		//登陆验证
		try{
			token.setRememberMe(user.isRememberMe());
			subject.login(token);
		}catch (AuthenticationException e) {
			return e.getMessage();
		}
		//角色验证
		try{
//			List<String> roleIdentifiers = new ArrayList<>();
//			roleIdentifiers.add("admin");
//		    if(subject.hasRole("admin")){
//		    	System.out.println("角色验证成功.");
//		    }else{
//		    	System.out.println("角色验证失败.");
//		    }
			subject.checkRoles("user","admin");
			System.out.println("角色验证成功.");
		    
		}catch (Exception e) {
			System.out.println("角色验证失败.");
			return e.getMessage();
		}
		
		//验证权限
		try{
			subject.checkPermissions("user:list","admin:list");
		    System.out.println("权限验证成功.");
		}catch (Exception e) {
			System.out.println("权限验证失败.");
			return e.getMessage();
		}
		return "登录成功.";
	}
	
	
	
	//////////////////////////////////////
	//通过注解配置授权
    //////////////////////////////////////
	
	@RequiresRoles("admin")
	@RequestMapping(value="testRole", method=RequestMethod.GET, produces="application/json;charset=utf-8")
	@ResponseBody
	public String testRole(){
		return "testRole success.";
	}

	@RequiresRoles("superAdmin")
	@RequestMapping(value="testRole1", method=RequestMethod.GET, produces="application/json;charset=utf-8")
	@ResponseBody
	public String testRole1(){
		return "testRole success.";
	}
	
	
	@RequiresPermissions({"user:list","admin:list"})
	@RequestMapping(value="testPermission", method=RequestMethod.GET, produces="application/json;charset=utf-8")
	@ResponseBody
	public String testPermission(){
		return "testPermission success.";
	}
	
	
	@RequiresPermissions({"user:list","admin:list","superAdmin:list"})
	@RequestMapping(value="testPermission1", method=RequestMethod.GET, produces="application/json;charset=utf-8")
	@ResponseBody
	public String testPermission1(){
		return "testPermission success.";
	}
	
	//////////////////////////////////////
	//通过过滤器配置权限
    //////////////////////////////////////
	@RequestMapping(value="testFilterRole", method=RequestMethod.GET, produces="application/json;charset=utf-8")
	@ResponseBody
	public String testFilterRole(){
		return "testRole success.";
	}

	@RequestMapping(value="testFilterRole1", method=RequestMethod.GET, produces="application/json;charset=utf-8")
	@ResponseBody
	public String testFilterRole1(){
		return "testRole success.";
	}

	
	@RequestMapping(value="testFilterPermission", method=RequestMethod.GET, produces="application/json;charset=utf-8")
	@ResponseBody
	public String testFilterPermission(){
		return "testRole success.";
	}

	@RequestMapping(value="testFilterPermission1", method=RequestMethod.GET, produces="application/json;charset=utf-8")
	@ResponseBody
	public String testFilterPermission1(){
		return "testRole success.";
	}	
	
	
	@RequestMapping(value="testFilterUerRole", method=RequestMethod.GET, produces="application/json;charset=utf-8")
	@ResponseBody
	public String testFilterUerRole(){
		return "testRole success.";
	}

	@RequestMapping(value="testFilterUerRole1", method=RequestMethod.GET, produces="application/json;charset=utf-8")
	@ResponseBody
	public String testFilterUerRole1(){
		return "testRole success.";
	}	
	
	
}
