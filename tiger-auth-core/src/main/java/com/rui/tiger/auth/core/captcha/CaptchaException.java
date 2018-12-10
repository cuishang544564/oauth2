package com.rui.tiger.auth.core.captcha;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义验证码异常
 * @author CaiRui
 * @date 2018-12-10 12:43
 */
public class CaptchaException extends AuthenticationException {

	public CaptchaException(String msg, Throwable t) {
		super(msg, t);
	}

	public CaptchaException(String msg) {
		super(msg);
	}
}
