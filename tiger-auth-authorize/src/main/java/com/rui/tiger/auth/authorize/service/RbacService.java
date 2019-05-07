package com.rui.tiger.auth.authorize.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户权限判断接口
 * @author CaiRui
 * @date 2019-05-07 08:02
 */
public interface RbacService {

	boolean hasPermission(HttpServletRequest request, Authentication authentication);

}
