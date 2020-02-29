package com.twenty.auth.server.service;

import com.twenty.auth.server.domain.OauthClientDetailsEntity;

/**
 * Created by wangxiangyun on 2017/2/14.
 */
public interface IClientDetailsService {
    void save(OauthClientDetailsEntity client);
}
