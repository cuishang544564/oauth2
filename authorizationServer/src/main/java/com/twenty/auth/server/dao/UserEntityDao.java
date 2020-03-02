package com.twenty.auth.server.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.twenty.auth.server.domain.UserEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author cuishang
 * @since 2020/2/29
 */
@Component
public interface UserEntityDao  extends BaseMapper<UserEntity> {
    /**
     * 匹配姓名得到用户
     * @param username
     * @return
     */
//    @Select("select * from user where username=#{username}")
//    UserEntity findByUsername(String username);

    List<UserEntity> selectExample(UserEntity userEntity);
}
