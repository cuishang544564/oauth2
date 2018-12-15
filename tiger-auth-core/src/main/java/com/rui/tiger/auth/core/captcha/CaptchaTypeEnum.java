package com.rui.tiger.auth.core.captcha;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证码类型枚举类
 * @author CaiRui
 * @Date 2018/12/15 17:58
 */
public enum CaptchaTypeEnum {

    SMS("sms","短信"),
    IMAGE("image","图形验证码");

    CaptchaTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<String,CaptchaTypeEnum> codeLookup = new HashMap<String,CaptchaTypeEnum>();
    private String code;
    private String desc;

    static {
        for (CaptchaTypeEnum type : CaptchaTypeEnum.values()) {
            codeLookup.put(type.code, type);
        }
    }

    /**
     * 根据类型获取枚举类
     * @param code
     * @return
     */
    public static CaptchaTypeEnum forCode(String code)  {
        return codeLookup.get(code);
    }

    public String getCode() {
        return code;
    }

}
