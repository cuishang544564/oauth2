package com.rui.tiger.auth.core.captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码控制器
 *
 * @author CaiRui
 * @date 2018-12-10 12:13
 */
@RestController
public class CaptchaController {

	public static final String IMAGE_CAPTCHA_SESSION_KEY = "image_captcha_session_key";
	private static final String FORMAT_NAME = "JPEG";

	@Autowired
	private CaptchaGenerate captchaGenerate;
	//spring session 工具类
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	/**
	 * 获取图片验证码
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/captcha/image")
	public void createKaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//1.接口生成验证码
		ImageCaptchaVo imageCaptcha = (ImageCaptchaVo) captchaGenerate.generate();
		//2.保存到session中
		sessionStrategy.setAttribute(new ServletWebRequest(request), IMAGE_CAPTCHA_SESSION_KEY, imageCaptcha);
		//3.写到响应流中
		response.setHeader("Cache-Control", "no-store, no-cache");// 没有缓存
		response.setContentType("image/jpeg");
		ImageIO.write(imageCaptcha.getImage(), FORMAT_NAME, response.getOutputStream());
	}

}
