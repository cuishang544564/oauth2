package com.rui.tiger.auth.core.social.qq.connect;

import com.rui.tiger.auth.core.social.qq.api.QQApi;
import com.rui.tiger.auth.core.social.qq.api.QQApiImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * QQ服务提供商
 * http://wiki.connect.qq.com/%E5%BC%80%E5%8F%91%E6%94%BB%E7%95%A5_server-side
 * @author CaiRui
 * @date 2019-1-3 9:10
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQApi> {

	//流程第1步 将用户导向认证服务器
	public static final String URL_AUTHORIZE  = "https://graph.qq.com/oauth2.0/authorize";
	//流程第4步 随带授权码获取用户令牌accessToken
	public static final String URL_ACCESS_TOKEN  = "https://graph.qq.com/oauth2.0/token";

	private String appId;//注册qq互联分配的id

	/**
	 * @param appId 注册qq互联分配的id
	 * @param secret 注册qq互联的分配密码
	 */
	public QQServiceProvider(String appId,String secret) {
		// OAuth2Operations 有一个默认实现类，可以使用这个默认实现类
		super(new OAuth2Template(appId, secret, URL_AUTHORIZE , URL_ACCESS_TOKEN ));
		this.appId=appId;
	}

	@Override
	public QQApi getApi(String accessToken) {
		return new QQApiImpl(accessToken, appId);
	}

}
