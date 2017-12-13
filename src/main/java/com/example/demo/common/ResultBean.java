package com.example.demo.common;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by wb-lwc235565 on 2017/12/5.
 */
@Data
public class ResultBean<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int SUCCESS_CODE = 10000;
    public static final int FAIL_CODE = 20000;
    public static final int BADE_CODE = 40000;
    public static final String SUCCESSFUL = "success";
    public static final String FAILURE = "failure";
    public static final String ERROR = "channel data unusual";
    private int code = SUCCESS_CODE;
    private String msg = SUCCESSFUL;
    private T data;

    public ResultBean() {
        super();
    }

    public ResultBean(T data) {
        super();
        this.data = data;
    }

    public ResultBean(int code, T data) {
        super();
        this.code = code;
        this.data = data;
    }


    public ResultBean(int code, String msg, T data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResultBean isSuccess(Object data) {
        return new ResultBean(data);
    }

    public static ResultBean isFailure(Object data) {
        return new ResultBean(FAIL_CODE, FAILURE, data);
    }

    public static ResultBean isThrows(Object data) {
        return new ResultBean(BADE_CODE, ERROR, data);
    }

}
