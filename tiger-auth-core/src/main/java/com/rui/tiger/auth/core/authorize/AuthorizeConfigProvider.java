package com.rui.tiger.auth.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 自定义权限控制接口
 * @author CaiRui
 * @date 2019-05-06 12:02
 */
public interface AuthorizeConfigProvider {

	/**
	 * @param config
	 * @return 返回的boolean表示配置中是否有针对anyRequest的配置。在整个授权配置中，
	 * 应该有且仅有一个针对anyRequest的配置，如果所有的实现都没有针对anyRequest的配置，
	 * 系统会自动增加一个anyRequest().authenticated()的配置。如果有多个针对anyRequest
	 * 的配置，则会抛出异常。
	 */
	boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
