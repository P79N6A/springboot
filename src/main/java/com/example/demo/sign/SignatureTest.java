package com.example.demo.sign;
import com.example.demo.common.ConfigConstants;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class SignatureTest {
    public static void  main(String[] args)throws Exception{
        String content="a=123";//1.传参（appid、token、bizconent）2、构造（植入1/3map,判断biz是否需二次加密，排序制符）3，签名（获取sign）4.获取请求链接/后台执行请求
        //import com.alipay.api.internal.util.StreamUtil;
        //import com.alipay.api.internal.util.codec.Base64;
//        com.sun.org.apache.xml.internal.security.utils.Base64
//        org.apache.tomcat.util.codec.binary.Base64.decodeBase64()
//uKD3hLCxI0cmrtKg9bzYj81/DLP35SgHGgVRNJauTt8l21ziFutiqWs+bliRDlnnMYnJXtd6UQ4fvuYLO+jBvG8MX+BDSp9oGP06zK06fwjXT26Y+3TBnp7M6E7seH7W2AsuKRVfXmOjLSbnp0/E8eaVSvWi11hSgZaeo8qzr4c=
//uKD3hLCxI0cmrtKg9bzYj81/DLP35SgHGgVRNJauTt8l21ziFutiqWs+bliRDlnnMYnJXtd6UQ4fvuYLO+jBvG8MX+BDSp9oGP06zK06fwjXT26Y+3TBnp7M6E7seH7W2AsuKRVfXmOjLSbnp0/E8eaVSvWi11hSgZaeo8qzr4c=
        System.out.println(sign(content,ConfigConstants.RSA_PRIVATE_KEY,ConfigConstants.CHARSET_GBK,"RSA"));
    }

    public static String sign(String content, String privateKey, String charset,String signType) throws ApiException {
        try {
            InputStream keyBytes=new ByteArrayInputStream(privateKey.getBytes());
            PrivateKey priKey;
            if (keyBytes != null) {
               priKey=KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decodeBase64(read(new ByteArrayInputStream(privateKey.getBytes())).getBytes())));
            } else {
                priKey= null;
            }
            Signature signature;
            if("RSA".equals(signType)||null==signType){
                signature = Signature.getInstance("SHA1WithRSA");
            }else if("RSA2".equals(signType)){
               signature = Signature.getInstance("SHA256WithRSA");
            }else{
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


    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static String read(InputStream in) throws IOException {
        Reader reader=new InputStreamReader(in);
        StringWriter writer = new StringWriter();
        char[] buffer = new char[4096];
        int amount;
        while((amount = reader.read(buffer)) >= 0) {
            writer.write(buffer, 0, amount);
        }
        return writer.toString();
    }
}
