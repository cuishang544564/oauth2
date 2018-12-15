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
 *
 * @author CaiRui
 * @date 2018-12-10 12:09
 * @see Constants
 */
@Configuration
public class CaptchaGenerateConfig {

	@Autowired
	private SecurityProperties securityProperties;//验证码配置获取

	// 参见 https://blog.csdn.net/larger5/article/details/79522105  DefaultKaptcha配置生成
	@Bean
	public DefaultKaptcha producer() {
		Properties properties = new Properties();
		properties.put("kaptcha.border", "no");
		//常量配置Constants和直接字符串配置都可以
		properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
		//验证码长度 put 不生效  注意
		//properties.put("kaptcha.textproducer.char.length", securityProperties.getImageCaptcha().getSize());
		properties.setProperty("kaptcha.textproducer.char.length", String.valueOf(securityProperties.getCaptcha().getImage().getSize()));

		Config config = new Config(properties);
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}
}
