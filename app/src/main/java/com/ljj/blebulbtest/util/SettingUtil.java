package com.ljj.blebulbtest.util;

import java.util.Calendar;

public class SettingUtil {
    public static String PHONE_MODEL = "";
    public static String PHONE_SYSTEM = "";
    public static int TEST_TIMES = 1;
    public static int TEST_TIMES_NOW = 0;
    public static int CONNECT_SUCCESS = 0;
    public static int CONNECT_FAIL = 0;
    public static String TEST_DISTANCE = "";
    public static String sdCard = "";
    //s
    public static int TEST_INTERVAL = 1;
    public static String CHOOSE_DEVICE = "";

    public static int TEST_TYPE = 0;
    public static int SEND_1000 = 0;
    public static long SEND_1000_Time = 0;
    public static long SEND_1000_rate = 0;
    public static int CONNECT_TYPE = 0;
    public static int CONNECT_TIME = 0;
    public static int STOP = 0;
//    public static String DEVICE_MAC = "C0:5E:C3:33:C2:DB";
    public static String DEVICE_MAC = "";
    public static int RSSI = 0;
    public static int SEND_TIMES = 0;
    public static int SEND_TIMES_Error = 0;
    public static String START_TEST = "";
    public static String STOP_TEST = "";
    public static long TEST_TIME = 0;
    public static String RESULT = "";

    public static long MINIMUM_SCAN_TIME = 0;
    public static long MAXIMUM_SCAN_TIME = 0;
    public static long AVERAGE_SCAN_TIME = 0;
    public static long ALL_SCAN_TIME = 0;
    public static long SCAN_SUCCESS_TIMES =0 ;
    public static long SCAN_FAIL_TIMES =0 ;
    public static long SCAN_SUCCESS_RATE = 0;

    public static long MINIMUM_CONNECT_TIME = 0;
    public static long MAXIMUM_CONNECT_TIME = 0;
    public static long ALL_CONNECT_TIME = 0;
    public static long AVERAGE_CONNECT_TIME = 0;
    public static String CONNECT_SUCCESS_TIMES = "0";
    public static String CONNECT_FAIL_TIMES = "0";
    public static long CONNECT_SUCCESS_RATE = 0;

    public static void checkConnect() {
        if ((TEST_TIMES_NOW - CONNECT_SUCCESS )> CONNECT_FAIL){
            sdCard =sdCard + "\r\n" + "失败次数："+(TEST_TIMES_NOW - CONNECT_SUCCESS) +  "\r\n" + System.currentTimeMillis() ;
            SdCardUtil.writeTxtToSdcard(DEVICE_MAC,sdCard);
        }
        CONNECT_FAIL = TEST_TIMES_NOW - CONNECT_SUCCESS;

        SCAN_FAIL_TIMES = TEST_TIMES_NOW - SCAN_SUCCESS_TIMES;
    }

    public static void getConnRate(){
        CONNECT_SUCCESS_RATE =   CONNECT_SUCCESS*100 ;
        CONNECT_SUCCESS_RATE = CONNECT_SUCCESS_RATE/TEST_TIMES_NOW;
    }
    public static void getScanRate(){
        SCAN_SUCCESS_RATE =   SCAN_SUCCESS_TIMES*100;
        SCAN_SUCCESS_RATE = SCAN_SUCCESS_RATE/TEST_TIMES_NOW ;
    }
    public static void checkResult() {
//        checkConnect();
        if (TEST_TYPE == 1){
            if (SEND_1000 == 0) {
                RESULT =
                        "测试时间：" + TEST_TIME + "ms" + "\r\n" +
                                "MAC地址：" + DEVICE_MAC + "\r\n" +
                                "收发送消息次数：" + SEND_TIMES + "\r\n" +
                                "失败次数：" + SEND_TIMES_Error + "\r\n"+
                                "发送时间：" + SEND_1000_Time + "ms" + "\r\n"+
                                "发送速率：" + SEND_1000_rate + "\r\n";
            }else {
                RESULT =
                        "测试时间：" + TEST_TIME + "ms" + "\r\n" +
                                "MAC地址：" + DEVICE_MAC + "\r\n" +
                                "收发送消息次数：" + SEND_TIMES + "\r\n" +
                                "失败次数：" + SEND_TIMES_Error + "\r\n"+
                                "发送时间：" + SEND_1000_Time + "ms" + "\r\n"+
                                "发送速率：" + SEND_1000_rate + "\r\n";
            }
        }else {
            if (CONNECT_TYPE == 0) {

                RESULT =
                        "测试时间：" + TEST_TIME + "ms" + "\r\n" +
                                "MAC地址：" + DEVICE_MAC + "\r\n" +
                                "连接次数：" + TEST_TIMES_NOW + "\r\n" +
                                "间隔：" + TEST_INTERVAL + "s" + "\r\n" +
                                "连接最短用时：" + MINIMUM_CONNECT_TIME + "ms" + "\r\n" +
                                "连接最长用时：" + MAXIMUM_CONNECT_TIME + "ms" + "\r\n" +
                                "连接平均用时：" + AVERAGE_CONNECT_TIME + "ms" + "\r\n" +
                                "连接成功次数：" + CONNECT_SUCCESS + "\r\n" +
                                "连接失败次数：" + CONNECT_FAIL + "\r\n" +
                                "连接成功率：" + CONNECT_SUCCESS_RATE + "%";
            } else {
                RESULT =
                        "测试时间：" + TEST_TIME + "\r\n" +
                                "MAC地址：" + DEVICE_MAC + "\r\n" +
                                "连接次数：" + TEST_TIMES_NOW + "\r\n" +
                                "间隔：" + TEST_INTERVAL + "s" + "\r\n" +
                                "扫描最短用时：" + MINIMUM_SCAN_TIME + "ms" + "\r\n" +
                                "扫描最长用时：" + MAXIMUM_SCAN_TIME + "ms" + "\r\n" +
                                "扫描平均用时：" + AVERAGE_SCAN_TIME + "ms" + "\r\n" +
                                "扫描成功次数：" + SCAN_SUCCESS_TIMES + "\r\n" +
                                "扫描失败次数：" + SCAN_FAIL_TIMES + "\r\n" +
                                "扫描成功率：" + SCAN_SUCCESS_RATE + "%" + "\r\n" +
                                "连接最短用时：" + MINIMUM_CONNECT_TIME + "ms" + "\r\n" +
                                "连接最长用时：" + MAXIMUM_CONNECT_TIME + "ms" + "\r\n" +
                                "连接平均用时：" + AVERAGE_CONNECT_TIME + "ms" + "\r\n" +
                                "连接成功次数：" + CONNECT_SUCCESS + "\r\n" +
                                "连接失败次数：" + CONNECT_FAIL + "\r\n" +
                                "连接成功率：" + CONNECT_SUCCESS_RATE + "%";
            }
        }
//                        "最近连接一次花费时间：" + CONNECT_TIME;
    }

    public static String getTime() {
        //获取系统的 日期
        Calendar calendar = Calendar.getInstance();
//年
        int year = calendar.get(Calendar.YEAR);
//月
        int month = calendar.get(Calendar.MONTH) + 1;
//日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
//小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//分钟
        int minute = calendar.get(Calendar.MINUTE);
//秒
        int second = calendar.get(Calendar.SECOND);
        int millisecond = calendar.get(Calendar.MILLISECOND);
        String  time = year + "年" +
                month+ "月"+
                day+ "日"+
                hour+ ":"+
                minute+ ":"+
                second+ "."+
                millisecond;
        return time;
    }
}
