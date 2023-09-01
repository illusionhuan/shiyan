package com.java.exception;

/**
 * 用户没有登录异常
 *
 * @author mz
 * @date 2023/08/30
 */
public class UserNotLoginException extends BaseException {

    public UserNotLoginException() {
    }

    public UserNotLoginException(String msg) {
        super(msg);
    }

}
