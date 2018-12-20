package cmd.shiro;

import com.demo.dao.UserDao;
import com.demo.entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;


/**
 *
 *
 */
public class CustomRealmMD5DB extends AuthorizingRealm {
	
	@Resource
	private UserDao userDao;
	
	Map<String, String> userMap = new HashMap<String, String>();
	{
		//密码进行了加密
		//userMap.put("Mark", "96e79218965eb72c92a549dd5a330112");
		//密码进行加盐后
		//userMap.put("Mark", "85ba00896f2b52db37560294050bcca0");
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
		List<String> permissionsList = userDao.getPermissionByUserName(userName);
		if(CollectionUtils.isEmpty(permissionsList)){
			return null;
		}
		Set<String> permissions = new HashSet(permissionsList);
		return permissions;
	}

	
	private Set<String> getRolesByUserName(String userName) {
		System.out.println("从数据库中获取授权数据");
		List<String> rolesList = userDao.getRolesByUserName(userName);
		if(CollectionUtils.isEmpty(rolesList)){
			return null;
		}
		Set<String> roles =  new HashSet(rolesList);
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
		
		//这里要将加盐的密码返回
		authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(userName));
		return authenticationInfo;
	}

	
	/**
	 * 
	 * 从数据库中查询
	 * 
	 * */
	private String getPasswordByUserName(String userName) {
		User user =  userDao.getPasswordByUserName(userName);
		if(user!=null){
			return user.getPassword();
		}
		return null;
	}
	
	public static void main(String[] args) {
		Md5Hash hash = new Md5Hash("111111", "xlc");
		System.out.println("hash:"+hash.toString());
	}
}








