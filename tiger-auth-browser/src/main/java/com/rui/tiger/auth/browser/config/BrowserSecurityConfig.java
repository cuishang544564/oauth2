package com.rui.tiger.auth.browser.config;

import com.rui.tiger.auth.core.authentication.TigerAuthenticationFailureHandler;
import com.rui.tiger.auth.core.authentication.TigerAuthenticationSuccessHandler;
import com.rui.tiger.auth.core.captcha.CaptchaFilter;
import com.rui.tiger.auth.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * 浏览器security配置类
 *
 * @author CaiRui
 * @date 2018-12-4 8:41
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private TigerAuthenticationFailureHandler tigerAuthenticationFailureHandler;
	@Autowired
	private TigerAuthenticationSuccessHandler tigerAuthenticationSuccessHandler;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * 密码加密解密
	 *
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 记住我持久化数据源
	 * JdbcTokenRepositoryImpl  CREATE_TABLE_SQL 建表语句可以先在数据库中执行
	 *
	 * @return
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource);
		//第一次会执行CREATE_TABLE_SQL建表语句 后续会报错 可以关掉
		//jdbcTokenRepository.setCreateTableOnStartup(true);
		return jdbcTokenRepository;
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//加入图片验证码过滤器
		CaptchaFilter captchaFilter = new CaptchaFilter();
		captchaFilter.setFailureHandler(tigerAuthenticationFailureHandler);
		captchaFilter.setSecurityProperties(securityProperties);
		captchaFilter.afterPropertiesSet();

		//图片验证码放在认证之前
		http.addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
				.formLogin()
				.loginPage("/authentication/require")//自定义登录请求
				.loginProcessingUrl("/authentication/form")//自定义登录表单请求
				.successHandler(tigerAuthenticationSuccessHandler)
				.failureHandler(tigerAuthenticationFailureHandler)
				.and()
				//记住我相关配置
				.rememberMe()
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(securityProperties.getBrowser().getRemberMeSeconds())
				.userDetailsService(userDetailsService)
				.and()
				.authorizeRequests()
				.antMatchers(securityProperties.getBrowser().getLoginPage(),
						"/authentication/require", "/captcha/image")//此路径放行 否则会陷入死循环
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.csrf().disable()//跨域关闭
		;
	}

}
