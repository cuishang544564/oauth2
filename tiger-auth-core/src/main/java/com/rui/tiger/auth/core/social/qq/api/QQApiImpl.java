package com.rui.tiger.auth.core.social.qq.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.stereotype.Component;

/**
 * 不同用户信息不一样 不能直接@Component(spring默认单例 线程不安全)
 * @author CaiRui
 * @date 2019-1-3 8:56
 */
@Slf4j
public class QQApiImpl extends AbstractOAuth2ApiBinding implements QQApi {

	//获取openID   http://wiki.connect.qq.com/%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7openid_oauth2-0
	public final static String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
	//获取用户信息 父类会自动携带accessToken http://wiki.connect.qq.com/get_user_info
	public final static String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

	/**
	 * 申请QQ登录成功后，分配给应用的appid
 	 */
	private String appId;//
	/**
	 * 通过输入在上一步获取的Access Token，得到对应用户身份的OpenID,与qq号码已一对应
	 * OpenID是此网站上或应用中唯一对应用户身份的标识，网站或应用可将此ID进行存储，便于用户下次登录时辨识其身份，或将其与用户在网站上或应用中的原有账号进行绑定
	 */
	private String openId;


	public QQApiImpl(String accessToken,String appId ){

		//accessToken 抽象父类参数
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);//token请求策略 放在header或参数中 QQ要求放在get请求参数中

		this.appId=appId;

		String openIdUrl=String.format(URL_GET_OPENID,accessToken);
		String result=getRestTemplate().getForObject(openIdUrl,String.class);
		//callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
		log.info("qq openID callback json:{}",result);
		//先直接截取 这个我们后面重构
	//	this.openId = StringUtils.substringBetween("\"openid\":", "}");
		this.openId = StringUtils.substringBetween(result,"\"openid\":\"", "\"}");
	}


	@Override
	public QQUserInfo getUserInfo()  {
		String url=String.format(URL_GET_USER_INFO,appId,openId);
		String userInfoResult=getRestTemplate().getForObject(url,String.class);
		//  QQUserInfo userInfo= JSON.parseObject(userInfoResult,QQUserInfo.class);
		log.info("qq userInfoResult json response:{}",userInfoResult);
		QQUserInfo userInfo= JSON.parseObject(userInfoResult, new TypeReference<QQUserInfo >(){});
		//返回的json中没有openId
		userInfo.setOpenId(openId);
		return userInfo;
	}
}
