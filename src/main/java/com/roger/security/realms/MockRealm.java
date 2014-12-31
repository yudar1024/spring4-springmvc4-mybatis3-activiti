package com.roger.security.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MockRealm extends AuthorizingRealm  {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo("roger","password","mockrealm");

		if(this.getCredentialsMatcher().doCredentialsMatch(token,info)){
			return info;
		}else{
			throw new AuthenticationException("username or password incorrect !");
		}
	}



}
