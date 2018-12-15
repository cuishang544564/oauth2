package com.rui.tiger.auth.demo.service;

import com.rui.tiger.auth.core.captcha.CaptchaGenerate;
import com.rui.tiger.auth.core.captcha.ImageCaptchaVo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author CaiRui
 * @date 2018-12-12 9:05
 */
//@Component("imageCaptchaGenerate")
@Slf4j
public class DemoImageCaptchaGenerateTest implements CaptchaGenerate {

	/**
	 * 测试覆盖 测试通过将其@Component去掉 保证正常流程执行
	 *
	 * @return
	 * @see com.rui.tiger.auth.core.config.CaptchaBeanConfig
	 */
	@Override
	public ImageCaptchaVo generate() {
		log.info("自定义验证码实现 覆盖原始的生成测试");
		return null;
	}
}
