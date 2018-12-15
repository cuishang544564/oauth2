package com.rui.tiger.auth.core.captcha;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author CaiRui
 * @Date 2018/12/15 21:27
 */
public interface CaptchaCreateService {
    /**
     *  生成验证码
     * @param request
     * @param type
     */
    void createCaptcha(ServletWebRequest request, String type);
}
