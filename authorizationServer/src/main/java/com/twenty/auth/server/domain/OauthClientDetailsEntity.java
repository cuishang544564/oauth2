package com.twenty.auth.server.domain;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;


/**
 * 
 *
 * @author cuishang
 * @since 2020/2/29
 */

@TableName("oauth_client_details")
@Data
public class OauthClientDetailsEntity {
    @TableId
    private String clientId;
    private String resourceIds;
    private String clientSecret;
    private String scope;
    private String authorizedGrantTypes;
    private String webServerRedirectUri;
    private String authorities;
    private Integer accessTokenValidity;
    private Integer refreshTokenValidity;
    private String additionalInformation;
    private String autoapprove="false";


}
