package com.rui.tiger.auth.browser.config;

import com.rui.tiger.auth.browser.session.TigerExpiredSessionStrategy;
import com.rui.tiger.auth.browser.session.TigerInvalidSessionStrategy;
import com.rui.tiger.auth.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * 失效默认实现
 * 自定义重写可以覆盖此实现
 * @author CaiRui
 * @date 2019-02-27 12:15
 */
@Configuration
public class BrowserSecurityBeanConfig {
	@Autowired
	private SecurityProperties securityProperties;

	@Bean
	@ConditionalOnMissingBean(InvalidSessionStrategy.class)
	public InvalidSessionStrategy invalidSessionStrategy(){
		return new TigerInvalidSessionStrategy(securityProperties.getBrowser().getSession().getInvalidSessionUrl());
	}

	@Bean
	@ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
	public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
		return new TigerExpiredSessionStrategy(securityProperties.getBrowser().getSession().getInvalidSessionUrl());
	}
}
