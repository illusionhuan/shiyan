package com.java.exception;

/**
 * 删除不允许例外
 *
 * @author mz
 * @date 2023/08/30
 */
public class DeletionNotAllowedException extends BaseException {

    public DeletionNotAllowedException(String msg) {
        super(msg);
    }

}
