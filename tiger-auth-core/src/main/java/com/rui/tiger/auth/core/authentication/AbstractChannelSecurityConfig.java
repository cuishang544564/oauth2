package com.rui.tiger.auth.core.authentication;

import com.rui.tiger.auth.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 密码登录的通用安全配置
 * @author CaiRui
 * @date 2018-12-26 18:11
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationSuccessHandler tigerAuthenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler tigerAuthenticationFailureHandler;

	/**
	 * 密码登录配置
	 * @param http
	 * @throws Exception
	 */
	protected void 	applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
		http.formLogin()
				.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
				.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)//
				.defaultSuccessUrl("/index.html")
				.successHandler(tigerAuthenticationSuccessHandler)
				.failureHandler(tigerAuthenticationFailureHandler);
	}


}
