package com.rui.tiger.auth.core.authorize;

import com.rui.tiger.auth.core.properties.SecurityConstants;
import com.rui.tiger.auth.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 通用权限接口配置管理
 * @author CaiRui
 * @date 2019-05-06 12:08
 */
@Component
@Slf4j
@Order(Integer.MIN_VALUE)
public class CommonAuthorizeConfigProvider implements AuthorizeConfigProvider  {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

		config.antMatchers(
				SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,//权限认证
				SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,//手机
				SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_OPENID,//openId
				securityProperties.getBrowser().getLoginPage(),//登录页面
				SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",// /captcha/* 验证码放行
				securityProperties.getBrowser().getSignupUrl(),
				securityProperties.getBrowser().getLoginOut(),
				securityProperties.getBrowser().getSession().getInvalidSessionUrl()
		).permitAll();//放行

		return false;
	}
}
