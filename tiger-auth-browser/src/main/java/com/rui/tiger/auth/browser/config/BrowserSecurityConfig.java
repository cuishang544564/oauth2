package com.rui.tiger.auth.browser.config;

import com.rui.tiger.auth.core.authentication.AbstractChannelSecurityConfig;
import com.rui.tiger.auth.core.authorize.AuthorizeConfigManager;
import com.rui.tiger.auth.core.config.CaptchaSecurityConfig;
import com.rui.tiger.auth.core.config.SmsAuthenticationSecurityConfig;
import com.rui.tiger.auth.core.properties.SecurityConstants;
import com.rui.tiger.auth.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * 浏览器security配置类
 *
 * @author CaiRui
 * @date 2018-12-4 8:41
 */
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private SmsAuthenticationSecurityConfig smsAuthenticationSecurityConfig;//短信登陆配置
	@Autowired
	private CaptchaSecurityConfig captchaSecurityConfig;//验证码配置
	@Autowired
	private SpringSocialConfigurer tigerSpringSocialConfigurer;
	@Autowired
	private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;
	@Autowired
	private InvalidSessionStrategy invalidSessionStrategy;
	@Autowired
	private LogoutSuccessHandler tigerLogoutSuccessHandler;
	@Autowired
	private AuthorizeConfigManager authorizeConfigManager;

	/**
	 * 密码加密解密
	 * 放到core公共配置中
	 *
	 * @return
	 *//*
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/

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

	/**
	 * 核心配置
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/**
		 * 表单密码配置
		 */
		applyPasswordAuthenticationConfig(http);
		http
				.apply(captchaSecurityConfig)
					.and()
				.apply(smsAuthenticationSecurityConfig)
					.and()
				.apply(tigerSpringSocialConfigurer)
					.and()
				.rememberMe()
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(securityProperties.getBrowser().getRemberMeSeconds())
				.userDetailsService(userDetailsService)
					.and()
				.sessionManagement()
				.invalidSessionStrategy(invalidSessionStrategy)//session失效策略
				.maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())//最大session并发数
				.maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())//true达到并发数后阻止登录，false 踢掉之前的登录
				.expiredSessionStrategy(sessionInformationExpiredStrategy)//并发策略
				.and()
				.and()
				.logout()
				.logoutUrl("/loginOut") //默认logout
				//.logoutSuccessUrl("") url和Handler只能配置一个
				.logoutSuccessHandler(tigerLogoutSuccessHandler)
				.deleteCookies("JSESSIONID")//清楚cook键值
					.and()
				.csrf().disable();
		//配置管理
		authorizeConfigManager.config(http.authorizeRequests());

	}

}
