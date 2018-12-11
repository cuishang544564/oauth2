package com.rui.tiger.auth.core.captcha;

/**
 * 验证码生成接口
 *
 * @author CaiRui
 * @date 2018-12-10 12:03
 */
public interface CaptchaGenerate {
	/**
	 * 生成图片验证码
	 *
	 * @return
	 */
	ImageCaptchaVo generate();
}
