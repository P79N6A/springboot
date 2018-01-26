
package com.example.demo.common;

import com.alipay.api.*;
import org.apache.commons.lang.StringUtils;


/**
 * Created by wb-lwc235565 on 2018/1/26.
 */

public class ExecuteHelper {

 private static AlipayClient alipayClient = new DefaultAlipayClient(ConfigConstants.GATE_WAY,ConfigConstants.APP_ID,
         ConfigConstants.RSA_PRIVATE_KEY,ConfigConstants.FORMAT_JSON,ConfigConstants.CHARSET_GBK, ConfigConstants.ALI_PUBLIC_KEY);



  /*  public ResultBean _ask(AlipayRequest alipayRequest,AlipayResponse alipayResponse,AlipayObject alipayObject,String jsonObject,ConfigConstants cs,Integer type) {
        String body;
        ResultBean resultBean = null;
        if(StringUtils.isEmpty(jsonObject)&&alipayObject!=null){
            alipayRequest.setBizModel(jsonObject.);
        }else{

        }
        if (StringUtils.isNotBlank(cs.getReturn_url())) {
            alipayRequest.setReturnUrl(cs.getReturn_url());
        }
        if (StringUtils.isNotBlank(cs.getNotify_url())) {
            alipayRequest.setNotifyUrl(cs.getNotify_url());
        }
        try {
            switch (type) {
                case POST_TYPE:
                    body = alipayClient.pageExecute(alipayRequest).getBody();
                    resultBean = ResultBean.isSuccess(body);
                    break;
                case GET_TYPE:
                    body = alipayClient.pageExecute(alipayRequest, "GET").getBody();
                    resultBean = ResultBean.isSuccess(body);
                    break;
                case SDK_TYPE:
                    body = alipayClient.sdkExecute(alipayRequest).getBody();
                    resultBean = ResultBean.isSuccess(body);
                    break;
                case DEFAULT_TYPE:
                    AlipayResponse response = null;
                    //判定是否进行了对应的授权
                    if (StringUtils.isNotBlank(alipayConfig.getApp_auth_token())) {
                        response = alipayClient.execute(alipayRequest, null, alipayConfig.getApp_auth_token());
                    } else {
                        response = alipayClient.execute(alipayRequest);
                    }
                    if (response.isSuccess()) {
                        resultBean = ResultBean.isSuccess(response, alipayClient.pageExecute(alipayRequest, "GET").getBody().replace("&", "&amp;"));
                    } else {
                        resultBean = ResultBean.isSuccess(response);
                    }
                    //不设置为字符串 设置对象
                    //if (alipayClient.execute(alipayRequest).isSuccess())

                    //else {
                    //    body = response.getBody();
                    //    resultBean = ResultBean.isFailure(body);
                    //}
                    break;
            }

        } catch (AlipayApiException e) {

         StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            body = sw.toString();
            resultBean = ResultBean.isThrowsExcetption(body);


        }
        return resultBean;
    }*/


}
