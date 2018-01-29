
package com.example.demo.common;

import com.alibaba.fastjson.JSON;
import com.alipay.api.*;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by wb-lwc235565 on 2018/1/26.
 */

public class ExecuteHelper {

    /***
     * 构造请求参数，映射对应实体类，拆分授权、应用信息
     * @param request
     * @return resp
     */
    public static ApiResult buildRequest(HttpServletRequest request) {
        ApiResult api;
        Map map = new HashMap();
        Enumeration pNames = request.getParameterNames();
        while (pNames.hasMoreElements()) {
            String name = (String) pNames.nextElement();
            String value = request.getParameter(name);
            map.put(name, value);
        }
        ConfigConstants configConstants = new ConfigConstants();
        configConstants.setAuth_token(StringUtils.isEmpty(request.getParameter(ConfigConstants.ACCESS_TOKEN)) ? null : request.getParameter(ConfigConstants.ACCESS_TOKEN));
        configConstants.setApp_auth_token(StringUtils.isEmpty(request.getParameter(ConfigConstants.APP_AUTH_TOKEN)) ? null : request.getParameter(ConfigConstants.APP_AUTH_TOKEN));
        configConstants.setReturn_url(StringUtils.isEmpty(request.getParameter(ConfigConstants.RETURN_URL)) ? null :request.getParameter(ConfigConstants.RETURN_URL));
        configConstants.setNotify_url(StringUtils.isEmpty(request.getParameter(ConfigConstants.NOTIFY_URL)) ? null : request.getParameter(ConfigConstants.NOTIFY_URL));
        String method = map.get(ConfigConstants.METHOD).toString();
        StringBuffer requestPath = new StringBuffer(ConfigConstants.REQUEST_PATH);
        requestPath.append(method);
        Class<?> requestClass;
        StringBuffer modelPath = new StringBuffer(ConfigConstants.MODEL_PATH);
        modelPath.append(method.replace(ConfigConstants.REQUEST, ConfigConstants.MODEL));
        Class<?> modelClass;
        StringBuffer responsePath = new StringBuffer(ConfigConstants.RESPONSE_PATH);
        responsePath.append(method.replace(ConfigConstants.REQUEST, ConfigConstants.RESPONSE));
        Class<?> responseClass;
        AlipayRequest alipayRequest;
        AlipayObject alipayObject;
        AlipayResponse alipayResponse;
        try {
            requestClass = Class.forName(requestPath.toString());
            alipayRequest = (AlipayRequest) requestClass.newInstance();
            modelClass = Class.forName(modelPath.toString());
            alipayObject = (AlipayObject) JSON.parseObject(JSON.toJSONString(map), modelClass);
            responseClass = Class.forName(responsePath.toString());
            alipayResponse = (AlipayResponse) responseClass.newInstance();
            api = exec(alipayRequest, alipayResponse, alipayObject, configConstants, method, askType(method));
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            api = ApiResult.isBuildError(method, request, e);
        }
        return api;
    }

    /***
     * 调用sdk执行分发请求
     * @param alipayRequest 请求
     * @param _alipayResponse 响应
     * @param alipayObject  入参
     * @param cs 授权信息
     * @param method 请求接口类
     * @param type 请求类型
     * @return 执行结果
     */
    public static ApiResult exec(AlipayRequest alipayRequest, AlipayResponse _alipayResponse, AlipayObject alipayObject, ConfigConstants cs, String method, Integer type) {
        ApiResult apiResult = null;
        alipayRequest.setBizModel(alipayObject);
        //AlipayResponse alipayResponse=_alipayResponse;
        if (StringUtils.isNotBlank(cs.getReturn_url())) {
            alipayRequest.setReturnUrl(cs.getReturn_url());
        }
        if (StringUtils.isNotBlank(cs.getNotify_url())) {
            alipayRequest.setNotifyUrl(cs.getNotify_url());
        }
        try {
            switch (type) {
                case ConfigConstants.POST_TYPE:
                    _alipayResponse = alipayClient.pageExecute(alipayRequest);
                    apiResult = ApiResult.isSuccess(method, alipayRequest, _alipayResponse);
                    break;
                case ConfigConstants.GET_TYPE:
                    _alipayResponse = alipayClient.pageExecute(alipayRequest, "GET");
                    apiResult = ApiResult.isSuccess(method, alipayRequest, _alipayResponse);
                    break;
                case ConfigConstants.SDK_TYPE:
                    _alipayResponse = alipayClient.sdkExecute(alipayRequest);
                    apiResult = ApiResult.isSuccess(method, alipayRequest, _alipayResponse);
                    break;
                case ConfigConstants.DEFAULT_TYPE:
                    _alipayResponse = alipayClient.execute(alipayRequest, StringUtils.isEmpty(cs.getAuth_token()) ? null : cs.getAuth_token(), StringUtils.isEmpty(cs.getApp_auth_token()) ? null : cs.getApp_auth_token());
                    //_alipayResponse.getBody().replace("&", "&amp;");
                    if (_alipayResponse.isSuccess()) {
                        apiResult = ApiResult.isSuccess(method, alipayRequest, _alipayResponse);
                    } else {
                        apiResult = ApiResult.isFailure(method, alipayRequest, _alipayResponse);
                    }
                    break;
            }

        } catch (AlipayApiException e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            apiResult = ApiResult.isExecError(method, alipayRequest, sw.toString());

        }
        return apiResult;
    }

    /**
     *除PC、H5、APP外接口全部调用exec执行请求
     * @param method 接口实体类
     * @return 类型
     */
    private static int askType(String method) {
        if ("AlipayTradePagePayRequest".equals(method) || "AlipayTradeWapPayRequest".equals(method)) {
            return ConfigConstants.GET_TYPE;
        } else if ("AlipayTradeAppPayRequest".equals(method)) {
            return ConfigConstants.SDK_TYPE;
        } else {
            return ConfigConstants.DEFAULT_TYPE;
        }
    }

    private static AlipayClient alipayClient = new DefaultAlipayClient(ConfigConstants.GATE_WAY, ConfigConstants.APP_ID,
            ConfigConstants.RSA_PRIVATE_KEY, ConfigConstants.FORMAT_JSON, ConfigConstants.CHARSET_GBK, ConfigConstants.ALI_PUBLIC_KEY);

}
