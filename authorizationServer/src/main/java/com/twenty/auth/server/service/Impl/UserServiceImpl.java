package com.twenty.auth.server.service.Impl;

import com.twenty.auth.server.dao.UserEntityDao;
import com.twenty.auth.server.domain.UserEntity;
import com.twenty.auth.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/2/11 0011.
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserEntityDao userDao;

    @Override
    public UserEntity findByname(String username) {
        UserEntity userEntity=new UserEntity();
        userEntity.setUsername(username);
        List<UserEntity> userEntities=userDao.selectExample(userEntity);
        if(CollectionUtils.isEmpty(userEntities)){
            return null;
        }
        return userEntities.get(0) ;
    }
}
