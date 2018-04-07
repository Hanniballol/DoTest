package com.net.retrofit.bean;

/**
 * autour: hannibal
 * date: 2017/12/25
 * e-mail:404769122@qq.com
 * description:
 */
public class HttpStatus {
    private int code;
    private String message;

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

    public boolean isCodeInvalid() {
        return code == HttpStatusEnum.TOKEN_INVALID.getCode();
    }
}
