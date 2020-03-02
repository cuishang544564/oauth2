package com.twenty.auth.server.service.Impl;

import com.twenty.auth.server.domain.Menu;
import com.twenty.auth.server.domain.Role;
import com.twenty.auth.server.domain.UserEntity;
import com.twenty.auth.server.domain.Authority;
import com.twenty.auth.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
		List<GrantedAuthority> authorities = new ArrayList<>();
		if(!CollectionUtils.isEmpty(userEntity.getRoles())){
			for(Role role:userEntity.getRoles()){
				authorities.add(new SimpleAuth(role.getRoleCode()));
				if(!CollectionUtils.isEmpty(role.getMenus())){
					for(Menu menu:role.getMenus()){
						if(!CollectionUtils.isEmpty(menu.getPerms())){
							menu.getPerms().stream().forEach(perm -> authorities.add(new SimpleAuth(perm.getPermCode())));
						}
					}
				}
			}
		}
//		if(username.equals("1")){
//
//			authorities.add(new Authority(1L, "ROLE_ADMIN"));
//			authorities.add(new Authority(2L, "ROLE_USER"));
//
//		}

		return new User(name, password, true, true, true, true, authorities);
	}
	class SimpleAuth implements GrantedAuthority {
		private String authority;

		public SimpleAuth(String authority) {
			this.authority = authority;
		}

		@Override
		public String getAuthority() {
			return authority;
		}
	}
}