package com.rui.tiger.auth.demo.controller;

import com.rui.tiger.auth.demo.vo.UserVo;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 用户控制器
 *
 * @author CaiRui
 * @date 2018-12-6 8:16
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private ProviderSignInUtils providerSignInUtils;

	/*@Autowired
	private AppSignUpUtils appSignUpUtils;
*/
	@RequestMapping("/hello")
	public String hello() {
		return "Hello,World";
	}

	/**
	 * 获取用户认证信息
	 *
	 * @return
	 */
	@GetMapping("me")
	public Authentication getCurrentAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取用户认证信息
	 * 同getCurrentAuthentication spring 会帮我们注入
	 *
	 * @param authentication
	 * @return
	 */
	@GetMapping("me/auto")
	public Authentication getCurrentAuthentication2(Authentication authentication) {
		return authentication;
	}

	/**
	 * 社交注册
	 */
	@PostMapping("/regist")
	public void regist(UserVo user, HttpServletRequest request){

		 // 不管是注册用户还是绑定用户，都会拿到一个用户唯一标识
		 String username=user.getUsername();
		//这里处理绑定或注册用户逻辑

		//进行系统用户和社交用户入库动作
		//appSignUpUtils.doPostSignUp(username,new ServletWebRequest(request));
		providerSignInUtils.doPostSignUp(username, new ServletWebRequest(request));
	}


}
