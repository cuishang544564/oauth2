package com.rui.tiger.auth.core.captcha.sms;

import com.rui.tiger.auth.core.captcha.CaptchaGenerate;
import com.rui.tiger.auth.core.captcha.CaptchaVo;
import com.rui.tiger.auth.core.properties.SecurityProperties;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 短信验证码生成器
 *
 * @author CaiRui
 * @Date 2018/12/15 9:10
 */
@Component("smsCaptchaGenerate")
public class SmsCaptchaGenerate implements CaptchaGenerate {

    @Autowired
    private SecurityProperties securityProperties;
    /**
     * 生成短信验证码
     *
     * @return
     */
    @Override
    public CaptchaVo generate() {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCaptcha().getSms().getLength());
        return new CaptchaVo(code, securityProperties.getCaptcha().getSms().getExpireSeconds());
    }
}
