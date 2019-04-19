package com.rui.tiger.auth.core.config;

import com.rui.tiger.auth.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SecurityProperties 配置类注入生效
 *
 * @author CaiRui
 * @date 2018-12-6 8:57
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityPropertiesCoreConfig {


	/**
	 * 密码加密解密
	 *
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
