package com.rui.tiger.auth.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rui.tiger.auth.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.config.annotation.configuration.ClientDetailsServiceConfiguration;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

/**
 * 认证成功处理器
 * {@link SavedRequestAwareAuthenticationSuccessHandler}是Spring Security默认的成功处理器
 *
 * @author CaiRui
 * @date 2018-12-6 12:39
 */
@Component("tigerAuthenticationSuccessHandler")
@Slf4j
public class TigerAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private SecurityProperties securityProperties;
	/**
	 * 授权服务器：自动配置的
	 * @see ClientDetailsServiceConfiguration#clientDetailsService()
	 */
	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private AuthorizationServerTokenServices authorizationServerTokenServices;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		log.info("登录成功");
		/**
		 * @see BasicAuthenticationFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, javax.servlet.FilterChain)
		 *  */
		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith("Basic ")) {
			// 不被认可的客户端异常
			throw new UnapprovedClientAuthenticationException("没有Authorization请求头");
		}
		// 解析请Authorization 获取client信息 client-id: tigerauth client-secret: 123456
		String[] tokens = extractAndDecodeHeader(header, request);
		assert tokens.length == 2;
		String clientId = tokens[0];
		String clientSecret = tokens[1];
		ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
		// 判定提交的是否与查询的匹配
		if (clientDetails == null) {
			throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
		} else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
			throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
		}
		/**  @see DefaultOAuth2RequestFactory#createTokenRequest(java.util.Map, ClientDetails)
		 * requestParameters,不同的授权模式有不同的参数，这里自定义的模式，没有参数
		 * String clientId,
		 * Collection<String> scope, 给自己的前段使用，默认用所有的即可
		 * String grantType 自定义 custom
		 * */
		TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_SORTED_MAP, clientId, clientDetails.getScope(), "custom");
		OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
		/**
		 * @see org.springframework.security.oauth2.provider.token.AbstractTokenGranter#getOAuth2Authentication(ClientDetails, TokenRequest)
		 * */
		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
		OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
		response.setContentType("application/json;charset=UTF-8");
	/*	log.info("TOKEN信息:" + JSON.toJSONString(accessToken));
		response.getWriter().write(JSON.toJSONString(accessToken));*/
		log.info("jack TOKEN信息:" + objectMapper.writeValueAsString(accessToken));
		response.getWriter().write(objectMapper.writeValueAsString(accessToken));
	}

	/**
	 * Decodes the header into a username and password.
	 * @throws BadCredentialsException if the Basic header is not present or is not valid
	 *                                 Base64
	 */
	private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {
		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.getDecoder().decode(base64Token);
		} catch (IllegalArgumentException e) {
			throw new BadCredentialsException(
					"Failed to decode basic authentication token");
		}
		String token = new String(decoded, "UTF-8");
		int delim = token.indexOf(":");
		if (delim == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		}
		return new String[]{token.substring(0, delim), token.substring(delim + 1)};
	}

}
