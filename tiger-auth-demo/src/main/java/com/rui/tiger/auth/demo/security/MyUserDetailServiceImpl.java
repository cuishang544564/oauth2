package com.rui.tiger.auth.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 自定义用户登录实现
 * 这里只要是存在一个自定义的 UserDetailsService ，那么security将会使用该实例进行配置
 *
 * @author CaiRui
 * @date 2018-12-5 8:19
 */
@Component
@Slf4j
public class MyUserDetailServiceImpl implements UserDetailsService, SocialUserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 账户密码登陆验证
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*//TODO 后续做成数据库实现（MyBaites-plus实现）先实现流程
		//1.根据用户名去数据库去查询用户信息获取加密后的密码 这里模拟一个加密的数据库密码
		String encryptedPassWord = passwordEncoder.encode("123456");
		log.info("模拟加密后的数据库密码:{}", encryptedPassWord);
		//2.这里可以去验证账户的其它相关信息 默认都通过
		//3.返回认证过的用户信息  授予一个admin的权限
		return new User(username,
				encryptedPassWord,
				true,
				true,
				true,
				true,
				AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));*/
        log.info("登录用户名:{}", username);
        return buildUser(username);
    }

    /**
     * 社交登陆
     *
     * @param userId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        log.info("社交登录用户ID:" + userId);
        return buildUser(userId);
    }

    private SocialUserDetails buildUser(String userId) {
        // 根据用户名查找用户信息
        //根据查找到的用户信息判断用户是否被冻结
        String password = passwordEncoder.encode("123456");
        log.info("数据库密码是:" + password);
        return new SocialUser(
                userId,
                password,
                true,
                true,
                true,
                true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin")
        );
    }

}