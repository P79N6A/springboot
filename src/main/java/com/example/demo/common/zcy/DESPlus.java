
package com.example.demo.common.zcy;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.MessageDigest;
import java.security.Security;


/**
 * 描述:数据加密,解密。采用DES对称算法
 * 注意事项: 1、加密密钥为：strDefaultKey
 *         2、如果加密文件的长度与原文件的长度有关，即密文文件的长度受到原文文件的长度影响。
 *         3、
 * @author lxm
 * @modefy date 2011-5-17 上午11:28:13
 */
public class DESPlus {
    private static String strDefaultKey = "zjzfcg.gov.cn"; //默认的加密密钥。
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;
    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     *
     * @param arrB
     *            需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception
     *             本方法不处理任何异常，所有异常全部抛出
     */
    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn
     *            需要转换的字符串
     * @return 转换后的byte数组
     * @throws Exception
     *             本方法不处理任何异常，所有异常全部抛出
     * @author <a href="mailto:leo841001@163.com">LiGuoQing</a>
     */
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }



    /**
     * 默认构造方法，使用默认密钥
     *
     * @throws Exception
     */
    public DESPlus() throws Exception {
        this(strDefaultKey);
    }

    /**
     * 指定密钥构造方法
     *
     * @param strKey
     *            指定的密钥
     * @throws Exception
     */
    public DESPlus(String strKey) throws Exception {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key key = getKey(strKey.getBytes());

        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);

        decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    /**
     * 加密字节数组
     *
     * @param arrB
     *            需加密的字节数组
     * @return 加密后的字节数组
     * @throws Exception
     */
    public byte[] encrypt(byte[] arrB) throws Exception {
        return encryptCipher.doFinal(arrB);
    }

    /**
     * 加密字符串
     *
     * @param strIn
     *            需加密的字符串
     * @return 加密后的字符串
     * @throws Exception
     */
    public String encrypt(String strIn) throws Exception {
        return byteArr2HexStr(encrypt(strIn.getBytes()));
    }

    /**
     * 解密字节数组
     *
     * @param arrB
     *            需解密的字节数组
     * @return 解密后的字节数组
     * @throws Exception
     */
    public byte[] decrypt(byte[] arrB) throws Exception {
        return decryptCipher.doFinal(arrB);
    }

    /**
     * 解密字符串
     *
     * @param strIn
     *            需解密的字符串
     * @return 解密后的字符串
     * @throws Exception
     */
    public String decrypt(String strIn) throws Exception {
        return new String(decrypt(hexStr2ByteArr(strIn)));
    }

    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
     *
     * @param arrBTmp
     *            构成该字符串的字节数组
     * @return 生成的密钥
     * @throws java.lang.Exception
     */
    private Key getKey(byte[] arrBTmp) throws Exception {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];

        // 将原始字节数组转换为8位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }

        // 生成密钥
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

        return key;
    }

    public static final String SPLIT = "##";// 分隔符
    //md5
    private static String MD5(String inStr) {
        MessageDigest md5 = null;
        String hexValue = "";
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(inStr.getBytes("UTF-8"));
            StringBuffer hex = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                hex.append(Integer.toHexString(((int) bytes[i]) & 0xff));
            }
            hexValue = hex.toString();
            hex = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            md5 = null;
        }
        return hexValue;

    }


    /**
     * MD5加密信息，ECP登录参数生成用请勿修改
     * @param gpAccountId 采购网账号id
     * @param hgtAccount  政采通帐号
     * @param ticketKey   serial_key
     * @return
     */
    public static String encrypt3(String gpAccountId, String hgtAccount,String ticketKey) {
        return MD5(gpAccountId + hgtAccount+ticketKey);
    }

    /**
     * ECP登录时判断是否有效的登录信息，请勿修改
     * @param tokenkey     传递的token，也就是参数pf
     * @param gpAccountId  采购网账号id
     * @param hgtAccount   政采通帐号
     * @param key          serial_key
     * @param around       令牌有效浮动时间（秒）
     * @param dESPlus
     * @return
     */
    public static boolean valid(String tokenkey, String gpAccountId,
                                String hgtAccount, String key, int around,DESPlus dESPlus) {
        if (tokenkey != null && hgtAccount != null) {
            String local_v = encrypt3(gpAccountId, hgtAccount,key);
            try {
                String desToken = dESPlus.decrypt(tokenkey);
                String[] str = desToken.split(SPLIT);
                if (str.length == 3) {
                    if (local_v.equals(str[0])) {
                        long currentTimeMillis = System.currentTimeMillis();
                        long tokenTime = Long.valueOf(str[2]).longValue();
                        if ( currentTimeMillis >= tokenTime && currentTimeMillis < tokenTime+around*1000 ) {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
//              e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static void main(String[] arg) {
        try {
            DESPlus dESPlus = new DESPlus();

//            String md5 = dESPlus.encrypt3("B63E23F00D5B25D0E04468B59992248C", "6602415","B63E23F00D5C25D0E04468B59992248C");//
//            System.out.println("md5="+md5);
//            String token = (md5+ SPLIT+"B63E23F00D5B25D0E04468B59992248C" + SPLIT + System.currentTimeMillis());
//            //对数据加密
//            System.out.println("加密前deCode:"+token);
//            String enCode= dESPlus.encrypt(token);
//            System.out.println("加密后enCode:"+enCode);
//            //对加密数据进行解密
//            String deCode = dESPlus.decrypt(enCode);
//            System.out.println("解密后decode:"+  deCode);
//            //对加密数据进行解密
//            File f=new File("F:/out.txt");
//            FileOutputStream fileOutputStream = new FileOutputStream(f);
//            PrintStream printStream = new PrintStream(fileOutputStream);
//            System.setOut(printStream);
            String[] arg1={"2452b99abbc09a745416310e08dc04b2",
                    "d324fb79c59415155416310e08dc04b2",
                    "17b45d51e388e4f25416310e08dc04b2",
                    "94aae3bf328f64c7c5a85a6c72519329a157e19482b30d6f"};
            for(int i=0;i<arg1.length;i++){
                String deCode1 = new String(dESPlus.decrypt(arg1[i]).getBytes("gbk"),"utf-8");
                System.out.println("加密前deCode1:"+arg1[i]+";解密后decode1:"+  deCode1);
            }
            //判断有效性
           // System.out.println("flag:"+valid(enCode,"B63E23F00D5B25D0E04468B59992248C", "6602415","B63E23F00D5C25D0E04468B59992248C", 300, dESPlus));

        } catch (Exception e) {
            e.printStackTrace();
        }
    } 

}

