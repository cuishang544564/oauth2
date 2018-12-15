package com.rui.tiger.auth.core.config;

import com.rui.tiger.auth.core.captcha.*;
import com.rui.tiger.auth.core.captcha.image.ImageCaptchaGenerate;
import com.rui.tiger.auth.core.captcha.sms.DefaultSmsCaptchaSender;
import com.rui.tiger.auth.core.captcha.sms.SmsCaptchaGenerate;
import com.rui.tiger.auth.core.captcha.sms.SmsCaptchaSend;
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
public class CaptchaBeanConfig {

	//图片验证码生成
	@Bean
	// spring 容器中如果存在imageCaptchaGenerate的bean就不会再初始化该bean了
	//可参见：https://www.cnblogs.com/yixianyixian/p/7346894.html 这篇博文
	@ConditionalOnMissingBean(name = "imageCaptchaGenerate")
	public CaptchaGenerate imageCaptchaGenerate() {
		ImageCaptchaGenerate imageCaptchaGenerate = new ImageCaptchaGenerate();
		return imageCaptchaGenerate;
	}
	//短信验证码生成
	@Bean
	@ConditionalOnMissingBean(name = "smsCaptchaGenerate")
	public CaptchaGenerate smsCaptchaGenerate() {
		SmsCaptchaGenerate smsCaptchaGenerate = new SmsCaptchaGenerate();
		return smsCaptchaGenerate;
	}

	@Bean
	@ConditionalOnMissingBean(DefaultSmsCaptchaSender.class)
	public SmsCaptchaSend defaultSmsCaptchaSender() {
		DefaultSmsCaptchaSender defaultSmsCaptchaSender=new DefaultSmsCaptchaSender();
		return defaultSmsCaptchaSender;
	}

}
