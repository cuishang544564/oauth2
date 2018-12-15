package com.rui.tiger.auth.core.captcha.sms;

import com.rui.tiger.auth.core.captcha.sms.SmsCaptchaSend;
import lombok.extern.slf4j.Slf4j;

/**
 * @author CaiRui
 * @Date 2018/12/15 10:05
 */
@Slf4j
public class DefaultSmsCaptchaSender implements SmsCaptchaSend {

    //实际生产环境中,调用渠道供应商发送短信
    @Override
    public boolean sendSms(String mobile, String code) {
        log.info("模拟向手机{}发送短信验证码{}",mobile,code);
        log.info("短信渠道发送中...发送成功");
        return true;
    }

}
