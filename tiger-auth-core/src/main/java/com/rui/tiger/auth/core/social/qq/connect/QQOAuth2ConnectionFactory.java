package com.rui.tiger.auth.core.social.qq.connect;

import com.rui.tiger.auth.core.social.qq.api.QQApi;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * qq连接工厂
 * @author CaiRui
 * @date 2019-1-3 9:11
 */
public class QQOAuth2ConnectionFactory extends OAuth2ConnectionFactory<QQApi> {
    /**
     *
     * @param providerId 供应商唯一标识 如qq 也是后面添加social的过滤，过滤器帮我们拦截的url其中的某一段地址
     * @param appId  应用id
     * @param appSecret 应用密码
     */
    public QQOAuth2ConnectionFactory(String providerId, String appId,String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQApiAdapter());
    }

}
