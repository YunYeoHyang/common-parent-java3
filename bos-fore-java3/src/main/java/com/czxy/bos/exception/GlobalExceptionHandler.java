package com.czxy.bos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice           //对所有的controller进行增强
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)      //程序出现异常，将执行下面的方法
    public ResponseEntity<String> defaultErrorHandler(Exception e){
        //如果是自定义异常，将显示异常信息。如果不是，显示"联系管理员。。。。"
        if(e instanceof BosException){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR); //500
        } else {
            return new ResponseEntity<>("服务器异常，请联系管理员." , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
