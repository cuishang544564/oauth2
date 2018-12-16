package com.rui.tiger.auth.core.properties;

/**
 * 短信验证码配置类
 * @author CaiRui
 * @Date 2018/12/15 9:30
 */
public class SmsCaptchaProperties {
    /**
     * 长度
     */
    private int length=6;
    /**
     * 过期秒数 默认3分钟
     */
    private int expireSeconds=180;

    /**
     * 短信验证码拦截的路径 多个路径以,(逗号)进行分割
     */
    private String interceptUrl;



    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public String getInterceptUrl() {
        return interceptUrl;
    }

    public void setInterceptUrl(String interceptUrl) {
        this.interceptUrl = interceptUrl;
    }
}
