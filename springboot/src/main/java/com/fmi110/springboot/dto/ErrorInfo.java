package com.fmi110.springboot.dto;

/**
 * dto : data transfer object 数据传输对象
 */
public class ErrorInfo<T> extends Info<T>{

    public ErrorInfo() {
        super.code = 500;
        super.message = "操作失败";
    }

    public ErrorInfo(T data) {
        super.code = 500;
        super.message = "操作失败";
        this.data = data;
    }

    public ErrorInfo(Integer code, String message, T data) {
        super(code, message, data);
    }
}
