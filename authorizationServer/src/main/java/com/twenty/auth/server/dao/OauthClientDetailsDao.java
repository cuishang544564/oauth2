package com.twenty.auth.server.dao;

import com.twenty.auth.server.domain.OauthClientDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wangxiangyun on 2017/2/14.
 */
public interface OauthClientDetailsDao  extends JpaRepository<OauthClientDetailsEntity,String>{
}
