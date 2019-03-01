package com.rui.tiger.auth.core.properties;

/**
 * @author CaiRui
 * @date 2019-02-27 08:44
 */
public class SessionProperties {


	private int maximumSessions=1;//session最大并发数

	private boolean maxSessionsPreventsLogin;//默认false 会踢掉之前已经登录的信息

	private String invalidSessionUrl=SecurityConstants.DEFAULT_SESSION_INVALID_URL;//默认失效界面


	public int getMaximumSessions() {
		return maximumSessions;
	}

	public void setMaximumSessions(int maximumSessions) {
		this.maximumSessions = maximumSessions;
	}

	public boolean isMaxSessionsPreventsLogin() {
		return maxSessionsPreventsLogin;
	}

	public void setMaxSessionsPreventsLogin(boolean maxSessionsPreventsLogin) {
		this.maxSessionsPreventsLogin = maxSessionsPreventsLogin;
	}

	public String getInvalidSessionUrl() {
		return invalidSessionUrl;
	}

	public void setInvalidSessionUrl(String invalidSessionUrl) {
		this.invalidSessionUrl = invalidSessionUrl;
	}
}
