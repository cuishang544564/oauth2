package com.rui.tiger.auth.app;

import com.rui.tiger.auth.core.properties.OAuth2ClientProperties;
import com.rui.tiger.auth.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.*;

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

    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;

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
//        InMemoryClientDetailsServiceBuilder inMemory = clients.inMemory();
//        OAuth2ClientProperties[] clientsInCustom = securityProperties.getOauth2().getClients();
//        for (OAuth2ClientProperties p : clientsInCustom) {
//            inMemory.withClient(p.getClientId())
//                    .secret(p.getClientSecret())
//                    .redirectUris(p.getRedirectUris())
//                    .authorizedGrantTypes(p.getAuthorizedGrantTypes())
//                    .accessTokenValiditySeconds(p.getAccessTokenValiditySeconds())
////                    .autoApprove(p.getAutoApprove())
//                    .scopes(p.getScopes());
//        }
        clients.withClientDetails(clientDetailsService());
        super.configure(clients);

    }
    public ClientDetailsService clientDetailsService(){
        return new ClientDetailsService() {
            @Override
            public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
                return new ClientDetails() {
                    @Override
                    public String getClientId() {
                        return "imooc";
                    }

                    @Override
                    public Set<String> getResourceIds() {
                        return null;
                    }

                    @Override
                    public boolean isSecretRequired() {
                        return true;
                    }

                    @Override
                    public String getClientSecret() {
//                        return "imooc";
                        return "$2a$16$NLhOZilPoqa1EH8MixtP5.0c9bzZevIlcrW04bYcxChryu3gayTIe";
                    }

                    @Override
                    public boolean isScoped() {
                        return true;
                    }

                    @Override
                    public Set<String> getScope() {
                        HashSet<String> strings = new HashSet<>();
                        strings.add("all");
                        return strings;
                    }

                    @Override//这里配置支持哪几种授权模式
                    public Set<String> getAuthorizedGrantTypes() {
                        Set<String> strings = new HashSet<>();
                        strings.add("code");
                        strings.add("authorization_code");
                        strings.add("refresh_token");
                        strings.add("password");
                        strings.add("implicit");
                        return strings;
                    }

                    @Override
                    public Set<String> getRegisteredRedirectUri() {
                        Set<String> strings = new HashSet<>();
                        strings.add("http://localhost:8062/auth/account");//这里是重定向地址及最后code授权码返回地址
                        return strings;
                    }

                    @Override
                    public Collection<GrantedAuthority> getAuthorities() {
                        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
                        grantedAuthorityList.add(new GrantedAuthority() {
                            @Override
                            public String getAuthority() {
                                return "itxsl";
                            }
                        });
                        return grantedAuthorityList;
                    }

                    @Override
                    public Integer getAccessTokenValiditySeconds() {
                        return 3600;
                    }

                    @Override
                    public Integer getRefreshTokenValiditySeconds() {
                        return 3600;
                    }

                    @Override
                    public boolean isAutoApprove(String s) {
                        return true;
                    }

                    @Override
                    public Map<String, Object> getAdditionalInformation() {
                        return null;
                    }
                };
            }
        };
    }
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(this.authenticationManager)
                .tokenStore(redisTokenStore);
        /**
         * 私有方法，但是在里面调用了accessTokenEnhancer.enhance所以这里使用链
         * @see  DefaultTokenServices#createAccessToken(org.springframework.security.oauth2.provider.OAuth2Authentication, org.springframework.security.oauth2.common.OAuth2RefreshToken)
         */
        if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null) {
            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancers = new ArrayList<>();
            enhancers.add(jwtTokenEnhancer);
            enhancers.add(jwtAccessTokenConverter);
            enhancerChain.setTokenEnhancers(enhancers);
            // 一个处理链，先添加，再转换
            endpoints
                    .tokenEnhancer(enhancerChain)
                    .accessTokenConverter(jwtAccessTokenConverter);
        }
    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 这里使用什么密码需要 根据上面配置client信息里面的密码类型决定
        // 目前上面配置的是无加密的密码
//        security.passwordEncoder(NoOpPasswordEncoder.getInstance());
        //默认是不支持表单提交，这里修改提交权限
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
        super.configure(security);
    }


}
