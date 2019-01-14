package com.rui.tiger.auth.core.social.wechat.connect;

import com.rui.tiger.auth.core.social.wechat.api.WechatApi;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * @author CaiRui
 * @Date 2019-01-12 13:32
 */
public class WechatConnectionFactory extends OAuth2ConnectionFactory<WechatApi> {

    /**
     * @param appId
     * @param appSecret
     */
    public WechatConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new WechatServiceProvider(appId, appSecret), new WechatApiAdapter());
    }

    /**
     * 由于微信的openId是和accessToken一起返回的，所以在这里直接根据accessToken设置providerUserId即可，不用像QQ那样通过QQAdapter来获取
     */
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if(accessGrant instanceof WechatAccessGrant) {
            return ((WechatAccessGrant)accessGrant).getOpenId();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see org.springframework.social.connect.support.OAuth2ConnectionFactory#createConnection(org.springframework.social.oauth2.AccessGrant)
     */
    public Connection<WechatApi> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<WechatApi>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getApiAdapter(extractProviderUserId(accessGrant)));
    }

    /* (non-Javadoc)
     * @see org.springframework.social.connect.support.OAuth2ConnectionFactory#createConnection(org.springframework.social.connect.ConnectionData)
     */
    public Connection<WechatApi> createConnection(ConnectionData data) {
        return new OAuth2Connection<WechatApi>(data, getOAuth2ServiceProvider(), getApiAdapter(data.getProviderUserId()));
    }

    private ApiAdapter<WechatApi> getApiAdapter(String providerUserId) {
        return new WechatApiAdapter(providerUserId);
    }

    private OAuth2ServiceProvider<WechatApi> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<WechatApi>) getServiceProvider();
    }

}
