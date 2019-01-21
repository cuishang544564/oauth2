package com.rui.tiger.auth.core.social;

import com.rui.tiger.auth.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * 社交配置类
 *
 * @author CaiRui
 * @Date 2019/1/5 11:46
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;//数据源
    @Autowired
    private SecurityProperties securityProperties;
    //第三方登录直接注册用户 可以不实现 跳到注册界面
    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;


    /**
     * 默认配置类  包括了过滤器SocialAuthenticationFilter 添加到security过滤链中
     *
     * @return
     */
    @Bean
    public SpringSocialConfigurer tigerSpringSocialConfigurer() {
        TigerSpringSocialConfigurer tigerSpringSocialConfigurer = new TigerSpringSocialConfigurer(
                securityProperties.getSocial().getFilterProcessesUrl());
        //配置自己的注册界面
        tigerSpringSocialConfigurer.signupUrl(securityProperties.getBrowser().getSignupUrl());
        return tigerSpringSocialConfigurer;
    }

    /**
     * 业务系统用户和服务提供商用户对应关系,保存在表UserConnection
     * JdbcUsersConnectionRepository.sql 中有建表语句
     * userId 业务系统Id
     * providerId 服务提供商的Id
     * providerUserId  同openId
     * Encryptors  加密策略 这里不加密
     *
     * @param connectionFactoryLocator
     * @return
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository jdbcUsersConnectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        //设定表UserConnection的前缀 表名不可以改变
        //jdbcUsersConnectionRepository.setTablePrefix("tiger_");
        if(connectionSignUp!=null){
            jdbcUsersConnectionRepository.setConnectionSignUp(connectionSignUp);
        }
        return jdbcUsersConnectionRepository;
    }

    /**
     * 从认证中获取用户信息
     *
     * @return
     */
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    /**
     * social和注册互动工具类
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator){
        return new ProviderSignInUtils(connectionFactoryLocator,getUsersConnectionRepository(connectionFactoryLocator));
    }



    //https://docs.spring.io/spring-social/docs/1.1.x-SNAPSHOT/reference/htmlsingle/#creating-connections-with-connectcontroller
    //社交账号绑定和解绑处理帮助类
    @Bean
    public ConnectController connectController(
            ConnectionFactoryLocator connectionFactoryLocator,
            ConnectionRepository connectionRepository) {
        return new ConnectController(connectionFactoryLocator, connectionRepository);
    }
}
