package com.twenty.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author cuishang
 * @since 2020/1/4
 */

@SpringBootApplication
public class ResourceServer {
    public static void main(String[] args) {
        SpringApplication.run(ResourceServer.class,args);
    }
}
