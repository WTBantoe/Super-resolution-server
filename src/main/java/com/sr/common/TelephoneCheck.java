package com.sr.common;

import javax.net.ssl.*;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author cyh
 * @Date 2021/9/30 15:20
 */
public class TelephoneCheck {
    //手机号正则表达式
    public static final String PHONE_NUMBER_REGEX = "^1(3[0-9])|(4[0-1]|[4-9])|(5[0-3]|[5-9])|(6[2567])|(7[0-8])|(8[0-9])|(9[0-3]|[5-9])\\d{8}$";

    public static String APP_KEY = "349f7272485a8";

    public static String APP_SECRET = "3302a31df896659c443a0079575a81a3";

    public static String ADDRESS = "https://webapi.sms.mob.com/sms/verify";
    /**
     * 验证手机号是否符合正则表达式
     * @param telephone
     * @return
     */
    public static Boolean checkTelephoneNumber (String telephone) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(telephone);
        return matcher.find();
    }

    /**
     * 验证手机号和验证码
     * @param telephone
     * @param code
     * @return
     */
    public static Boolean checkTelephoneNumberAndCode (String telephone, String code) {
        String params = "appkey=" + APP_KEY + "&phone=" + telephone + "&zone=86" + "&code=" + code;
        HttpURLConnection connection = null;
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
                public X509Certificate[] getAcceptedIssuers(){return null;}
                public void checkClientTrusted(X509Certificate[] certs, String authType){}
                public void checkServerTrusted(X509Certificate[] certs, String authType){}
            }};

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());

            //ip host verify
            HostnameVerifier hv = new HostnameVerifier() {
                public boolean verify(String urlHostName, SSLSession session) {
                    return urlHostName.equals(session.getPeerHost());
                }
            };

            //set ip host verify
            HttpsURLConnection.setDefaultHostnameVerifier(hv);

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            URL url = new URL(ADDRESS);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");// POST
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            // set params ;post params
            connection.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(params.getBytes(StandardCharsets.UTF_8));
            out.flush();
            out.close();
            connection.connect();
            //get result
            return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return false;
    }
}
