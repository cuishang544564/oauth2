package com.rui.tiger.auth.authorize.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author CaiRui
 * @date 2019-05-07 08:05
 */
@Component("rbacService")
public class RbacServiceImpl implements RbacService {

	private AntPathMatcher antPathMatcher=new AntPathMatcher();

	@Override
	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

		Object principal = authentication.getPrincipal();

		boolean hasPermission = false;

		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();

			//读取用户所拥有权限的所有URL 这个应该根据用户名从数据库中查询 此处只是模拟
			List<String> urls = new ArrayList<>();

			for (String url : urls) {
				//匹配 /user/* 这种格式
				if (antPathMatcher.match(url, request.getRequestURI())) {
					hasPermission = true;
					break;
				}
			}

		}
		return hasPermission;
	}
}
