package com.rui.tiger.auth.browser.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 并发失效策略
 * @author CaiRui
 * @date 2019-02-26 18:23
 */
public class TigerExpiredSessionStrategy extends AbstractSessionInvalidStrategy implements SessionInformationExpiredStrategy {

	public TigerExpiredSessionStrategy(String destinationUrl) {
		super(destinationUrl);
	}

	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		onSessionInvalid(event.getRequest(), event.getResponse());
	}

	/**
	 * 并发导致的失效
	 * @return
	 */
	protected boolean isConcurrency() {
		return true;
	}
}
