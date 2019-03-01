package com.rui.tiger.auth.browser.session;

import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过期失效策略
 * @author CaiRui
 * @date 2019-02-27 09:12
 */
public class TigerInvalidSessionStrategy extends AbstractSessionInvalidStrategy implements InvalidSessionStrategy {

	public TigerInvalidSessionStrategy(String destinationUrl) {
		super(destinationUrl);
	}

	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		onSessionInvalid(request, response);
	}

}
