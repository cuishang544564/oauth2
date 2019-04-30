package com.rui.tiger.auth.app;

import com.rui.tiger.auth.core.social.TigerSpringSocialConfigurer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 后置处理器 springbean 初始化之前和之后
 *
 * @author CaiRui
 * @date 2019-04-30 08:42  BeanPostProcessor
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}


	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (StringUtils.equals(beanName, "tigerSpringSocialConfigurer")) {
		    TigerSpringSocialConfigurer configurer = (TigerSpringSocialConfigurer)bean;
			configurer.signupUrl("/social/signUp");//更换浏览器的默认注册
			return configurer;
		}
		return bean;
	}
}
