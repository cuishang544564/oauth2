package com.rui.tiger.auth.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * redis配置
 * @author CaiRui
 * @date 2019-04-22 12:15
 */
@Configuration
public class RedisConfig {

	@Bean
	public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory connectionFactory){
		RedisTemplate<Object,Object> redisTemplate=new RedisTemplate<>();
		RedisSerializer stringRedisSerializer=redisTemplate.getStringSerializer();
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setConnectionFactory(connectionFactory);
		return redisTemplate;
	}


}
