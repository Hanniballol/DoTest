package com.net.retrofit.exception;

/**
 * autour: hannibal
 * date: 2017/12/25
 * e-mail:404769122@qq.com
 * description:
 */
public class TokenInvalidException extends RuntimeException{
    @Override
    public String getMessage() {
        return "token失效";
    }
}
