package com.twenty.auth.server.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.twenty.auth.server.domain.OauthClientDetailsEntity;
import org.springframework.stereotype.Component;

@Component
public interface OauthClientDetailsDao  extends BaseMapper<OauthClientDetailsEntity> {
}
