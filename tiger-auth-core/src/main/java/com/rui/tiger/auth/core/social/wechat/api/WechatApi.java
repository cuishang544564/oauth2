package com.rui.tiger.auth.core.social.wechat.api;

/**
 * 微信用户api接口
 * @author CaiRui
 * @Date 2019-01-12 12:08
 */
public interface WechatApi {
    /**
     *  获取微信用户信息
     * @param openId
     * @return
     */
    WechatUserInfo getUserInfo(String openId);
}
