package com.rui.tiger.auth.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author CaiRui
 * @date 2019-04-09 12:12
 */
@EnableWebSecurity(debug = true)
@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private UserDetailsService userDetailsService;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//security5+ 认证默认为表单了也就是http.formLogin()
		http.httpBasic();
//		http.httpBasic().disable();
//		http
//				.formLogin()
//				// 登录页面
////				.loginPage("/auth/login")
//				// 登录处理url
//				.loginProcessingUrl("/auth/authorize")
//				.and()
//				.authorizeRequests()    //认证请求
//				.antMatchers("/users/register", "/doRegister", "/login","/auth/login", "/auth/authorize").permitAll()     //除了***能够无认证访问
//				.anyRequest().authenticated()    //任何请求都需要认证
////                .and()
////                .sessionManagement()
////                .invalidSessionUrl("/session/invalid")//session失效跳转地址
////                .maximumSessions(1)//最大session并发数
////                .maxSessionsPreventsLogin(true)//true达到并发数后阻止登录，false 踢掉之前的登录
////                .expiredSessionStrategy(new TigerExpiredSessionStrategy())//并发策略
//				.and()
//				.csrf().disable()

		;     //CSRF跨站请求伪造直接关闭
	}
}
