package com.czxy.bos.exception;

/**
 * 自定义异常类，在其他位置用于分区异常
 *      如果都是RuntTimeException，在程序无法区分，自动抛出的异常，还是程序运行时抛出的。
 *      提供自定义异常，如果是自己抛出的，就可以通过instanceof进行判断
 * Created by liangtong on 2018/9/4.
 */
public class BosException  extends RuntimeException{

    public BosException() {
    }

    public BosException(String message) {
        super(message);
    }

    public BosException(String message, Throwable cause) {
        super(message, cause);
    }

    public BosException(Throwable cause) {
        super(cause);
    }

    public BosException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
