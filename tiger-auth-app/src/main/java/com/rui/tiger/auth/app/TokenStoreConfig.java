package com.rui.tiger.auth.app;

import com.rui.tiger.auth.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * redis存储token
 * @author CaiRui
 * @Date 2019-05-01 17:44
 */
@Configuration
public class TokenStoreConfig {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    //这里要完全配合
    @ConditionalOnProperty(prefix = "tiger.auth.oauth2", name = "tokenStore", havingValue = "redis")

    public TokenStore redisTokenStore() {
        return new MyRedisTokenStore(redisConnectionFactory);
    }

    // matchIfMissing = true 如果没有找到配置项也是生效的
    @Configuration
    @ConditionalOnProperty(prefix = "tiger.auth.oauth2", name = "tokenStore", havingValue = "jwt", matchIfMissing = true)
    public  static class JwtConfig{

        @Autowired
        private SecurityProperties securityProperties;

        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            //指定密签
            converter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
            return converter;
        }

        @Bean
        @ConditionalOnBean(TokenEnhancer.class)
        public TokenEnhancer jwtTokenEnhancer() {
            return new TigerJwtTokenEnhancer();
        }


    }

}
