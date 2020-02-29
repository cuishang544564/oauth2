package com.twenty.auth.server.service.Impl;

import com.twenty.auth.server.dao.OauthClientDetailsDao;
import com.twenty.auth.server.domain.OauthClientDetailsEntity;
import com.twenty.auth.server.service.IClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangxiangyun on 2017/2/14.
 */
@Service
public class ClientDetailsServiceImpl implements IClientDetailsService {
    @Autowired
    private OauthClientDetailsDao oauthClientDetailsDao;
    @Override
    public void save(OauthClientDetailsEntity client) {
        oauthClientDetailsDao.save(client);
    }
}
