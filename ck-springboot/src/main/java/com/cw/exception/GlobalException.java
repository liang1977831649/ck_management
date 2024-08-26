package com.cw.exception;

import com.cw.entity.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public Result allExceptionHandler(Exception e){
        e.printStackTrace();
        return Result.error(!(null==e.getMessage()&&("".equals(e.getMessage())))? e.getMessage() : "您发生了某个异常");
    }
}