package com.twenty.auth.server.service.Impl;

import com.twenty.auth.server.dao.OauthClientDetailsDao;
import com.twenty.auth.server.domain.OauthClientDetailsEntity;
import com.twenty.auth.server.service.IClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 *
 * @author cuishang
 * @since 2020/2/29
 */

@Service
public class ClientDetailsServiceImpl implements IClientDetailsService {
    @Autowired
    private OauthClientDetailsDao oauthClientDetailsDao;
    @Override
    public void save(OauthClientDetailsEntity client) {
        oauthClientDetailsDao.insert(client);
    }
}
