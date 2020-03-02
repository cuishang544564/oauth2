package com.twenty.auth.server.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author cuishang
 * @since 2020/2/29
 */
@Configuration
@MapperScan(basePackages = {"com.twenty.auth.server.dao"})
public class MybatisPlusConfig {
}
