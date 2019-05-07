package com.rui.tiger.auth.demo.security;

import com.rui.tiger.auth.core.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 业务模块权限配置实现
 * @author CaiRui
 * @date 2019-05-06 12:16
 */
@Component
@Order(Integer.MAX_VALUE)
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {

	@Override
	public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		config.antMatchers(
				"/user/regist", // 注册请求
				"/error",
				"/connect/*",
				"/auth/*",
				"/signin",
				"/social/signUp",  // app注册跳转服务
				"/swagger-ui.html",
				"/swagger-ui.html/**",
				"/webjars/**",
				"/swagger-resources/**",
				"/v2/**"
		).permitAll();

		/*
		 利用@Order注解实现CommonAuthorizeConfigProvider中的放行路径不需要自定义权限验证
		 */
		config.anyRequest()
				.access("@rbacService.hasPermission(request,authentication)");

		return true;
	}
}
