package com.rpc.exception;

/**
 * <p>
 * bean初始化错误异常
 * </p>
 *
 * @author kiki
 * @date 2021/6/6
 */
public class BeanErrorException extends Exception {

    public BeanErrorException(String message) {
        super(message);
    }

    public BeanErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
