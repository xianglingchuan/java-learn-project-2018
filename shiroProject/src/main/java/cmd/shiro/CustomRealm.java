package cmd.shiro;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import org.springframework.jdbc.core.JdbcTemplate;

public class CustomRealm extends AuthorizingRealm {
	
	Map<String, String> userMap = new HashMap<String, String>();
	{
		userMap.put("Mark", "111111");
		super.setName("customRealm");
	}

	//用户授权使用
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		String userName = (String) principals.getPrimaryPrincipal();
		//从数据库或缓存中查询角色或功能权限
		Set<String> roles = getRolesByUserName(userName);
		Set<String> permissions = getPermissionByUserName(userName);
		
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.setRoles(roles);
		simpleAuthorizationInfo.setStringPermissions(permissions);
		return simpleAuthorizationInfo;
	}

	private Set<String> getPermissionByUserName(String userName) {
		Set<String> permissions = new TreeSet<String>();
		permissions.add("user:list");
		permissions.add("admin:list");
		return permissions;
	}

	private Set<String> getRolesByUserName(String userName) {
		Set<String> roles = new TreeSet<String>();
		roles.add("admin");
		roles.add("user");
		return roles;
	}

	//用于认证使用
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//1、从主体传过来的认证信息中获取用户名
		String userName = (String) token.getPrincipal();
		
		//2、通过用户名到数据库中获取凭证信息
		String password = getPasswordByUserName(userName);
		if(password==null){
			return null;
		}
		SimpleAuthenticationInfo authenticationInfo 
		= new SimpleAuthenticationInfo(userName,password,"customRealm");
		return authenticationInfo;
	}

	//这里模拟查询数据库信息
	private String getPasswordByUserName(String userName) {
		return userMap.get(userName);
	}

}








