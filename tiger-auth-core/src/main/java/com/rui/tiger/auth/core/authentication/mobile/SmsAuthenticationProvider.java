package com.rui.tiger.auth.core.authentication.mobile;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author CaiRui
 * @Date 2018/12/16 10:38
 */
public class SmsAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken smsCaptchaAuthenticationToken= (SmsAuthenticationToken) authentication;
        UserDetails user=userDetailsService.loadUserByUsername((String) smsCaptchaAuthenticationToken.getPrincipal());
        if(user==null){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        //认证通过
        SmsAuthenticationToken authenticationTokenResult=new SmsAuthenticationToken(user,user.getAuthorities());
        //将之前未认证的请求放进认证后的Token中
        authenticationTokenResult.setDetails(smsCaptchaAuthenticationToken.getDetails());
        return authenticationTokenResult;
    }

    //@Autowired
    @Getter
    @Setter
    private UserDetailsService userDetailsService;//

    /**
     * AuthenticationManager 验证该Provider是否支持 认证
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(SmsAuthenticationToken.class);
    }

}
