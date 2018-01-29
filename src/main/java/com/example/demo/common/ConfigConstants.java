package com.example.demo.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by wb-lwc235565 on 2018/1/26.
 */
public class ConfigConstants {
    /**请求网关**/
    public static final String GATE_WAY                       = "https://openapi.alipay.com/gateway.do";
    /**应用id**/
    public static final String APP_ID                         = "2016092201950845";
    /**应用私钥**/
    public static final String RSA_PRIVATE_KEY                = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANSe1tYHjTEBfgfl8I2b8F/hlUeBNUaNnVseEEwTISZlYCwjNl0X48W54y5zaZRpbLWPVVH6WYvMYH0DGZuBUJUXfwV4KN2iMSMR+/PcGoQaEH+3h6fEi8g+8G2Y6oxkqLINdeqyCDsjO6aiGjZdZVQVy6OGy6xINZOKq5ecFaitAgMBAAECgYByLeJ9CY0e9ggyQZ8OzOEm/ENoJNDxVHdeSSTDVbqFngcpbLdzArNEqXCAr2XRV1QTpCdTYLfZxSVDvPhxc95LV0cmXQJQgGIH7cFFJUDp8vlwCWnkQAV+yqnevTY9jXwMMhZFUilEyAlwSqZ2aiFlimcwFDXD5l2hRfQdCSq9wQJBAPgkSO1fACqx3gdKDH386+JTzfpLYl+h/z/UYXTnSEC/3nO1kRlLuJw0qcXCTRRanWgWGvxbNieBRPhxIYTBJRUCQQDbWpQ+Qu/DVOdiPFXa+udQzsFmsLcWQSABrgLdoyR09XxP36Hh1d/msWKjLdhvO0boga5XN5e/QEouRiLWqos5AkEAtQib3/nQQFXV21GNvZj5avyjKLk4wvaIJ0RF+akG0J5qp9ZOTrssq2HMfofb/j6B2j9OXtAYuUeZTvwSbS0QZQJAFOwX1bR2wA/aHhGZMtDZvWhrJAtY+0Ns9RwO4+sKsCk2GTxAaZUHzS5ANUZLLZje05CC+4iu7awJJ07DRexwaQJAO+nQVYpwAXKGr+IYk+X3xBEh4pStipSJRMFvK2DyQB/oWl22sA9PsFRbO4+POXbTQzhmodhklOErgn7ekKkQGw==";
    /**支付宝公钥 **/
    public static final String ALI_PUBLIC_KEY                 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
    /** UTF-8字符集 **/
    public static final String CHARSET_UTF8                   = "UTF-8";
    /** GBK字符集 **/
    public static final String CHARSET_GBK                    = "GBK";
    /** JSON 应格式 */
    public static final String FORMAT_JSON                    = "json";
    /** XML 应格式 */
    public static final String FORMAT_XML                     = "xml";
    /**签名算法请求类型**/
    public static final String SIGN_TYPE_RSA                  = "RSA";
    public static final String SIGN_TYPE_RSA2                 = "RSA2";
    /**用户授权token**/
    public static final String ACCESS_TOKEN                   = "auth_token";
    /**应用授权token**/
    public static final String APP_AUTH_TOKEN                 = "app_auth_token";
    /**异步通知地址**/
    public static final String NOTIFY_URL                     = "notify_url";
    /**同步通知地址**/
    public static final String RETURN_URL                     = "return_url";
    /**date format**/
    public static final String DATE_TIME_FORMAT               = "yyyy-MM-dd HH:mm:ss";
    /**method**/
    public static final String METHOD                         = "method";
    /**request path**/
    public static final String REQUEST_PATH                   = "com.alipay.api.request.";
    /**response path**/
    public static final String RESPONSE_PATH                  = "com.alipay.api.response.";
    /**model path**/
    public static final String MODEL_PATH                     = "com.alipay.api.domain.";
    /**model**/
    public static final String MODEL                          = "Model";
    /**req**/
    public static final String REQUEST                        = "Request";
    /**resp**/
    public static final String RESPONSE                       = "Response";
    public static final int POST_TYPE = 1;
    public static final int GET_TYPE = 2;
    public static final int SDK_TYPE = 3;
    public static final int DEFAULT_TYPE = 4;
    /**用户授权令牌**/
    public String auth_token;
    /**应用授权令牌**/
    public String app_auth_token;
    /**同步地址*/
    public String return_url;
    /**异步地址*/
    public String notify_url;
    public static String getGateWay() {
        return GATE_WAY;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getApp_auth_token() {
        return app_auth_token;
    }

    public void setApp_auth_token(String app_auth_token) {
        this.app_auth_token = app_auth_token;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }


    public static String outBiz() {
        String result = new Random().nextInt(100) + "";
        if (result.length() == 1) {
            result = "0" + result;
        }
        StringBuffer outBiz =new StringBuffer(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        outBiz.append("21001004").append(result).append("00").append(new SimpleDateFormat("HHmmss").format(new Date())).append(result);

        return outBiz.toString();
    }

}
