package com.java.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 后端统一返回结果
 *
 * @author mz
 * @date 2023/08/31
 */
@Data
public class Result<T> implements Serializable {

    private Integer code; //编码:1,成功；0和其他为失败
    private String msg; //错误信息
    private T data; //数据

    public static <T> Result<T> success(){
        Result<T> result = new Result<T>();
        result.code = 1;
        return result;
    }

    public static <T> Result<T> success(T object){
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 1;
        return result;
    }

    public static <T> Result<T> error(String msg){
//        Result<T> result = new Result<T>();
        Result result = new Result();
        result.msg = msg;
        result.code = 0;
        return result;
    }
}
