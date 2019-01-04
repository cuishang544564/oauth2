package com.rui.tiger.auth.core.social.qq.api;

/**
 *  获取qq用户信息接口
 * qq互联平台： http://wiki.connect.qq.com/api%E5%88%97%E8%A1%A8
 * @author CaiRui
 * @date 2019-1-3 8:56
 */
public interface QQApi {

	/**
	 * 获取用户信息
	 * @return
	 * @throws Exception
	 */
	QQUserInfo getUserInfo() ;

}
