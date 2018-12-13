package com.rui.tiger.auth.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author CaiRui
 * @date 2018-12-3 9:25
 */
@SpringBootApplication
@ComponentScan("com.rui.tiger.auth")
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
