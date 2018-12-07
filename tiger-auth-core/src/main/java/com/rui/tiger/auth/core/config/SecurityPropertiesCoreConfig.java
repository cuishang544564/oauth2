package com.rui.tiger.auth.core.config;

import com.rui.tiger.auth.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * SecurityProperties 配置类注入生效
 *
 * @author CaiRui
 * @date 2018-12-6 8:57
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityPropertiesCoreConfig {

}
