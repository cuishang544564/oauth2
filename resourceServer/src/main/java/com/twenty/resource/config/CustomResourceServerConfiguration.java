package com.twenty.resource.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.header.HeaderWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cuishang
 * @since 2020/2/28
 */
@Configuration
@EnableResourceServer//启用资源服务器
public class CustomResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Value("${spring.application.name}")
    private String resourceId;
    @Override
    public void configure(ResourceServerSecurityConfigurer resources)  throws Exception{
//        TokenStore tokenStore=new RedisTokenStore()
        resources.resourceId(resourceId)
//                .tokenStore()
        ;

    }

    @Override
    public void configure(HttpSecurity http)  throws Exception{
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .requestMatchers().antMatchers("/user/**")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/user/**").access("#oauth2.hasAnyScope('read','all') and hasRole('ROLE_ADMIN') ")
                .antMatchers(HttpMethod.POST, "/user/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PATCH, "/user/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PUT, "/user/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.DELETE, "/user/**").access("#oauth2.hasScope('write')")
                .and()
//                .exceptionHandling()
//                .and()
                .headers().addHeaderWriter(new HeaderWriter() {
            @Override
            public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
                response.addHeader("Access-Control-Allow-Origin", "*");
                if (request.getMethod().equals("OPTIONS")) {
                    response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
                    response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
                }
            }
        });
    }
}
