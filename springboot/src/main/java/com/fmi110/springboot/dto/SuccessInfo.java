package com.fmi110.springboot.dto;

/**
 * dto : data transfer object 数据传输对象
 */
public class SuccessInfo<T> extends Info<T>{



    public SuccessInfo() {
        super.code = 200;
        super.message = "操作成功";
    }

    public SuccessInfo(T data) {
        super.code = 200;
        super.message = "操作成功";
        this.data = data;
    }

}
