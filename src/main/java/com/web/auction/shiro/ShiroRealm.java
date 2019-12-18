package com.web.auction.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.web.auction.pojo.Auctionuser;
import com.web.auction.service.UserService;

public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
	
		//获取用户输入的账号
		String username=(String) token.getPrincipal();
				
		Auctionuser auctionuser = userService.findUserByUserName(username);
		if(auctionuser == null ) {
			return null;
		}
		
		String password_db = auctionuser.getUserpassword();
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(auctionuser,password_db,"ShiroRealm");
		return info;
	}
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

}
