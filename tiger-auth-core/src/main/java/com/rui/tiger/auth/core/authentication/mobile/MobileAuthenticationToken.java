package com.rui.tiger.auth.core.authentication.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author CaiRui
 * @Date 2018/12/15 22:28
 */
public class MobileAuthenticationToken extends AbstractAuthenticationToken {

    public MobileAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    @Override
    public Object getCredentials() {
        return null;
    }


    @Override
    public Object getPrincipal() {
        return null;
    }
}
