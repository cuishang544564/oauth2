package com.rui.tiger.auth.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author CaiRui
 * @date 2018-12-3 9:25
 */
@EnableRedisHttpSession
@SpringBootApplication
@ComponentScan("com.rui.tiger.auth")
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
