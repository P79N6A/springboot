package com.example.demo.sign;

import com.alibaba.fastjson.JSON;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.*;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Signatures {

    public static void main(String[] args) throws Exception {
        /**支持全量Api调用：公共参数-ConfigConstants配置、声明对应接口、业务参数集-AlipayObject.【ps:需引用支付宝sdk及签名】**/
       /* //收单接口测试：alipay.trade.precreate(当面付)
        AlipayTradePrecreateModel alipayObject=new AlipayTradePrecreateModel();
        alipayObject.setSubject("pay test");null
        alipayObject.setTotalAmount("0.01");
        alipayObject.setOutTradeNo("2018022821001004740016474574");
        System.out.println(ExecuteHelper.exec(new AlipayTradePrecreateRequest(),new AlipayTradePrecreateResponse(),alipayObject,new ConfigConstants(),null,4).response);*/

        /**开放api：公共参数：实例化Signature，业务参数:自定义传入(json).【ps：自签名，不依赖支付宝sdk】**/
        Signatures signatures=new Signatures(UserConfig.APP_ID,UserConfig.RSA_PRIVATE_KEY);
        Map<String, String> bizParams = new ConcurrentHashMap<>();
        bizParams.put("out_trade_no",bulidOutTradeNo());
        bizParams.put("subject","功能的快感测试");
       bizParams.put("total_amount","0.01");
        UserConfig userConfig=new UserConfig();
//        userConfig.setCode("d139b3a81bc5449bb2e63be8af8bNA86");
//        userConfig.setGrant_type("authorization_code");
        userConfig.setMethod("alipay.trade.precreate");
        userConfig.setBiz_content(JSON.toJSONString(bizParams));
        //userConfig.setBiz_content("{\"request_id\":\"2018030315200000006\",\"card_type\":\"OUT_MEMBER_CARD\",\"biz_no_suffix_len\":\"10\",\"write_off_type\":\"barcode\",\"template_style_info\":{\"card_show_name\":\"度假客会员3\",\"logo_id\":\"_KxSz3iCRNuPkTa6rzrrsQAAACMAAQED\",\"background_id\":\"FSlnoc6VTbCgdhWycP_eQQAAACMAAQED\",\"bg_color\":\"rgb(22,112,179)\",\"front_text_list_enable\":false,\"front_image_enable\":false,\"feature_descriptions\":[\"使用度假客会员卡可享受会员折扣\"],\"slogan\":\"会员权益享不停\",\"brand_name\":\"度假客\"},\"template_benefit_info\":[{\"title\":\"消费即折扣\",\"benefit_desc\":[\"消费即折扣\"],\"start_date\":\"2018-03-01 00:00:00\",\"end_date\":\"2030-02-28 23:59:59\"}],\"column_info_list\":[{\"code\":\"BENEFIT_INFO\",\"operate_type\":\"openWeb\",\"title\":\"专享\",\"value\":\"80\",\"more_info\":{\"title\":\"会员专享权益\",\"url\":\"http://www.baidu.com\"}}],\"field_rule_list\":[{\"field_name\":\"Balance\",\"rule_name\":\"ASSIGN_FROM_REQUEST\",\"rule_value\":\"Balance\"}]}");
        System.out.println(signatures.buildParams(userConfig));
        //CountDownLatch cd=new CountDownLatch(1000);Executor ex= Executors.newFixedThreadPool(10);
        MapEncrypt en=new MapEncrypt();
        System.out.println(en.encrypt("hello*worlds"));
        System.out.println(en.decrypt(en.encrypt("hello*worlds")));
        System.out.println(en.getBase64("ILoveYou"));
        System.out.println(en.getFromBase64(en.getBase64("ILoveYou")));
        File file=new File("D:/tempAes/image.jpg");
        FileInputStream in=new FileInputStream(file);
        File file2=new File("D:/tempAes/aes.key");
        FileOutputStream out=new FileOutputStream(file2);
        en.generateKey();
        System.out.println(JSON.toJSONString(en.getKey()));
        en.encrypt(in,out);
        en.decrypt(in,out);

    }


    /**
     * 构造请求参数 biz/json
     *
     * @param userConfig 基本配置、业务参数
     * @return
     * @throws ApiException
     */
    public String buildParams(UserConfig userConfig) throws ApiException {
        Map<String, String> params = new HashMap<>();
        params.put("biz_content", userConfig.getBiz_content());
        params.put("method", userConfig.getMethod());
        params.put("version", this.version);
        params.put("app_id", this.app_id);
        params.put("sign_type", this.sign_type);
        params.put("notify_url", userConfig.getNotify_url());
        params.put("return_url", userConfig.getReturn_url());
        params.put("code", userConfig.getCode());
        params.put("grant_type", userConfig.getGrant_type());
        params.put("auth_token", userConfig.getAuth_token());
        params.put("app_auth_token", userConfig.getApp_auth_token());
        params.put("charset", this.charset);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        params.put("timestamp", df.format(new Date(Long.valueOf(System.currentTimeMillis()).longValue())));
        params.put("format", this.format);
        String signContent = sortSignatureContent(params);
        params.put("sign", sign(signContent, this.private_key, this.charset, this.sign_type));
        return getRequestUrl(params).toString();
    }

    /**
     * 获取请求链接，不执行请求
     *
     * @param params 请求参数：公共参数+业务参数
     * @return
     * @throws ApiException
     */
    private String getRequestUrl(Map<String, String> params) throws ApiException {
        StringBuffer urlSb = new StringBuffer(this.server_url);
        String data;
        try {
            if (params != null && !params.isEmpty()) {
                StringBuilder query = new StringBuilder();
                Set entries = params.entrySet();
                boolean hasParam = false;
                Iterator var = entries.iterator();
                while (var.hasNext()) {
                    Map.Entry entry = (Map.Entry) var.next();
                    String name = (String) entry.getKey();
                    String value = (String) entry.getValue();
                    if (isNotEmpty(new String[]{name, value})) {
                        if (hasParam) {
                            query.append("&");
                        } else {
                            hasParam = true;
                        }
                        query.append(name).append("=").append(URLEncoder.encode(value, this.charset));
                    }
                }

                data = query.toString();
            } else {
                data = null;
            }
            urlSb.append("?");
            urlSb.append(data);
        } catch (IOException var5) {
            throw new ApiException(var5);
        }
        return urlSb.toString();
    }

    /**
     * 排序、拼接
     *
     * @param params 待签名字符
     * @return string
     */
    public static String sortSignatureContent(Map<String, String> params) {
        TreeMap sortedParams = new TreeMap();
        if (params != null && params.size() > 0) {
            sortedParams.putAll(params);
        }

        StringBuffer content = new StringBuffer();
        ArrayList keys = new ArrayList(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;

        for (int i = 0; i < keys.size(); ++i) {
            String key = (String) keys.get(i);
            String value = (String) sortedParams.get(key);
            if (isNotEmpty(new String[]{key, value})) {
                content.append((index == 0 ? "" : "&") + key + "=" + value);
                ++index;
            }
        }

        return content.toString();
    }

    /**
     * 签名
     *
     * @param content 待签名参数
     * @param privateKey 账号私钥
     * @param charset 字符编码
     * @param signType 签名算法类型
     * @return
     * @throws ApiException
     */
    public static String sign(String content, String privateKey, String charset, String signType) throws ApiException {
        try {
            InputStream keyBytes = new ByteArrayInputStream(privateKey.getBytes());
            PrivateKey priKey;
            if (keyBytes != null) {
                priKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decodeBase64(read(new ByteArrayInputStream(privateKey.getBytes())).getBytes())));
            } else {
                priKey = null;
            }
            Signature signature;
            if ("RSA".equals(signType) || null == signType) {
                signature = Signature.getInstance("SHA1WithRSA");
            } else if ("RSA2".equals(signType)) {
                signature = Signature.getInstance("SHA256WithRSA");
            } else {
                throw new ApiException("Sign Type is Not Support : signType=" + signType);
            }

            signature.initSign(priKey);
            if (isBlank(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64(signed));
        } catch (InvalidKeySpecException e1) {
            throw new ApiException("rsa private key format not a PKCS8", e1);
        } catch (Exception e2) {
            throw new ApiException("content = " + content + "; charset = " + charset, e2);
        }
    }

    /**
     * @param str 字符
     * @return
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotEmpty(String... values) {
        boolean result = true;
        if (values != null && values.length != 0) {
            String[] var2 = values;
            int var3 = values.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                String value = var2[var4];
                result &= !isBlank(value);
            }
        } else {
            result = false;
        }

        return result;
    }

    /**
     * @param in byte
     * @return
     * @throws IOException
     */
    public static String read(InputStream in) throws IOException {
        Reader reader = new InputStreamReader(in);
        StringWriter writer = new StringWriter();
        char[] buffer = new char[4096];
        int amount;
        while ((amount = reader.read(buffer)) >= 0) {
            writer.write(buffer, 0, amount);
        }
        return writer.toString();
    }

    private  static String bulidOutTradeNo() {
        String result = new Random().nextInt(100) + "";
        if (result.length() == 1) {
            result = "0" + result;
        }
        StringBuffer outBiz =new StringBuffer(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        return outBiz.append("21001004").append(result).append("00").append(new SimpleDateFormat("HHmmss").format(new Date())).append(result).toString();
    }
    /**
     * 请求网关
     **/
    private String server_url;
    /**
     * 应用id
     **/
    private String app_id;
    /**
     * 账户私钥
     **/
    private String private_key;
    /**
     * 入参格式、json/xml
     **/
    private String format;
    /**
     * 接口版本号，常规固定为1.0
     **/
    private String version;
    /**
     * 请求字符编码
     **/
    private String charset;
    /**
     * 支付宝公钥，用于回显数据签名校验
     **/
    private String alipay_public_key;
    /**
     * 签名算法类型-rsa/rsa2
     **/
    private String sign_type;

    public Signatures(String appId, String privateKey) {
        this.server_url = "https://openapi.alipay.com/gateway.do";
        this.app_id = appId;
        this.private_key = privateKey;
        this.format = "json";
        this.version = "1.0";
        this.charset = "utf-8";
        this.alipay_public_key = null;
        this.sign_type = "RSA";

    }

    public Signatures(String appId, String privateKey,String sign_type) {
        this(appId, privateKey);
        this.sign_type = sign_type;
    }

    public Signatures(String serverUrl, String appId, String privateKey,  String sign_type) {
        this(appId, privateKey, sign_type);
        this.server_url = serverUrl;
    }

    public Signatures(String serverUrl, String appId, String privateKey,  String sign_type, String alipayPublicKey) {
        this(serverUrl, appId, privateKey, sign_type);
        this.alipay_public_key = alipayPublicKey;
    }

    public Signatures(String serverUrl, String appId, String privateKey, String sign_type, String format, String version, String charset) {
        this(serverUrl, appId, privateKey, sign_type);
        this.format = format;
        this.version = version;
        this.charset = charset;
    }


    public Signatures(String serverUrl, String appId, String privateKey, String sign_type,  String format, String version, String charset, String alipayPublicKey) {
        this(serverUrl, appId, privateKey,sign_type,format, version, charset);
        this.alipay_public_key = alipayPublicKey;

    }
}
