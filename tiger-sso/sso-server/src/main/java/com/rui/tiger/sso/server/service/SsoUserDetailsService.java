package com.rui.tiger.sso.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author CaiRui
 * @date 2019-05-05 15:42
 */
@Component
@Slf4j
public class SsoUserDetailsService implements UserDetailsService {


	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String password = passwordEncoder.encode("123456");
		//TODO 这里实际是查询数据库获取
		log.info("用户名 {}，数据库密码{}", username, password);
		User admin = new User(username,
//                              "{noop}123456",
				password,
				true, true, true, true,
				AuthorityUtils.commaSeparatedStringToAuthorityList(""));
		return admin;
	}
}
