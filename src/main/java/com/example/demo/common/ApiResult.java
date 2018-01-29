package com.example.demo.common;

import java.io.Serializable;

/**
 * Created by wb-lwc235565 on 2018/1/26.
 */
public class ApiResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int SUCCESS = 200;
    public static final int BADE = 201;
    public static final int EXE_ERROR = 500;
    public static final int BAD_ERROR = 300;
    public static final String SUCCESSFUL = "success";
    public static final String FAILURE = "failure";
    public static final String BULID_ERROR = "build params error";
    public static final String EXEC_ERROR = "sdk exec unusual error";
    /**
     * result
     **/
    public int status = SUCCESS;
    /**
     * api method
     **/
    public String method;
    /**
     * msg
     **/
    public String msg;
    public T request;
    public T response;

    public ApiResult() {
        super();
    }

    public ApiResult(int status, String method, String msg, T req, T resp) {
        this.status = status;
        this.method = method;
        this.msg = msg;
        this.request = req;
        this.response = resp;
    }

    public static ApiResult isSuccess(String method, Object req, Object resp) {
        return new ApiResult(SUCCESS, method, SUCCESSFUL, req, resp);
    }

    public static ApiResult isFailure(String method, Object req, Object resp) {
        return new ApiResult(BADE, method, FAILURE, req, resp);
    }

    public static ApiResult isExecError(String method, Object req, Object resp) {
        return new ApiResult(EXE_ERROR, method, EXEC_ERROR, req, resp);
    }

    public static ApiResult isBuildError(String method, Object req, Object resp) {
        return new ApiResult(BAD_ERROR, method, BULID_ERROR, req, resp);
    }

}
