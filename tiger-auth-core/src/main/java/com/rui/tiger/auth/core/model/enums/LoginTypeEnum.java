package com.rui.tiger.auth.core.model.enums;

import lombok.Getter;

/**
 * 登录类型枚举类
 *
 * @author CaiRui
 * @date 2018-12-6 12:45
 */
@Getter
public enum LoginTypeEnum {

	/**
	 * json数据返回
	 */
	JSON,
	/**
	 * 重定向
	 */
	REDIRECT;
}
