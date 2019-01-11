package com.rui.tiger.auth.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * 第三方应用登录 默认注册用户
 * @author CaiRui
 * @date 2019-01-10 18:20
 */
@Component
@Slf4j
public class DemoConnectionSignUp implements ConnectionSignUp {

	/**
	 * 根据社交用户信息默认创建用户并返回用户唯一标识
	 * @param connection
	 * @return
	 */
	@Override
	public String execute(Connection<?> connection) {
		log.info("根据社交用户信息默认创建用户并返回用户唯一标识");
		//系统业务逻辑处理  目前直接以昵称作为唯一标识  这里 可以调用注册逻辑
		return connection.getDisplayName();
	}
}
