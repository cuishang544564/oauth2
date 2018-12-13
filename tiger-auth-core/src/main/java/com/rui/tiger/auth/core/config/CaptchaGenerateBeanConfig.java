package com.rui.tiger.auth.core.config;

import com.google.code.kaptcha.Producer;
import com.rui.tiger.auth.core.captcha.CaptchaGenerate;
import com.rui.tiger.auth.core.captcha.ImageCaptchaGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码Bean生成配置类
 *
 * @author CaiRui
 * @date 2018-12-12 8:41
 */
@Configuration
public class CaptchaGenerateBeanConfig {

	@Bean
	// spring 容器中如果存在imageCaptchaGenerate的bean就不会再初始化该bean了
	//可参见：https://www.cnblogs.com/yixianyixian/p/7346894.html 这篇博文
	@ConditionalOnMissingBean(name = "imageCaptchaGenerate")
	public CaptchaGenerate imageCaptchaGenerate() {
		ImageCaptchaGenerate imageCaptchaGenerate = new ImageCaptchaGenerate();
		return imageCaptchaGenerate;
	}

}
