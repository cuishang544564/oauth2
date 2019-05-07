package com.rui.tiger.auth.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 权限控制接口持有管理者
 * @author CaiRui
 * @date 2019-05-06 12:05
 */
public interface AuthorizeConfigManager {

	void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
