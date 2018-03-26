package com.huying.bos.service.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huying.bos.dao.system.UserRepository;
import com.huying.bos.domain.system.User;

@Component
public class UserRealm extends AuthorizingRealm{

	@Autowired
	private  UserRepository userRepository;
	
	//授权的方法
	// 该方法会在用户每次请求需要权限校验的资源时调用
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info=new  SimpleAuthorizationInfo();
		//授权
		info.addStringPermission("courierAction_pageQuery");
		//授予角色
		info.addRole("admin");
		return info;
	}

	//认证的方法
	//登录属于认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//根据用户名查找用户 从token里面取出来
     UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken) token;
     String username = usernamePasswordToken.getUsername();
     
     //查找用户
     User user = userRepository.findByUsername(username);
     
     //找到比对密码
     if (user != null) {
		//有这个用户
    	 /*@param principal
		 *            当事人,主体.往往传递从数据库中查询出来的用户对象                         
		 * @param credentials
		 *            凭证,密码(是从数据库中查询出来的密码)
		 * @param realmName  */

    	 AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    	 //比对成功，处理后面逻辑
    	 //比对失败抛异常
    	 return info;
	}
     //找不到抛异常
		return null;
	}

}
