package com.example.demo.common.zcy;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.RequestUtil;
import com.example.demo.model.WorkDuty;
import com.example.demo.model.purchase.response.DateResponse;
import com.example.demo.model.purchase.response.UserAuthResponse;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args)throws Exception {
        // String result=sendPost("http://ipaas.cai-inc.com/api/auth/login","userName=wb-luweicheng&psw=lwc@123.com");
//        RestClient restClient=new RestClient();
//        HashMap<String,String> headermap = new HashMap<String,String>();
//        headermap.put("Content-Type", "application/json;charset=utf-8");

        // jsonObject.put("token","eyJhbGciOiJIUzUxMiJ9.eyJyZWFsTmFtZSI6IumZhuS8n-eoiyIsIm1haWwiOiJ3Yi1sdXdlaWNoZW5nQGNhaS1pbmMuY29tIiwibmlja05hbWUiOiLlvq7ovrAiLCJtb2JpbGUiOiIxODM1NzE3MDkzOSIsImVtcGxveWVlSWQiOiJQMDAwNiIsImlkIjo0LCJhdmF0YXIiOiJodHRwczovL3N0YXRpYy5kaW5ndGFsay5jb20vbWVkaWEvbEFEUEJiQ2MxWm1UeG96TkFVak5BUmdfMjgwXzMyOC5qcGciLCJ1c2VyTmFtZSI6IndiLWx1d2VpY2hlbmciLCJleHAiOjE1NDYwNDgyMzB9.jvRn-iB8icEok3vkG2KZJXl5tL3acNcIikH-QjvyUlhAHJMJdTJcOxdTvVtZzDgyqEvsTuyh0l5nLicZWJXIGQ");
//        String userJsonString=JSON.toJSONString(jsonObject);
//        CloseableHttpResponse closeableHttpResponse= restClient.post("http://ipaas.cai-inc.com/api/auth/login", userJsonString, headermap);
//        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
//        System.out.println(statusCode);
//        System.out.println(JSON.toJSON(closeableHttpResponse));
        // interfaceUtil("http://ipaas.cai-inc.com/api/auth/login","");
        //{"success":false,"code":"auth.login.error","msg":"域账号验证失败，请重新输入","data":null}
        //{"success":true,"code":"","msg":"","data":{"token":"eyJhbGciOiJIUzUxMiJ9.eyJyZWFsTmFtZSI6IumZhuS8n-eoiyIsIm1haWwiOiJ3Yi1sdXdlaWNoZW5nQGNhaS1pbmMuY29tIiwibmlja05hbWUiOiLlvq7ovrAiLCJtb2JpbGUiOiIxODM1NzE3MDkzOSIsImVtcGxveWVlSWQiOiJQMDAwNiIsImlkIjo0LCJhdmF0YXIiOiJodHRwczovL3N0YXRpYy5kaW5ndGFsay5jb20vbWVkaWEvbEFEUEJiQ2MxWm1UeG96TkFVak5BUmdfMjgwXzMyOC5qcGciLCJ1c2VyTmFtZSI6IndiLWx1d2VpY2hlbmciLCJleHAiOjE1NDYwNDgyMzB9.jvRn-iB8icEok3vkG2KZJXl5tL3acNcIikH-QjvyUlhAHJMJdTJcOxdTvVtZzDgyqEvsTuyh0l5nLicZWJXIGQ","userInfo":{"id":4,"createAt":"2018-11-29T09:50:30.451+0000","updateAt":"2018-11-29T09:50:30.451+0000","userName":"wb-luweicheng","nickName":"微辰","employeeId":"P0006","realName":"陆伟程","avatar":"https://static.dingtalk.com/media/lADPBbCc1ZmTxozNAUjNARg_280_328.jpg","mail":"wb-luweicheng@cai-inc.com","mobile":"18357170939","departments":[{"id":5,"createAt":"2018-11-29T09:50:30.475+0000","updateAt":"2018-11-29T09:50:30.475+0000","name":"技术支持","pid":2,"path":"政采云有限公司,技术研发部,技术支持"}]}}}
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName","wb-luweicheng");
        jsonObject.put("psw","Lianshang@052611");
        String userResp=RequestUtil.authRequest(jsonObject.toString(),Config.USER_AUTH_LOGIN);
        System.out.println(userResp);
//        if(RequestUtil.isSuccess(userResp)){
//            UserAuthResponse userAuthResponse=JSON.parseObject(RequestUtil.getPosition(userResp),UserAuthResponse.class);
//            System.out.println(JSON.toJSON(userAuthResponse));
//        }else{
//            System.out.println("net exception");
//        }
//        int year = 2019;
//        int month = 0;//月份从0开始,10代表11月份
//        Calendar calendar = new GregorianCalendar(year, month, 1);
//        int i = 1;
//        while (calendar.get(Calendar.YEAR) < year + 1) {
//            calendar.set(Calendar.WEEK_OF_YEAR, i++);
//            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//            if (calendar.get(Calendar.MONTH) == month) {
//                System.out.printf("星期天：%tF%n", calendar);
//            }
//            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
//            if (calendar.get(Calendar.MONTH) == month) {
//                System.out.printf("星期六：%tF%n", calendar);
//            }
//        }


//        Calendar calendars = Calendar.getInstance();
//        calendars.setTime(new Date());
//        //第几周
//        int week = calendars.get(Calendar.WEEK_OF_MONTH);
//        //第几天，从周日开始
//        int day = calendars.get(Calendar.DAY_OF_WEEK);
//        System.out.println(week + "=" + day);

//        while(true){
//            Scanner scanner=new Scanner(System.in);
//            String inline=scanner.nextLine();
//            String s=inline.replaceAll("？","!")
//                            .replaceAll("你","我")
//                            .replaceAll("吗","");
//            System.out.println(s);
//        }
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
//        SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMdd");
//        System.out.println(sdf.format(new Date()));


        String str="{\n" +
                "        \"supplierOrgId\":\"12345\",\n" +
                "        \"purchaserOrgId\": \"123\",\n" +
                "        \"purchaserOrgName\": \"资产云\",\n" +
                "        \"districtCode\": \"310000\",\n" +
                "        \"purchaserName\": \"张三\",\n" +
                "        \"purchaserPhone\": \"18912121890\",\n" +
                "        \"orderNumber\": \"00000123437\",\n" +
                "        \"salesman\": \"李四\",\n" +
                "        \"logisticsCompanyName\": \"顺丰\",\n" +
                "        \"shipFee\": 100,\n" +
                "        \"logisticsCompanyCode\": \"SF\",\n" +
                "        \"remark\": \"加急\",\n" +
                "         \"itemList\": [{\n" +
                "             \"itemCode\": \"111\",\n" +
                "             \"itemSizeCode\": \"L\",\n" +
                "             \"itemQuantity\": 10,\n" +
                "             \"itemStatus\": 0,\n" +
                "             \"itemRemark\": \"常规\"\n" +
                "         }],\n" +
                "         \"invoiceList\": [{\n" +
                "             \"billType\": 1,\n" +
                "             \"invoiceBillingMode\": 1,\n" +
                "             \"invoiceType\": 1,\n" +
                "             \"invoiceTitle\": \"资产云\",\n" +
                "             \"invoiceContent\": \"abc\",\n" +
                "             \"taxId\": \"122121779797\",\n" +
                "             \"depositBank\": \"建设银行\",\n" +
                "             \"bankAccount\": \"123456789\",\n" +
                "             \"address\": \"杭州市西湖区\",\n" +
                "             \"phone\": \"18112121891\",\n" +
                "             \"invoiceAmount\": 800\n" +
                "         }],\n" +
                "        \"receiver\": \"阿三\",\n" +
                "        \"receiverPhone\": \"0571-82018197\",\n" +
                "        \"receiverMobile\": \"13512121891\",\n" +
                "        \"receiverZip\": \"310000\",\n" +
                "        \"receiverCity\": \"浙江省\",\n" +
                "        \"receiverDetail\": \"西湖区\"\n" +
                "     }";
        org.json.JSONObject jsonObject1=new org.json.JSONObject(str);
        System.out.println(jsonObject1);
        String result=RequestUtil.execRequest("http://sandbox.zcy.gov.cn",Config.APP_KEY,Config.SECRET,jsonObject1.toString(),"/fiscal/zcy.purchase.requirement.add");
        System.out.println(result);


    }
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static void interfaceUtil(String path,String data) {
        try {
            URL url = new URL(path);
            //打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            PrintWriter out = null;
            //请求方式
//          conn.setRequestMethod("POST");
//           //设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            //设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            //最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
            //post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //发送请求参数即数据
            out.print(data);
            //缓冲数据
            out.flush();
            //获取URLConnection对象对应的输入流
            InputStream is = conn.getInputStream();
            //构造一个字符流缓存
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            while ((str = br.readLine()) != null) {
                System.out.println(str);
            }
            //关闭流
            is.close();
            //断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
            //固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些。
            conn.disconnect();
            System.out.println("完整结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
