package com.rui.tiger.auth.core.captcha;

import com.rui.tiger.auth.core.authentication.TigerAuthenticationFailureHandler;
import com.rui.tiger.auth.core.properties.SecurityConstants;
import com.rui.tiger.auth.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 重构的验证码拦截器 （图片验证+短信验证）
 *
 * @author CaiRui
 * @date 2018-12-25 8:37
 */
@Component("captchaUnionFilter")
@Slf4j
public class CaptchaUnionFilter extends OncePerRequestFilter implements InitializingBean {
	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private TigerAuthenticationFailureHandler tigerAuthenticationFailureHandler;
	@Autowired
	private CaptchaProcessorHolder captchaProcessorHolder;

	/**
	 * 存放所有需要校验验证码的url
	 * key: 验证码类型
	 * value: 验证路径
	 */
	private Map<String, CaptchaTypeEnum> urlMap = new HashMap<>();

	/**
	 * 验证请求url与配置的url是否匹配的工具类
	 */
	private AntPathMatcher pathMatcher = new AntPathMatcher();

	/**
	 * bean初始化后调用
	 *
	 * @throws ServletException
	 */

	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		//短信验证码
		urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, CaptchaTypeEnum.SMS);
		addVaildateUrlToUrlMap(securityProperties.getCaptcha().getSms().getInterceptUrl(), CaptchaTypeEnum.SMS);
		//图片验证码
		urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, CaptchaTypeEnum.IMAGE);
		addVaildateUrlToUrlMap(securityProperties.getCaptcha().getImage().getInterceptImageUrl(), CaptchaTypeEnum.IMAGE);
	}

	/**
	 * 验证码拦截核心逻辑
	 *
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		CaptchaTypeEnum captchaTypeEnum = getCaptchaTypeWithRequestUrl(request);
		if (captchaTypeEnum != null) {
			try {
				log.info("校验请求【" + request.getRequestURI() + "】" + captchaTypeEnum.getDesc() + "验证码");
				captchaProcessorHolder.findCaptchaProcessor(captchaTypeEnum)
						.validate(new ServletWebRequest(request, response),captchaTypeEnum);
			} catch (CaptchaException captchaException) {
				log.info("验证码校验异常", captchaException.getMessage());
				tigerAuthenticationFailureHandler.onAuthenticationFailure(request, response, captchaException);
				return;
			}
			//filterChain.doFilter(request, response); 就是null后续都不执行  被坑了很久
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * 根据请求路径返回验证码类型
	 *
	 * @param request
	 * @return
	 */
	private CaptchaTypeEnum getCaptchaTypeWithRequestUrl(HttpServletRequest request) {
		String requestUrl = request.getRequestURI();//返回除去host（域名或者ip）部分的路径
		if (!StringUtils.equalsIgnoreCase("get", request.getMethod())) {
			Set<String> urlSet = urlMap.keySet();
			for (String url : urlSet) {
				if (pathMatcher.match(url, requestUrl)) {
					return urlMap.get(url);
				}
			}
		}
		return null;
	}

	/**
	 * 不同类型拦截路径赋值
	 *
	 * @param interceptUrl
	 * @param captchaTypeEnum
	 */
	private void addVaildateUrlToUrlMap(String interceptUrl, CaptchaTypeEnum captchaTypeEnum) {
		if (StringUtils.isNotBlank(interceptUrl)) {
			String[] interceptUrlArray = StringUtils.split(interceptUrl, ",");
			for (String url : interceptUrlArray) {
				urlMap.put(url, captchaTypeEnum);
			}
		}

	}

}
