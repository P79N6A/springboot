package com.example.demo.sign;

/**
 * Created by wb-lwc235565 on 2018/3/6.
 */
public class UserConfig {
    public static final String GATE_WAY = "https://openapi.alipay.com/gateway.do";
    /**
     * 应用id
     **/
    public static final String APP_ID = "2016092201950845";
    /**
     * 应用私钥
     **/
    public static final String RSA_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANSe1tYHjTEBfgfl8I2b8F/hlUeBNUaNnVseEEwTISZlYCwjNl0X48W54y5zaZRpbLWPVVH6WYvMYH0DGZuBUJUXfwV4KN2iMSMR+/PcGoQaEH+3h6fEi8g+8G2Y6oxkqLINdeqyCDsjO6aiGjZdZVQVy6OGy6xINZOKq5ecFaitAgMBAAECgYByLeJ9CY0e9ggyQZ8OzOEm/ENoJNDxVHdeSSTDVbqFngcpbLdzArNEqXCAr2XRV1QTpCdTYLfZxSVDvPhxc95LV0cmXQJQgGIH7cFFJUDp8vlwCWnkQAV+yqnevTY9jXwMMhZFUilEyAlwSqZ2aiFlimcwFDXD5l2hRfQdCSq9wQJBAPgkSO1fACqx3gdKDH386+JTzfpLYl+h/z/UYXTnSEC/3nO1kRlLuJw0qcXCTRRanWgWGvxbNieBRPhxIYTBJRUCQQDbWpQ+Qu/DVOdiPFXa+udQzsFmsLcWQSABrgLdoyR09XxP36Hh1d/msWKjLdhvO0boga5XN5e/QEouRiLWqos5AkEAtQib3/nQQFXV21GNvZj5avyjKLk4wvaIJ0RF+akG0J5qp9ZOTrssq2HMfofb/j6B2j9OXtAYuUeZTvwSbS0QZQJAFOwX1bR2wA/aHhGZMtDZvWhrJAtY+0Ns9RwO4+sKsCk2GTxAaZUHzS5ANUZLLZje05CC+4iu7awJJ07DRexwaQJAO+nQVYpwAXKGr+IYk+X3xBEh4pStipSJRMFvK2DyQB/oWl22sA9PsFRbO4+POXbTQzhmodhklOErgn7ekKkQGw==";
    /**
     * 支付宝公钥
     **/
    public static final String ALI_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
    /**
     * UTF-8字符集
     **/
    public static final String CHARSET_UTF8 = "UTF-8";
    /**
     * GBK字符集
     **/
    public static final String CHARSET_GBK = "GBK";
    /**
     * JSON 应格式
     */
    public static final String FORMAT_JSON = "json";
    /**
     * XML 应格式
     */
    public static final String FORMAT_XML = "xml";
    /**
     * 签名算法请求类型
     **/
    public static final String SIGN_TYPE_RSA = "RSA";
    public static final String SIGN_TYPE_RSA2 = "RSA2";
    /**
     * 用户授权code
     **/
    public String code;
    /**
     * 应用授权类型
     **/
    public String grant_type;
    /**
     * 用户授权令牌
     **/
    public String auth_token;
    /**
     * 应用授权令牌
     **/
    public String app_auth_token;
    /**
     * 同步地址
     */
    public String return_url;
    /**
     * 异步地址
     */
    public String notify_url;
    /**
     * 业务请求参数
     **/
    public String biz_content;
    /**
     * 请求接口名称
     **/
    private String method;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBiz_content() {
        return biz_content;
    }

    public void setBiz_content(String biz_content) {
        this.biz_content = biz_content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
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

}
