package com.rui.tiger.auth.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author CaiRui
 * @date 2019-04-09 12:12
 */
@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private UserDetailsService userDetailsService;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//security5+ 认证默认为表单了也就是http.formLogin()
		http.httpBasic();
	}
}
