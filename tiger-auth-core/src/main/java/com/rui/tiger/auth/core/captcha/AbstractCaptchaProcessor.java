package com.rui.tiger.auth.core.captcha;

import org.apache.commons.lang.StringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

/**
 * 验证码处理器抽象父类
 *
 * @author CaiRui
 * @Date 2018/12/15 18:21
 */
public abstract class AbstractCaptchaProcessor<C extends CaptchaVo> implements CaptchaProcessor {

	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	/**
	 * 创建验证码
	 *
	 * @param request 封装请求和响应
	 * @throws Exception
	 */
	@Override
	public void create(ServletWebRequest request) throws Exception {
		//生成
		C captcha = generateCaptcha(request);
		//保存
		save(request, captcha);
		//发送
		send(request, captcha);
	}

	/**
	 * 短信和手机验证码的通用验证
	 *
	 * @param request
	 * @param captchaType 验证码
	 */
	@Override
	public void validate(ServletWebRequest request, CaptchaTypeEnum captchaType) throws CaptchaException {

		String sessionKey = getSessionKey(captchaType);
		CaptchaVo captchaInSession= (CaptchaVo) sessionStrategy.getAttribute(request, sessionKey);

		String captchaInRequest;
		try {
			captchaInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
					captchaType.getParamNameOnValidate());
		} catch (ServletRequestBindingException e) {
			throw new CaptchaException("获取验证码的值失败");
		}

		if (StringUtils.isBlank(captchaInRequest)) {
			throw new CaptchaException(captchaType + "验证码的值不能为空");
		}

		if (captchaInSession == null) {
			throw new CaptchaException(captchaType + "验证码不存在");
		}

		if (captchaInSession.isExpried()) {
			sessionStrategy.removeAttribute(request, sessionKey);
			throw new CaptchaException(captchaType + "验证码已过期");
		}

		if (!StringUtils.equals(captchaInSession.getCode(), captchaInRequest)) {
			throw new CaptchaException(captchaType + "验证码不匹配");
		}
		//验证成功清除缓存中的key
		sessionStrategy.removeAttribute(request, sessionKey);
	}

	/**
	 * 生成验证码
	 * @param request
	 * @return
	 */
	protected abstract C generateCaptcha(ServletWebRequest request);

	/**
	 * 发送验证码
 	 * @param request
	 * @param captcha
	 */
	protected abstract void send(ServletWebRequest request, C captcha) throws IOException, ServletRequestBindingException;

	/**
	 * 保存验证码到session中
	 * @param request
	 * @param captcha
	 */
	private void save(ServletWebRequest request, C captcha) {
		//redis不支持bufferImage序列化
		CaptchaVo captchaVo=new CaptchaVo(captcha.getCode(),captcha.getExpireTime());
		sessionStrategy.setAttribute(request, CAPTCHA_SESSION_KEY +getCondition().getCode(),captchaVo);
	}


	/**
	 * 获取验证码session key值
	 *
	 * @param captchaType
	 * @return
	 */
	private String getSessionKey(CaptchaTypeEnum captchaType) {
		return CAPTCHA_SESSION_KEY + captchaType.getCode();
	}

}
