package com.rui.tiger.auth.demo.controller;

import com.rui.tiger.auth.core.properties.SecurityProperties;
import com.rui.tiger.auth.demo.vo.UserVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.UnsupportedEncodingException;

/**
 * 用户控制器
 *
 * @author CaiRui
 * @date 2018-12-6 8:16
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	private ProviderSignInUtils providerSignInUtils;

	@Autowired
	private SecurityProperties securityProperties;

	/*@Autowired
	private AppSignUpUtils appSignUpUtils;*/

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
	 *
	 * @return
	 */
	@GetMapping("me/{userId}")
	public Authentication getCurrentAuthentication(@PathVariable String userId) {
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
	public Object getCurrentAuthentication2(Authentication authentication,HttpServletRequest request) throws UnsupportedEncodingException {
		String authorization = request.getHeader("Authorization");
		String token = StringUtils.substringAfter(authorization, "bearer ");
		String jwtSigningKey = securityProperties.getOauth2().getJwtSigningKey();
		// 生成的时候使用的是 org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
		// 源码里面把signingkey变成utf8了
		// JwtAccessTokenConverter类，解析出来是一个map
		// 所以这个自带的JwtAccessTokenConverter对象也是可以直接用来解析的
		byte[] bytes = jwtSigningKey.getBytes("utf-8");
		Claims body = Jwts.parser().setSigningKey(bytes).parseClaimsJws(token).getBody();
		String company = (String) body.get("company");
		log.info("公司名称:{}",company);
		return body;
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
	//	appSignUpUtils.doPostSignUp(username,new ServletWebRequest(request));
		providerSignInUtils.doPostSignUp(username, new ServletWebRequest(request));
	}


}
