package com.example.hdxy.baseproject.Util;

/**
 * Created by hdxy on 2018/11/30.
 */

public class StringUtil{
    public static boolean isEmpty(String str) {
        if (str == null || "null".equals(str) || str.length() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
