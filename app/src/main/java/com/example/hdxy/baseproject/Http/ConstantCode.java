package com.example.hdxy.baseproject.Http;

/**
 * Created by hdxy on 2018/12/1.
 */

public class ConstantCode {
    /**
     * 200请求成功
     * 501参数丢失
     * 502服务器出现异常
     * 503用户登陆过期
     * 504系统运行中
     * 505 用户被封禁
     * 506缺少头参数
     * 507异常操作
     * 508数据异常联系客服
     * 509参数异常
     */
    public static final int SUCCESSCODE = 200;
    public static final int PARAMSMISS = 501;
    public static final int SERVEREXCEPTION = 502;
    public static final int USEROVERDUE = 503;
    public static final int SERVERHANDING = 504;
    public static final int USERUNABLE = 505;
    public static final int HEADERMISS = 506;
    public static final int ABNORMAL_OPERATION = 507;
    public static final int DATA_ANOMALY  = 508;
    public static final int ABNORMAL_PARAMS = 509;
}
