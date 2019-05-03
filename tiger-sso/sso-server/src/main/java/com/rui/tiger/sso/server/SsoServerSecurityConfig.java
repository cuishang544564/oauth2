package com.rui.tiger.sso.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author CaiRui
 * @Date 2019-05-03 14:41
 */
@Configuration
public class SsoServerSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //security5+ 认证默认为表单了也就是http.formLogin()
        http.httpBasic();
    }
}
