package com.rui.tiger.auth.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 权限配置文件父类（注意这里不用lombok 会读取不到）
 * 这里会有很多权限配置子模块
 *
 * @author CaiRui
 * @date 2018-12-6 8:41
 */

@ConfigurationProperties(value = "tiger.auth", ignoreInvalidFields = true)
public class SecurityProperties {

	/**
	 * 浏览器配置类
	 */
	private BrowserProperties browser = new BrowserProperties();
	/**
	 * 图片验证码配置类
	 */
	private ImageCaptchaProperties imageCaptcha = new ImageCaptchaProperties();


	public BrowserProperties getBrowser() {
		return browser;
	}

	public void setBrowser(BrowserProperties browser) {
		this.browser = browser;
	}

	public ImageCaptchaProperties getImageCaptcha() {
		return imageCaptcha;
	}

	public void setImageCaptcha(ImageCaptchaProperties imageCaptcha) {
		this.imageCaptcha = imageCaptcha;
	}
}
