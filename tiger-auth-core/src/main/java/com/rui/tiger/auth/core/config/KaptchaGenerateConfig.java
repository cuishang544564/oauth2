package com.rui.tiger.auth.core.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.rui.tiger.auth.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 验证码生成配置类
 * @see Constants
 * @author CaiRui
 * @date 2018-12-10 12:09
 */
@Configuration
public class KaptchaGenerateConfig {

	@Autowired
    private SecurityProperties securityProperties;

	@Bean
	public DefaultKaptcha producer() {
		Properties properties = new Properties();
		properties.put("kaptcha.border", "no");
		properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
		//验证码长度
		properties.put("kaptcha.textproducer.char.length", securityProperties.getImageCaptcha().getSize());
		Config config = new Config(properties);
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}
}
