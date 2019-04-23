package com.rui.tiger.auth.browser.captcha;

import com.rui.tiger.auth.core.captcha.CaptchaRepository;
import com.rui.tiger.auth.core.captcha.CaptchaTypeEnum;
import com.rui.tiger.auth.core.captcha.CaptchaVo;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * session存储实现
 * @author CaiRui
 * @date 2019-04-22 08:48
 */
@Component
public class SessionCaptchaRepository implements CaptchaRepository {

	/** 操作session的工具类 */
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	/** 验证码放入session的时候前缀 */
	public final static String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE";

	/**
	 * 保存验证码
	 *
	 * @param request
	 * @param code
	 * @param captchaType
	 */
	@Override
	public void save(ServletWebRequest request, CaptchaVo code, CaptchaTypeEnum captchaType) {
		sessionStrategy.setAttribute(request, getSessionKey(captchaType), code);
	}

	/**
	 * 获取验证码
	 *
	 * @param request
	 * @param captchaType
	 * @return
	 */
	@Override
	public CaptchaVo get(ServletWebRequest request, CaptchaTypeEnum captchaType) {
		String sessionKey = getSessionKey(captchaType);
		// 拿到创建 create() 存储到session的code验证码对象
		return (CaptchaVo) sessionStrategy.getAttribute(request, sessionKey);

	}

	/**
	 * 移除验证码
	 *
	 * @param request
	 * @param captchaType
	 */
	@Override
	public void remove(ServletWebRequest request, CaptchaTypeEnum captchaType) {
		sessionStrategy.removeAttribute(request, getSessionKey(captchaType));
	}

	/**
	 * 构建验证码放入session时的key; 在保存的时候也使用该key
	 * @param validateCodeType
	 * @return
	 */
	private String getSessionKey(CaptchaTypeEnum validateCodeType) {
		return SESSION_KEY_PREFIX + validateCodeType.toString().toUpperCase();
	}

}
