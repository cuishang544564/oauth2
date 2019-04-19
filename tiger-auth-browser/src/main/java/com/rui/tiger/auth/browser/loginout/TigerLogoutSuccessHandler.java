package com.rui.tiger.auth.browser.loginout;

import com.alibaba.fastjson.JSON;
import com.rui.tiger.auth.core.properties.BrowserProperties;
import com.rui.tiger.auth.core.properties.SecurityProperties;
import com.rui.tiger.auth.core.support.SimpleResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录处理Handler
 *
 * @author CaiRui
 * @date 2019-03-12 08:31
 */
public class TigerLogoutSuccessHandler implements LogoutSuccessHandler {


	private SecurityProperties securityProperties;

	public TigerLogoutSuccessHandler(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String loginOutUrl = securityProperties.getBrowser().getLoginOut();

		if (StringUtils.isBlank(loginOutUrl)) {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(JSON.toJSONString(new SimpleResponse("退出成功")));
		} else {
			response.sendRedirect(loginOutUrl);
		}


	}
}
