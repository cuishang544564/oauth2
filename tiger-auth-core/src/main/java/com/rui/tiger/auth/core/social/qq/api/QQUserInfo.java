package com.rui.tiger.auth.core.social.qq.api;

import lombok.Data;

/**
 * QQ用户信息封装
 * @author CaiRui
 * @date 2019-1-3 8:56
 */
@Data
public class QQUserInfo {

	/**
	 * 返回码 0 成功
	 * 详见公共返回码说明
	 * http://wiki.connect.qq.com/%E5%85%AC%E5%85%B1%E8%BF%94%E5%9B%9E%E7%A0%81%E8%AF%B4%E6%98%8E
	 */
	private String ret;
	/**
	 * 如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。
	 */
	private String msg;
	/**
	 *
	 */
	private String openId;
	/**
	 * 不知道什么东西，文档上没写，但是实际api返回里有。
	 */
	private String is_lost;
	/**
	 * 省(直辖市)
	 */
	private String province;
	/**
	 * 市(直辖市区)
	 */
	private String city;
	/**
	 * 出生年月
	 */
	private String year;
	/**
	 * 用户在QQ空间的昵称。
	 */
	private String nickname;
	/**
	 * 大小为30×30像素的QQ空间头像URL。
	 */
	private String figureurl;
	/**
	 * 大小为50×50像素的QQ空间头像URL。
	 */
	private String figureurl_1;
	/**
	 * 大小为100×100像素的QQ空间头像URL。
	 */
	private String figureurl_2;
	/**
	 * 大小为40×40像素的QQ头像URL。
	 */
	private String figureurl_qq_1;
	/**
	 * 大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100×100的头像，但40×40像素则是一定会有。
	 */
	private String figureurl_qq_2;
	/**
	 * 性别。 如果获取不到则默认返回”男”
	 */
	private String gender;
	/**
	 * 标识用户是否为黄钻用户（0：不是；1：是）。
	 */
	private String is_yellow_vip;
	/**
	 * 标识用户是否为黄钻用户（0：不是；1：是）
	 */
	private String vip;
	/**
	 * 黄钻等级
	 */
	private String yellow_vip_level;
	/**
	 * 黄钻等级
	 */
	private String level;
	/**
	 * 标识是否为年费黄钻用户（0：不是； 1：是）
	 */
	private String is_yellow_year_vip;
}
