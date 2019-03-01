package com.rui.tiger.auth.browser.session;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * session失效父类
 * （过期和并发失效共同业务逻辑处理）
 *
 * @author CaiRui
 * @date 2019-02-27 09:10
 */
@Slf4j
public class AbstractSessionInvalidStrategy {

	/**
	 * 跳转的url
	 */
	private String destinationUrl;
	/**
	 * 跳转之前是否创建新的session
	 */
	private boolean createNewSession = true;
	/**
	 * 默认跳转策略
	 */
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public AbstractSessionInvalidStrategy(String destinationUrl) {
		Assert.isTrue(UrlUtils.isValidRedirectUrl(destinationUrl), "url must start with '/' or with 'http(s)'");
		this.destinationUrl = destinationUrl;
	}

	protected void onSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (createNewSession) {
			request.getSession();
		}

		String sourceUrl = request.getRequestURI();
		String targetUrl="";

		if (StringUtils.endsWithIgnoreCase(sourceUrl, ".html")) {
			targetUrl = destinationUrl+".html";
			log.info("session失效,跳转到"+targetUrl);
			redirectStrategy.sendRedirect(request,response,targetUrl);
		} else {
			String message = "session已失效";
			if(isConcurrency()){
				message = message + "，有可能是并发登录导致的";
			}
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(message);
		}
	}

	/**
	 * session失效是否是并发导致的
	 *
	 * @return
	 */
	protected boolean isConcurrency() {
		return false;
	}

	/**
	 * Determines whether a new session should be created before redirecting (to
	 * avoid possible looping issues where the same session ID is sent with the
	 * redirected request). Alternatively, ensure that the configured URL does
	 * not pass through the {@code SessionManagementFilter}.
	 *
	 * @param createNewSession defaults to {@code true}.
	 */
	public void setCreateNewSession(boolean createNewSession) {
		this.createNewSession = createNewSession;
	}


}
