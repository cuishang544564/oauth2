package com.rui.tiger.auth.core.social.wechat.connect;

import com.rui.tiger.auth.core.social.wechat.api.WechatApi;
import com.rui.tiger.auth.core.social.wechat.api.WechatApiImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 *  微信的OAuth2流程处理器的提供器，供spring social的connect体系调用
 * @author CaiRui
 * @Date 2019-01-12 13:40
 */
public class WechatServiceProvider extends AbstractOAuth2ServiceProvider<WechatApi> {

    /**
     * 微信获取授权码的url
     *
     * https://open.weixin.qq.com/connect/qrconnect?
     * appid=APPID&
     * redirect_uri=REDIRECT_URI&
     * response_type=code&
     * scope=SCOPE&
     * state=STATE#wechat_redirect
     */
    private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";
    /**
     * 微信获取accessToken的url
     */
    private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * @param appId
     * @param appSecret
     */
    public WechatServiceProvider(String appId, String appSecret) {
        super(new WechatOAuth2Template(appId, appSecret,URL_AUTHORIZE,URL_ACCESS_TOKEN));
    }


    /* (non-Javadoc)
     * @see org.springframework.social.oauth2.AbstractOAuth2ServiceProvider#getApi(java.lang.String)
     */
    @Override
    public WechatApi getApi(String accessToken) {
        return new WechatApiImpl(accessToken);
    }

}
