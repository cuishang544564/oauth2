package com.rui.tiger.auth.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 自定义SpringSocialConfigurer 用于覆盖默认的社交登陆拦截请求
 *
 * @author CaiRui
 * @Date 2019/1/5 16:15
 */
public class TigerSpringSocialConfigurer extends SpringSocialConfigurer {

	private String filterProcessesUrl;//覆盖默认的/auth 拦截路径

	private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

	public TigerSpringSocialConfigurer(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}


	@Override
	protected <T> T postProcess(T object) {
		SocialAuthenticationFilter socialAuthenticationFilter = (SocialAuthenticationFilter) super.postProcess(object);
		socialAuthenticationFilter.setFilterProcessesUrl(filterProcessesUrl);
		if (socialAuthenticationFilterPostProcessor!=null){
			socialAuthenticationFilterPostProcessor.process(socialAuthenticationFilter);
		}
		return (T) socialAuthenticationFilter;
	}

	public SocialAuthenticationFilterPostProcessor getSocialAuthenticationFilterPostProcessor() {
		return socialAuthenticationFilterPostProcessor;
	}

	public void setSocialAuthenticationFilterPostProcessor(SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor) {
		this.socialAuthenticationFilterPostProcessor = socialAuthenticationFilterPostProcessor;
	}
}
