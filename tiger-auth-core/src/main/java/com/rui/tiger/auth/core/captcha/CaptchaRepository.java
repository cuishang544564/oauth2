package com.rui.tiger.auth.core.captcha;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码存储接口
 * (适配浏览器和app)
 * @author CaiRui
 * @Date 2019-04-21 12:06
 */
public interface CaptchaRepository {
    /**
     * 保存验证码
     * @param request
     * @param code
     * @param captchaType
     */
    void save(ServletWebRequest request, CaptchaVo code, CaptchaTypeEnum captchaType);

    /**
     * 获取验证码
     * @param request
     * @param captchaType
     * @return
     */
    CaptchaVo get(ServletWebRequest request,  CaptchaTypeEnum captchaType);

    /**
     * 移除验证码
     * @param request
     * @param captchaType
     */
    void remove(ServletWebRequest request,  CaptchaTypeEnum captchaType);

}
