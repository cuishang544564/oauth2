package com.twenty.auth.server.service;

import com.twenty.auth.server.domain.UserEntity;

/**
 * Created by Administrator on 2017/2/11 0011.
 */
public interface IUserService {

    UserEntity findByname(String username);
}
