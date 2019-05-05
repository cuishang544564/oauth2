package com.rui.tiger.sso.client1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CaiRui
 * @date 2019-05-05 14:03
 */

@SpringBootApplication
@EnableOAuth2Sso
@RestController
public class SsoClient1Application {

	public static void main(String[] args) {
		SpringApplication.run(SsoClient1Application.class, args);
	}

	//编写一个获取当前服务器的用户信息控制器
	@GetMapping("/user")
	public Authentication user(Authentication user){
		return user;
	}


}