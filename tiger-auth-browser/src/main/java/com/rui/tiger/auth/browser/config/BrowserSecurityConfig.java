package com.rui.tiger.auth.browser.config;

import com.rui.tiger.auth.core.authentication.TigerAuthenticationFailureHandler;
import com.rui.tiger.auth.core.authentication.TigerAuthenticationSuccessHandler;
import com.rui.tiger.auth.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 浏览器security配置类
 *
 * @author CaiRui
 * @date 2018-12-4 8:41
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private TigerAuthenticationFailureHandler tigerAuthenticationFailureHandler;
    @Autowired
    private TigerAuthenticationSuccessHandler tigerAuthenticationSuccessHandler;

    /**
     * 密码加密解密
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage( "/authentication/require")//自定义登录请求
                .loginProcessingUrl("/authentication/form")//自定义登录表单请求
                .successHandler(tigerAuthenticationSuccessHandler)
                .failureHandler(tigerAuthenticationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers(securityProperties.getBrowser().getLoginPage(),
                        "/authentication/require")//此路径放行 否则会陷入死循环
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()//跨域关闭
        ;
    }

}
