package com.sr.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author cyh
 * @Date 2021/9/30 15:20
 */
public class TelephoneCheck {
    //手机号正则表达式
    public static final String PHONE_NUMBER_REGEX = "^1(3[0-9])|(4[0-1]|[4-9])|(5[0-3]|[5-9])|(6[2567])|(7[0-8])|(8[0-9])|(9[0-3]|[5-9])\\d{8}$";

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
        //TODO 接入第三方短信
        return true;
    }
}
