package com.rui.tiger.auth.core.properties;

/**
 * 社交配置
 * @author CaiRui
 * @Date 2019/1/5 12:49
 */
public class SocialProperties {

    private QQProperties qq=new QQProperties();

    private WechatProperties wechat=new WechatProperties();

    private String filterProcessesUrl="/auth";//默认的地址


    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public WechatProperties getWechat() {
        return wechat;
    }

    public void setWechat(WechatProperties wechat) {
        this.wechat = wechat;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }
}
