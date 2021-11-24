package com.sr.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author cyh
 * @Date 2021/11/15 17:25
 */
public class StringUtil {
    public static String getBindLinkSeparateByComma(List<String> files) {
        StringBuilder stringBuffer = new StringBuilder();
        for (String file : files) {
            stringBuffer.append(file);
            stringBuffer.append(",");
        }
        return stringBuffer.toString();
    }

    public static List<String> splitCommentMaterial(String materialString) {
        String[] materials = materialString.split(",");
        return new ArrayList<>(Arrays.asList(materials));
    }
}
