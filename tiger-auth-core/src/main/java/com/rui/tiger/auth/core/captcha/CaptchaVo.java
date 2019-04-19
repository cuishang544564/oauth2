package com.rui.tiger.auth.core.captcha;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 验证码
 *
 * @author CaiRui
 * @Date 2018/12/15 9:11
 */
@Data
public class CaptchaVo implements Serializable {
    /**
     * 验证码
     */
    private String code;
    /**
     * 失效时间 这个通常放在缓存中或维护在数据库中
     */
    private LocalDateTime expireTime;

    public CaptchaVo(String code, int expireAfterSeconds) {
        this.code = code;
        //多少秒后
        this.expireTime = LocalDateTime.now().plusSeconds(expireAfterSeconds);
    }

    public CaptchaVo(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }
    /**
     * 是否失效
     *
     * @return
     */
    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
