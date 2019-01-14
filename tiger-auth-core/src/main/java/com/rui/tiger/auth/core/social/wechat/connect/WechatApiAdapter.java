package com.rui.tiger.auth.core.social.wechat.connect;

import com.rui.tiger.auth.core.social.wechat.api.WechatApi;
import com.rui.tiger.auth.core.social.wechat.api.WechatUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 微信 api适配器，将微信 api的数据模型转为spring social的标准模型。
 * @author CaiRui
 * @Date 2019-01-12 13:34
 */
public class WechatApiAdapter implements ApiAdapter<WechatApi> {
    private String openId;

    public WechatApiAdapter() {}

    public WechatApiAdapter(String openId){
        this.openId = openId;
    }

    /**
     * @param api
     * @return
     */
    @Override
    public boolean test(WechatApi api) {
        return true;
    }

    /**
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(WechatApi api, ConnectionValues values) {
        WechatUserInfo profile = api.getUserInfo(openId);
        values.setProviderUserId(profile.getOpenid());
        values.setDisplayName(profile.getNickname());
        values.setImageUrl(profile.getHeadimgurl());
    }

    /**
     * @param api
     * @return
     */
    @Override
    public UserProfile fetchUserProfile(WechatApi api) {
        return null;
    }

    /**
     * @param api
     * @param message
     */
    @Override
    public void updateStatus(WechatApi api, String message) {
        //do nothing
    }
}
