package com.rui.tiger.auth.core.captcha;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

/**
 * 图片验证码生成接口
 *
 * @author CaiRui
 * @date 2018-12-10 12:07
 */
//@Service("imageCaptchaGenerate")

public class ImageCaptchaGenerate implements CaptchaGenerate {

	@Autowired
	private Producer producer;//config bean中配置

	@Override
	public ImageCaptchaVo generate() {
		String code = producer.createText();
		BufferedImage bufferedImage = producer.createImage(code);
		return new ImageCaptchaVo(bufferedImage, code, 60 * 5);//5分钟过期
	}
}
