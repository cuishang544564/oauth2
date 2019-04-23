package com.rui.tiger.auth.app;

import com.rui.tiger.auth.app.social.openid.OpenIdAuthenticationSecurityConfig;
import com.rui.tiger.auth.core.config.CaptchaSecurityConfig;
import com.rui.tiger.auth.core.config.SmsAuthenticationSecurityConfig;
import com.rui.tiger.auth.core.properties.SecurityConstants;
import com.rui.tiger.auth.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author CaiRui
 * @date 2019-04-17 08:38
 */
@Configuration
@EnableResourceServer
public class TigerResourceServerConfig extends ResourceServerConfigurerAdapter{

    @Autowired
    private AuthenticationSuccessHandler tigerAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler tigerAuthenticationFailureHandler;

    @Autowired
    private SmsAuthenticationSecurityConfig smsAuthenticationSecurityConfig;//短信登陆配置
    @Autowired
    private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;
    @Autowired
    private SpringSocialConfigurer tigerSpringSocialConfigurer;
    @Autowired
    private CaptchaSecurityConfig captchaSecurityConfig;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        /**
         * 表单密码配置
         */
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)//
                .defaultSuccessUrl("/index.html")
                .successHandler(tigerAuthenticationSuccessHandler)
                .failureHandler(tigerAuthenticationFailureHandler);

        http
                .apply(captchaSecurityConfig) //图形验证码的有问题 先不处理
                .and()
                .apply(smsAuthenticationSecurityConfig)
                .and()
                .apply(tigerSpringSocialConfigurer)
                .and()
                .apply(openIdAuthenticationSecurityConfig)
                .and()
                .authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,//权限认证
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,//手机
                        securityProperties.getBrowser().getLoginPage(),//登录页面
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",//  /captcha/* 验证码放行
                        securityProperties.getBrowser().getSignupUrl(),
                        //这个第三方自定义权限 后续抽离出去 可配置
                        securityProperties.getBrowser().getLoginOut(),
                        "/user/regist",
                        "/index.html",
                        securityProperties.getBrowser().getSession().getInvalidSessionUrl())
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }
}
