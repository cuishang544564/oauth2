package com.rui.tiger.auth.core.properties;

/**
 * oauth2配置
 * @author CaiRui
 * @Date 2019-05-01 16:17
 */
public class OAuth2Properties {
    private OAuth2ClientProperties[] clients = {};

    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }
}
