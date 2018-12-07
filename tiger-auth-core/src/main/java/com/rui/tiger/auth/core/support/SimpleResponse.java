package com.rui.tiger.auth.core.support;


/**
 * @author CaiRui
 * @date 2018-12-5 12:46
 */
public class SimpleResponse {

    private int code;
    private String message;
    private Object data;

    public SimpleResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public SimpleResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public SimpleResponse(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
