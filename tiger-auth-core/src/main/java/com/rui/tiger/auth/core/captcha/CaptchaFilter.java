package com.rui.tiger.auth.core.captcha;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 图片验证码过滤器
 * OncePerRequestFilter 过滤器只会调用一次
 *
 * @author CaiRui
 * @date 2018-12-10 12:23
 */
public class CaptchaFilter extends OncePerRequestFilter {

	//一般在配置类中进行注入
	@Setter
	@Getter
	private AuthenticationFailureHandler failureHandler;

	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (StringUtils.equals("/authentication/form", request.getRequestURI())
				&& StringUtils.equalsIgnoreCase("post", request.getMethod())) {
			try {
				validate(request);
			}catch (CaptchaException captchaException ){
				failureHandler.onAuthenticationFailure(request, response, captchaException);
				//后续流程终止
				return;
			}

		}
		filterChain.doFilter(request, response);

	}

	/**
	 * 图片验证码校验
	 * @param request
	 */
	private void validate(HttpServletRequest request) throws ServletRequestBindingException {
		// 拿到之前存储的imageCode信息
		ServletWebRequest swr = new ServletWebRequest(request);
		ImageCaptchaVo imageCodeInSession = (ImageCaptchaVo) sessionStrategy.getAttribute(swr, CaptchaController.IMAGE_CAPTCHA_SESSION_KEY);
		String codeInRequest = ServletRequestUtils.getStringParameter(request, "imageCode");

		if (StringUtils.isBlank(codeInRequest)) {
			throw new CaptchaException("验证码的值不能为空");
		}
		if (imageCodeInSession == null) {
			throw new CaptchaException("验证码不存在");
		}
		if (imageCodeInSession.isExpried()) {
			sessionStrategy.removeAttribute(swr, CaptchaController.IMAGE_CAPTCHA_SESSION_KEY);
			throw new CaptchaException("验证码已过期");
		}
		if (!StringUtils.equals(imageCodeInSession.getCode(), codeInRequest)) {
			throw new CaptchaException("验证码不匹配");
		}
		//验证通过 移除缓存
		sessionStrategy.removeAttribute(swr, CaptchaController.IMAGE_CAPTCHA_SESSION_KEY);
	}
}
