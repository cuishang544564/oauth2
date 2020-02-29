package com.twenty.auth.server.service.Impl;

import com.twenty.auth.server.domain.UserEntity;
import com.twenty.auth.server.domain.Authority;
import com.twenty.auth.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private IUserService iUserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity= iUserService.findByname(username);
		String name = userEntity.getUsername();
		String password = userEntity.getPassword();
		List<Authority> authorities = new ArrayList<Authority>();
		if(username.equals("1")){

			authorities.add(new Authority(1L, "ROLE_ADMIN"));
			authorities.add(new Authority(2L, "ROLE_USER"));

		}

		return new User(name, password, true, true, true, true, authorities);
	}

}