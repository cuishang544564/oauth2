package com.rui.tiger.auth.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @author CaiRui
 * @date 2019-04-24 16:01
 */
public interface SocialAuthenticationFilterPostProcessor {

	void process(SocialAuthenticationFilter socialAuthenticationFilter);
}
