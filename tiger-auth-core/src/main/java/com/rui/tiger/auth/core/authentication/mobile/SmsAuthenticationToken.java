package com.rui.tiger.auth.core.authentication.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 手机token
 * 参照：UsernamePasswordAuthenticationToken
 * @author CaiRui
 * @Date 2018/12/15 22:28
 */
public class SmsAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 500L;
    private final Object principal;//用户名
    //private Object credentials; 密码 手机登录验证码在登录前已经验证 考虑手机验证码通用 没有放到这里

    /**
     * 没有认证成功
     * @param mobile 手机号
     */
    public SmsAuthenticationToken(Object mobile) {
        super((Collection)null);
        this.principal = mobile;
        this.setAuthenticated(false);
    }

    /**
     * 认证成功同时进行权限设置
     * @param principal
     * @param authorities
     */
    public SmsAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    public Object getCredentials() {
        return null;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if(isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
