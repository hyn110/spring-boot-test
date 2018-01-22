package com.fmi110.springboot.dto;

/**
 * dto : data transfer object 数据传输对象
 */
public class Info<T> {

    public static final Integer OK = 0;
    public static final Integer ERROR = 100;

    protected Integer code;
    protected String  message;
    protected String  url;
    protected T       data;

    public Info() {
    }

    public Info(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Info(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
