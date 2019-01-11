package com.rui.tiger.auth.browser.support;

import lombok.Data;

/**
 * 社交用户信息封装
 * @author CaiRui
 * @date 2019-01-09 18:24
 */
@Data
public class SocialUserInfo {

	private String providerId;//供应商ID

	private String providerUserId;//供应商用户ID 即openID

	private String nickName;//昵称

	private String headImg;//图像地址

}
