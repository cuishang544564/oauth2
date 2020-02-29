package com.twenty.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cuishang
 * @since 2020/1/5
 */
@RestController
@EnableOAuth2Sso//启用第三方应用
@SpringBootApplication
public class ClientServer {
    public static void main(String[] args) {
        SpringApplication.run(ClientServer.class,args);
    }
    @GetMapping("/user")
    public Authentication user(Authentication credentials) {
        return credentials;
    }
}
