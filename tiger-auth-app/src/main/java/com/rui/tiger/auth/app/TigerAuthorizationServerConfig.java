package com.rui.tiger.auth.app;

import com.rui.tiger.auth.core.properties.OAuth2ClientProperties;
import com.rui.tiger.auth.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.Arrays;

/**
 * 服务提供商-认证服务器
 * @author CaiRui
 * @date 2019-03-18 09:12
 */
@Configuration
@EnableAuthorizationServer
@Slf4j
public class TigerAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private TokenStore redisTokenStore;

    private final AuthenticationManager authenticationManager;
    public TigerAuthorizationServerConfig(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
       /* clients.inMemory()
                .withClient("tigerauth")
                .secret("123456")
                .redirectUris("http://example.com", "localhost:80")
                .authorizedGrantTypes("refresh_token", "password")
                .accessTokenValiditySeconds(7200)
                .scopes("all")
                .and()
                .withClient("myid2")
                .secret("myid2")
                .redirectUris("http://example.com", "localhost:8080")
                .authorizedGrantTypes("refresh_token", "password")
                .accessTokenValiditySeconds(7200)
                .scopes("all", "read", "write");*/
//配置文件解析token配置
        InMemoryClientDetailsServiceBuilder inMemory = clients.inMemory();
        OAuth2ClientProperties[] clientsInCustom = securityProperties.getOauth2().getClients();
        for (OAuth2ClientProperties p : clientsInCustom) {
            inMemory.withClient(p.getClientId())
                    .secret(p.getClientSecret())
                    .redirectUris(p.getRedirectUris())
                    .authorizedGrantTypes(p.getAuthorizedGrantTypes())
                    .accessTokenValiditySeconds(p.getAccessTokenValiditySeconds())
                    .scopes(p.getScopes());
        }

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(this.authenticationManager)
                .tokenStore(redisTokenStore);
    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 这里使用什么密码需要 根据上面配置client信息里面的密码类型决定
        // 目前上面配置的是无加密的密码
        security.passwordEncoder(NoOpPasswordEncoder.getInstance());
    }


}
