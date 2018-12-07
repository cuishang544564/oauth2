package com.rui.tiger.auth.core.properties;

import com.rui.tiger.auth.core.model.enums.LoginTypeEnum;

/**
 * 浏览器配置
 *
 * @author CaiRui
 * @date 2018-12-6 8:42
 */
public class BrowserProperties {
    /**
     * 登录页面 不配置默认标准登录界面
     */
    private String loginPage = "/tiger-login.html";
    /**
     * 跳转类型 默认返回json数据
     */
    private LoginTypeEnum loginType = LoginTypeEnum.JSON;


    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginTypeEnum getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginTypeEnum loginType) {
        this.loginType = loginType;
    }
}
