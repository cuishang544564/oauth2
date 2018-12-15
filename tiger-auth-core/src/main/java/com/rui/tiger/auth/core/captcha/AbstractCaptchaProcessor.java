package com.rui.tiger.auth.core.captcha;

import org.apache.commons.lang.StringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

/**
 * 验证码处理器抽象父类
 * @author CaiRui
 * @Date 2018/12/15 18:21
 */
public abstract class AbstractCaptchaProcessor<C extends CaptchaVo> implements CaptchaProcessor {

    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();
    /**
     * 创建验证码
     *
     * @param request 封装请求和响应
     * @throws Exception
     */
    @Override
    public void create(ServletWebRequest request) throws Exception {
        //生成
        C captcha=generateCaptcha(request);
        //保存
        save(request,captcha);
        //发送
        send(request,captcha);
    }

    protected abstract C generateCaptcha(ServletWebRequest request);

    protected abstract void send(ServletWebRequest request, C captcha) throws IOException, ServletRequestBindingException;

    private void save(ServletWebRequest request, C captcha) {
        sessionStrategy.setAttribute(request, CAPTCHA_SESSION_KEY+getCaptchaTypeFromUrl(request), captcha);
    }

    /**
     * 根据请求的url获取校验码的类型
     * @param request
     * @return
     */
    private String getCaptchaTypeFromUrl(ServletWebRequest request) {
        return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/captcha/");
    }


}
