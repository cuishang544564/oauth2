package com.rui.tiger.auth.core.captcha.sms;

import com.rui.tiger.auth.core.captcha.CaptchaException;
import com.rui.tiger.auth.core.captcha.CaptchaProcessor;
import com.rui.tiger.auth.core.captcha.CaptchaVo;
import com.rui.tiger.auth.core.captcha.ImageCaptchaVo;
import com.rui.tiger.auth.core.properties.SecurityProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *  手机验证码过滤器
 * OncePerRequestFilter 过滤器只会调用一次
 *
 * @author CaiRui
 * @date 2018-12-10 12:23
 */
@Setter
@Getter
@Slf4j
public class SmsCaptchaFilter extends OncePerRequestFilter implements InitializingBean {

	//一般在配置类中进行注入

	private AuthenticationFailureHandler failureHandler;

	private SecurityProperties securityProperties;

	/**
	 * 验证码拦截的路径
	 */
	private Set<String> interceptUrlSet = new HashSet<>();

	//session工具类
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	//路径匹配工具类
	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	/**
	 * @throws ServletException
	 */

	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		//其它配置的需要验证码验证的路径
		String configInterceptUrl = securityProperties.getCaptcha().getSms().getInterceptUrl();
		if (StringUtils.isNotBlank(configInterceptUrl)) {
			String[] configInterceptUrlArray = StringUtils.split(configInterceptUrl, ",");
			interceptUrlSet = Stream.of(configInterceptUrlArray).collect(Collectors.toSet());
		}
		//短信登录请求验证
		interceptUrlSet.add("/authentication/mobile");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		log.info("验证码验证请求路径:[{}]", request.getRequestURI());
		boolean action = false;// 默认不放行
		for (String url : interceptUrlSet) {
			if (antPathMatcher.match(url, request.getRequestURI())) {
				action = true;
			}
		}
		if (action) {
			try {
				validate(request);
			} catch (CaptchaException captchaException) {
				//失败调用我们的自定义失败处理器
				failureHandler.onAuthenticationFailure(request, response, captchaException);
				//后续流程终止
				return;
			}

		}
		//后续过滤器继续执行
		filterChain.doFilter(request, response);
	}

	/**
	 * 图片验证码校验
	 *
	 * @param request
	 */
	private void validate(HttpServletRequest request) throws ServletRequestBindingException {
		String smsSessionKey=CaptchaProcessor.CAPTCHA_SESSION_KEY+"sms";
		// 拿到之前存储的imageCode信息
		ServletWebRequest swr = new ServletWebRequest(request);
		CaptchaVo smsCaptchaInSession = (CaptchaVo) sessionStrategy.getAttribute(swr, smsSessionKey);
		String codeInRequest = ServletRequestUtils.getStringParameter(request, "smsCode");

		if (StringUtils.isBlank(codeInRequest)) {
			throw new CaptchaException("验证码的值不能为空");
		}
		if (smsCaptchaInSession == null) {
			throw new CaptchaException("验证码不存在");
		}
		if (smsCaptchaInSession.isExpried()) {
			sessionStrategy.removeAttribute(swr, smsSessionKey);
			throw new CaptchaException("验证码已过期");
		}
		if (!StringUtils.equals(smsCaptchaInSession.getCode(), codeInRequest)) {
			throw new CaptchaException("验证码不匹配");
		}
		//验证通过 移除缓存
		sessionStrategy.removeAttribute(swr, smsSessionKey);
	}
}
