package com.twenty.dommain.entity.common;

/**
 * @author cuishang
 * @since 2020/2/20
 */
public class Result<T> {
    private int errorcode;
    private String errormsg;
    private T data;

    public Result(int errorcode, String errormsg) {
        this.errorcode = errorcode;
        this.errormsg = errormsg;
    }

    public Result(int errorcode, String errormsg, T data) {
        this.errorcode = errorcode;
        this.errormsg = errormsg;
        this.data = data;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
