package com.rui.tiger.auth.core.captcha.sms;

/**
 * 短信验证码发送接口
 * @author CaiRui
 * @Date 2018/12/15 10:03
 */
public interface SmsCaptchaSend {

    /**
     * 发送短信验证码
     * @param mobile
     * @param code
     * @return
     */
    boolean sendSms(String mobile,String code);

}
