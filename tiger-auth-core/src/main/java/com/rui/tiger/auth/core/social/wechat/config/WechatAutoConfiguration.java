package com.rui.tiger.auth.core.social.wechat.config;

import com.rui.tiger.auth.core.properties.SecurityProperties;
import com.rui.tiger.auth.core.properties.WechatProperties;
import com.rui.tiger.auth.core.social.wechat.connect.WechatConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;

/**
 * 微信登陆配置
 * @author CaiRui   extends SocialConfigurerAdapter
 * @Date 2019-01-12 13:57
 */
//@Configuration
//@ConditionalOnProperty(prefix = "tiger.auth.social.wechat", name = "app-id")
public class WechatAutoConfiguration extends SocialConfigurerAdapter {
 /*   @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        connectionFactoryConfigurer.addConnectionFactory(createConnectionFactory());
    }



    private ConnectionFactory<?> createConnectionFactory() {
        WechatProperties weixinConfig = securityProperties.getSocial().getWechat();
        return new WechatConnectionFactory(weixinConfig.getProviderId(), weixinConfig.getAppId(),
                weixinConfig.getAppSecret());
    }


    // 后补：做到处理注册逻辑的时候发现的一个bug：登录完成后，数据库没有数据，但是再次登录却不用注册了
    // 就怀疑是否是在内存中存储了。结果果然发现这里父类的内存ConnectionRepository覆盖了SocialConfig中配置的jdbcConnectionRepository
    // 这里需要返回null，否则会返回内存的 ConnectionRepository
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return null;
    }*/

}
