package com.rui.tiger.auth.core.social.qq.connect;

import com.rui.tiger.auth.core.social.qq.api.QQApi;
import com.rui.tiger.auth.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 适配器，用于将不同服务提供商的个性化用户信息映射到
 * {@link org.springframework.social.connect.Connection}
 * @author CaiRui
 * @date 2019-1-3 9:10
 */
public class QQApiAdapter implements ApiAdapter<QQApi> {

	/**
	 * 测试是否连通
	 * @param api
	 * @return
	 */
	@Override
	public boolean test(QQApi api) {
		return true;
	}

	@Override
	public void setConnectionValues(QQApi api, ConnectionValues values) {
		QQUserInfo userInfo = api.getUserInfo();
		values.setDisplayName(userInfo.getNickname());//昵称
		values.setImageUrl(userInfo.getFigureurl_qq_1());//图像URL地址
		values.setProfileUrl(null); // 主页地址，像微博一般有主页地址
		// 服务提供商返回的该user的openid 一般来说这个openid是和你的开发账户也就是appid绑定的
		values.setProviderUserId(userInfo.getOpenId());
	}

	/**
	 * 获取标准的用户信息
	 * @param api
	 * @return
	 */
	@Override
	public UserProfile fetchUserProfile(QQApi api) {
		return null;
	}
    //更新微博 qq没有用
	@Override
	public void updateStatus(QQApi api, String message) {

	}
}
