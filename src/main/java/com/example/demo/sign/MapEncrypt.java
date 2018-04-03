package com.example.demo.sign;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wb-lwc235565 on 2018/3/28.
 */
public class MapEncrypt {
    static Map<Character,Character> fisrt=new HashMap<>();
    static Map<Character,Character> second=new HashMap<>();

    MapEncrypt(){
        String a="abcdefghijklmnopqrstuvwsvz*";
        String b="veknohzf*iljxdmygbrcswqupta";
        for (int i = 0; i <a.length() ; i++) {
            fisrt.put(a.charAt(i),b.charAt(i));
            second.put(b.charAt(i),a.charAt(i));
        }
    }
    //map加密
    public static  String encrypt(String str){
        String strs=str.toLowerCase();
        String e="";
        for (int i = 0; i <strs.length() ; i++) {
            e=e+fisrt.get(strs.charAt(i));
        }
        return  e;
    }
    //map解密
    public  static  String decrypt(String str){
        String d="";
        for (int i = 0; i <str.length() ; i++) {
            d=d+second.get(str.charAt(i));
        }
        return  d;
    }
    //加密
    public String getBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }
    //解密
    public String getFromBase64(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private Key key;

    /**
     * 生成AES对称秘钥
     * @throws NoSuchAlgorithmException
     */
    public void generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecureRandom random = new SecureRandom();
        keygen.init(random);
        this.key = keygen.generateKey();
    }


    /**
     * 加密
     * @param in
     * @param out
     * @throws InvalidKeyException
     * @throws ShortBufferException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IOException
     */
    public void encrypt(InputStream in, OutputStream out) throws InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, IOException {
        this.crypt(in, out, Cipher.ENCRYPT_MODE);
    }

    /**
     * 解密
     * @param in
     * @param out
     * @throws InvalidKeyException
     * @throws ShortBufferException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IOException
     */
    public void decrypt(InputStream in, OutputStream out) throws InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, IOException {
        this.crypt(in, out, Cipher.DECRYPT_MODE);
    }

    /**
     * 实际的加密解密过程
     * @param in
     * @param out
     * @param mode
     * @throws IOException
     * @throws ShortBufferException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    public void crypt(InputStream in, OutputStream out, int mode) throws IOException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(mode, this.key);

        int blockSize = cipher.getBlockSize();
        int outputSize = cipher.getOutputSize(blockSize);
        byte[] inBytes = new byte[blockSize];
        byte[] outBytes = new byte[outputSize];

        int inLength = 0;
        boolean more = true;
        while (more) {
            inLength = in.read(inBytes);
            if (inLength == blockSize) {
                int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
                out.write(outBytes, 0, outLength);
            } else {
                more = false;
            }
        }
        if (inLength > 0)
            outBytes = cipher.doFinal(inBytes, 0, inLength);
        else
            outBytes = cipher.doFinal();
        out.write(outBytes);
        out.flush();
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Key getKey() {
        return key;
    }
}
