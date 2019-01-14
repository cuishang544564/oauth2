package com.rui.tiger.auth.core.social.wechat.api;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author CaiRui
 * @Date 2019-01-12 12:24
 */
@Slf4j
public class WechatApiImpl extends AbstractOAuth2ApiBinding implements WechatApi {

    /**
     * 获取用户信息的url
     * https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID
     * access_token 父类会帮我们拼接
     */
    private static final String URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid=";

    /**
     * @param accessToken
     */
    public WechatApiImpl(String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }

    /**
     * 默认注册的StringHttpMessageConverter字符集为ISO-8859-1，而微信返回的是UTF-8的，所以覆盖了原来的方法。
     */
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        messageConverters.remove(0);
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return messageConverters;
    }

    @Override
    public WechatUserInfo getUserInfo(String openId) {
        String url = URL_GET_USER_INFO + openId;
        String response = getRestTemplate().getForObject(url, String.class);
        if(StringUtils.contains(response, "errcode")) {
            log.info("微信用户信息获取失败:"+response);
            return null;
        }
        WechatUserInfo  profile = null;
        try {
            profile = JSON.parseObject(response, WechatUserInfo.class);
        } catch (Exception e) {
            log.info("微信用户信息json转换异常",e);
        }
        return profile;
    }
}
