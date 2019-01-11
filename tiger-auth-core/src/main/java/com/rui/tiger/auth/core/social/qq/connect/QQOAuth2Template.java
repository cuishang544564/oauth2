package com.rui.tiger.auth.core.social.qq.connect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 *
 * @author CaiRui
 * @Date 2019-01-06 14:55
 */
@Slf4j
public class QQOAuth2Template extends OAuth2Template{

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        // 会发现返回的信息是 callback( {"error":100002,"error_description":"param client_secret is wrong or lost "} )
        // 通过debug可以发现，传递过来的参数少了2个，对比文档中的；
        // 调用本方法之前传递过来的参数，也就是 exchangeForAccess() 方法
        // 其中有一个 useParametersForClientAuthentication 属性需要为true才会携带另外另个参数
        setUseParametersForClientAuthentication(true);//默认是false
    }


    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        // 添加一个处理 [text/plan] 格式的转换器  qq返回的不是json
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("utf-8")));
        return restTemplate;
    }

    // http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
    // 文档中说明：响应的是 access_token=FE04************************CCE2&expires_in=7776000&refresh_token=88E4************************BE14
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String responseStr=getRestTemplate().postForObject(accessTokenUrl,parameters,String.class);
        log.info("获取accessToken响应:{}", responseStr);
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        String expiresIn = StringUtils.substringAfterLast(items[1], "=");
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");
        AccessGrant accessGrant = new AccessGrant(accessToken, null, refreshToken, new Long(expiresIn));
        return accessGrant;
    }
}
