package com.example.demo.common;

import java.io.Serializable;

/**
 * Created by wb-lwc235565 on 2018/1/26.
 */
public class ApiResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int SUCCESS = 200;
    public static final int  BADE= 201;
    public static final int ERROR = 500;
    /**result**/
    public int status=SUCCESS;
    /**api method**/
    public String method;
    public String  alipayRequest;
    public T resp;
    public ApiResult(){super();}
    public ApiResult(int status,String method,String req,T resp){
        this.status=status;
        this.method=method;
        this.alipayRequest=req;
        this.resp=resp;
    }
    public static ApiResult isSuccess(String method,String req,Object resp) {
        return new ApiResult(SUCCESS,method,req,resp);
    }

    public static ApiResult isFailure(String method,String req,Object resp) {
        return new ApiResult(BADE, method,req,resp);
    }

    public static ApiResult isError(String method,String req,Object resp) {
        return new ApiResult(ERROR,method,req,resp);
    }

}
