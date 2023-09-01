package com.java.handler;

import com.java.constant.MessageConstant;
import com.java.exception.BaseException;
import com.java.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理程序
 *
 * @author mz
 * @date 2023/08/31
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /*
    *
    *  捕获业务异常
    * */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息:{}",ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /*
    *  捕获SQL异常
    * */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        //Duplicate entry 'zhangsan' for key 'employee.idx_username'
        String message = ex.getMessage();
        if(message.contains("Duplicate entry")){
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.ALREADY_EXISTS;
            return Result.error(msg);
        }else{
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }
}
