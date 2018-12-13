package com.rui.tiger.auth.core.support.strategy;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.Map;


/**
 * spring 容器
 * 蔡锐  2015年10月18日
 */
@Service
@Lazy(false)
public class ManageSpringBeans implements ApplicationContextAware {
	private static ApplicationContext context;

	/**
	 * 获取springBean
	 *
	 * @param requiredType bean类型
	 * @return 对应的bean
	 * T
	 */
	public static <T> T getBean(final Class<T> requiredType) {
		return context.getBean(requiredType);
	}

	public static <T> T getBean(final String beanName) {
		return (T) context.getBean(beanName);
	}

	public static <T> Map<String, T> getBeans(final Class<T> requiredType) {
		return context.getBeansOfType(requiredType);
	}

	/**
	 * 从annotation提取bean
	 *
	 * @param annotationType
	 * @return Map<String,Object>
	 */
	public static Map<String, Object> getBeansWithAnnotation(final Class<? extends Annotation> annotationType) {
		return context.getBeansWithAnnotation(annotationType);
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}

}
