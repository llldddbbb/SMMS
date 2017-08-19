package com.smms.common.exception;

/**
 * 自定义异常
 */
public class MyException extends RuntimeException{

    private String msg;
    private int code=500;

    public MyException() {
    }

    public MyException(String msg) {
        super(msg);
        this.msg = msg;
    }
    public MyException(String msg,int code) {
        super(msg);
        this.msg = msg;
        this.code=code;
    }

    public MyException(String msg, Throwable cause) {
        super(msg, cause);
        this.msg = msg;
    }

    public MyException(String msg,int code, Throwable cause) {
        super(msg, cause);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
