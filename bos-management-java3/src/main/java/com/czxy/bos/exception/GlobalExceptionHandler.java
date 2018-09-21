package com.czxy.bos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** 全局异常处理类
 * Created by liangtong on 2018/9/4.
 */
@ControllerAdvice           //对当前项目的所有的controller进行增强
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)      //只要是异常，都执行此方法
    public ResponseEntity<String> defaultErrorHandler( Exception e ){
        e.printStackTrace();
        //如果是自定义异常
        if(e instanceof BosException){
            //自定义异常，显示异常中的信息
            return new ResponseEntity<String>(e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            //如果不是
            return new ResponseEntity<String>("服务器异常，请稍后重试" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
