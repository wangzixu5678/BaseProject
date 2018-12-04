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

    //过滤一下为空字段
    public static String judgeString(String s){
        if (null == s || "".equals(s.trim()) || "null".equals(s) || "0".equals(s)) {
            return "";
        }
        return s;
    }
}
