package com.rui.tiger.auth.app.captcha;

import com.rui.tiger.auth.core.captcha.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * redis存储验证码
 * @author CaiRui
 * @Date 2019-04-21 12:12
 */
@Component
public class RedisCaptchaRepository implements CaptchaRepository {
    /**
     * @see RedisAutoConfiguration#redisTemplate(org.springframework.data.redis.connection.RedisConnectionFactory)
     */
    @Autowired
    private RedisTemplate redisTemplate;
    /** 验证码放入redis规则模式：CODE_{TYPE}_{DEVICEId} */
    private final static String CODE_KEY_PATTERN = "CODE_%s_%s";


    @Override
    public void save(ServletWebRequest request, CaptchaVo code, CaptchaTypeEnum captchaType) {
        redisTemplate.opsForValue().set(buildKey(request, captchaType), code, 180, TimeUnit.MINUTES);
    }

    @Override
    public CaptchaVo get(ServletWebRequest request, CaptchaTypeEnum captchaType) {
        String key = buildKey(request, captchaType);
        // 拿到创建 create() 存储到session的code验证码对象
        return (CaptchaVo) redisTemplate.opsForValue().get(key);

    }

    @Override
    public void remove(ServletWebRequest request, CaptchaTypeEnum captchaType) {
        String key = buildKey(request, captchaType);
        redisTemplate.delete(key);
    }

    /**
     * 构建验证码放入redis时的key; 在保存的时候也使用该key
     * {@link AbstractCaptchaProcessor#save(org.springframework.web.context.request.ServletWebRequest, com.rui.tiger.auth.core.captcha.CaptchaVo)
     * @param validateCodeType
     * @return
     */
    private String buildKey(ServletWebRequest request, CaptchaTypeEnum validateCodeType) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new CaptchaException("请在请求头中携带deviceId参数");
        }
        return String.format(CODE_KEY_PATTERN, validateCodeType, deviceId);
    }

}
