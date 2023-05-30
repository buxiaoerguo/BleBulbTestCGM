package com.ljj.blebulbtest.util;

import android.annotation.SuppressLint;
import android.util.Log;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

/**
 * @author ljj
 * @date 2020/5/18 10:45
 * @description 基础工具类
 */
public class BaseUtil {
    public static String BULB_PID = "12296";
    public static String GATEWAY_PID = "12821";
    public static String SWITCH_PID = "12309";
    public static BaseUtil baseUtil;
//    public static ArrayList<AiBean> aiList;
//    public static List<GetBleDeviceListRequest.DataBean> allList;
    public static String aiMsg = "";
    public static String TITLE = "V 0.0.5";
    public static String Device_Version = "V 0.0.4";
    public static int onoffs;
    public static int aiTime;
    public static int aiNowTime;
    public static String CREATE_TIME = "";
    public static String MD5 = "";
    //    public static String GATEWAY_DID = "JA_SLB0_2003110000";
    public static String GATEWAY_DID = "";
    public String GATEWAY_MODEL = "JA_SLB0";
    public static String BULB_MODEL = "JA_SL10";
    public String GATEWAY_TYPE = "88888888";
    /**
     * uuid
     */
    public static String serviceUUID = "31305642-4C53-2E6E-6175-696A2E6D6F63";
    public static String characteristicUUID = "31305642-4C53-2E6E-6175-696A2E6E6573";
    public static String characteristicUUIDs = "31305642-4C53-2E6E-6175-696A2E636572";
    public static String firstServiceUUID = "0000180a-0000-1000-8000-00805f9b34fb";
    public static String COMPANY_UUID = "00002a29-0000-1000-8000-00805f9b34fb";
    public static String DEVICE_TYPE_UUID = "00002a24-0000-1000-8000-00805f9b34fb";
    public static String DEVICE_ID_UUID = "00002a25-0000-1000-8000-00805f9b34fb";
    public static String FIRMWARE_UUID = "00002a26-0000-1000-8000-00805f9b34fb";
    public static String HARDWARE_UUID = "00002a27-0000-1000-8000-00805f9b34fb";
    //ota
    public static UUID server_ota = UUID.fromString("44415247-5055-2E6E-6175-696A2E6D6F63");
    public static UUID character_ota_control = UUID.fromString("44415247-5055-2E6E-6175-696A2E727463");
    public static UUID character_ota_data = UUID.fromString("44415247-5055-2E6E-6175-696A2E746164");
    /**
     * wyze_url
     */
    public static String WYZE_SERVER = "https://test-bulb-service.wyzecam.com";
    public static String GET_TOKEN = "/plugin/bulb/get_token";
    public static String CHECK_DEVICE_TOKEN = "/plugin/bulb/check_device_token";
    public static String BIND_DEVICE = "/plugin/bulb/bind_device";
    public static String GET_BIND_KEY = "/plugin/bulb/get_bind_key";
    public static String GET_DEVICE_SETTING = "/plugin/bulb/get_device_setting";
    public static String UNBIND_DEVICE = "/plugin/bulb/unbind_device";
    public static String SET_IOT_PROP = "/plugin/bulb/set_iot_prop";
    public static String SEND_DATA = "/plugin/bulb/send_data";
    public static String QUERY_DATA = "/plugin/bulb/query_data";
    /**
     * 获取网络配置文件的信息（上传）
     */
    public static String UPLOAD_NETWORK = "/plugin/bulb/upload_network_profile";
    /**
     * 获取网络配置文件的信息（下载）
     */
    public static String DOWNLOAD_NETWORK = "/plugin/bulb/network_profile";

    public static String UPDATA_NETWORK = "/plugin/bulb/network_profile";


    public static String GET_TOKEN_URL = WYZE_SERVER + GET_TOKEN;
    public static String CHECK_DEVICE_TOKEN_URL = WYZE_SERVER + CHECK_DEVICE_TOKEN;
    public static String BIND_DEVICE_URL = WYZE_SERVER + BIND_DEVICE;
    public static String GET_BIND_KEY_URL = WYZE_SERVER + GET_BIND_KEY;
    public static String GET_DEVICE_SETTING_URL = WYZE_SERVER + GET_DEVICE_SETTING;
    public static String UNBIND_DEVICE_URL = WYZE_SERVER + UNBIND_DEVICE;
    public static String SET_IOT_PROP_URL = WYZE_SERVER + SET_IOT_PROP;

    public static String GET_UPLOAD_NETWORK = WYZE_SERVER + UPLOAD_NETWORK;
    public static String GET_DOWNLOAD_NETWORK = WYZE_SERVER + DOWNLOAD_NETWORK;
    public static String POST_UPDATA_NETWORK = WYZE_SERVER + UPDATA_NETWORK;
    public static String SEND_DATA_URL = WYZE_SERVER + SEND_DATA;
    public static String QUERY_DATA_URL = WYZE_SERVER + QUERY_DATA;

    public String UNPROVISIONED_DEV_LIST = "unprovisioned_dev_list";
    public String UNPROVISIONED_CHOICE = "unprovisioned_choice";
    public String PROVISIONED_DEL_DEV = "provisioned_del_dev";
    public String PROVISIONED_NEW_GRP = "provisioned_new_grp";
    public String PROVISIONED_DEL_GRP = "provisioned_del_grp";
    public String PROVISIONED_MOVE = "provisioned_move";
    public String PROVISIONED_SWITCH = "provisioned_switch";
    public String PROVISIONED_SWITCH_QUERY = "provisioned_switch_query";
    public String PROVISIONED_LIGHTNESS = "provisioned_lightness";
    public String PROVISIONED_LIGHTNESS_QUERY = "provisioned_lightness_query";
    public String MESH_CFGFILE_UPLOAD = "mesh_cfgfile_upload";
    public String MESH_CFGFILE_DOWNLOAD = "mesh_cfgfile_download";
    public String MESH_CFGFILE_QUERY = "mesh_cfgfile_query";
    public String PROVISIONED_DIDKEY_QUERY = "provisioned_didkey_query";
    public String PROVISIONED_UTC_SET = "provisioned_utc_set";
    public String MESH_DEVICE_RENAME = "mesh_device_rename";

    public static BaseUtil getInstance() {
        if (baseUtil == null) {
            synchronized (BaseUtil.class) {
                if (baseUtil == null) {
                    baseUtil = new BaseUtil();
                }
            }
        }
        return baseUtil;
    }

    public String getCalTime(int reTime, String AbTime) {
        String finalTime = AbTime;
        int minute = Integer.parseInt(AbTime.substring(14, 16));
        int hour = Integer.parseInt(AbTime.substring(11, 13));
        int day = Integer.parseInt(AbTime.substring(8, 10));
        int month = Integer.parseInt(AbTime.substring(5, 7));
        int year = Integer.parseInt(AbTime.substring(0, 4));
        int realMin = reTime + minute;
        int realHour = 0;
        int realDay = 0;
        if (realMin > 59) {
            realHour = realMin / 60;
            int testMin = realHour * 60;
            realMin = realMin - testMin;
            realHour = realHour + hour;
            if (realHour > 23) {
                realDay = realHour / 24;
                int testhour = realDay * 24;
                realHour = realHour - testhour;
            }
            realDay = realDay + day;
            if (realDay > 31) {
                month = month + 1;
                if (month > 12) {
                    year = year + 1;
                    month = month - 12;
                }
                realDay = realDay - 31;
            } else {
                if (realDay == 31) {
                    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                        realDay = 31;
                    } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                        month = month + 1;
                        realDay = realDay - 30;
                    } else if (month == 2) {
                        month = month + 1;
                        int y = year / 4;
                        int years = y * 4;
                        int yy = year - years;
                        if (yy != 0) {
                            realDay = realDay - 28;
                        } else {
                            realDay = realDay - 29;
                        }
                    }
                } else if (realDay == 30) {
                    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                        realDay = 30;
                    } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                        realDay = 30;
                    } else if (month == 2) {
                        month = month + 1;
                        int y = year / 4;
                        int years = y * 4;
                        int yy = year - years;
                        if (yy != 0) {
                            realDay = realDay - 28;
                        } else {
                            realDay = realDay - 29;
                        }
                    }
                } else {
                    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                        realDay = realDay;
                    } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                        realDay = realDay;
                    } else if (month == 2) {
                        int y = year / 4;
                        int years = y * 4;
                        int yy = year - years;
                        if (yy != 0) {
                            realDay = realDay - 28;
                            if (realDay == 0) {
                                realDay = 28;
                            } else {
                                month = month + 1;
                                realDay = realDay - 28;
                            }
                        } else {
                            realDay = realDay - 29;
                            if (realDay == 0) {
                                realDay = 29;
                            } else {
                                month = month + 1;
                                realDay = realDay - 29;
                            }
                        }
                    }
                }
            }
            String months = month + "";
            String dayss = realDay + "";
            String hourss = realHour + "";
            String minss = realMin + "";
            if (month < 10) {
                months = "0" + month;
            }
            if (realDay < 10) {
                dayss = "0" + realDay;
            }
            if (realHour < 10) {
                hourss = "0" + realHour;
            }
            if (realMin < 10) {
                minss = "0" + realMin;
            }
            finalTime = year + "-" + months + "-" + dayss + " " + hourss + ":" + minss;
        } else {
            String months = month + "";
            String dayss = day + "";
            String hourss = hour + "";
            String minss = realMin + "";
            if (month < 10) {
                months = "0" + month;
            }
            if (day < 10) {
                dayss = "0" + day;
            }
            if (hour < 10) {
                hourss = "0" + hour;
            }
            if (realMin < 10) {
                minss = "0" + realMin;
            }
            finalTime = year + "-" + months + "-" + dayss + " " + hourss + ":" + minss;
        }
        return finalTime;
    }

    public String getStamp() {
        String test = Local2UTC();
        String utcTime = "";
        try {
            utcTime = dateToStamp(test).substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return utcTime;
    }

    public static int getSecondTimestamp(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0, length - 3));
        } else {
            return 0;
        }
    }

    public byte[] setTime() {
        String test = Local2UTC();
        String utcTime = "";
        try {
            utcTime = dateToStamp(test).substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int utcTimes = Integer.parseInt(utcTime);

        int tesst = utcTimes - 1000000000;
        String hh = Integer.toHexString(tesst);
        Log.e("TAG", "setTime: " + hh);
        return hexToByteArray(hh);
    }

    public String Local2UTC() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("gmt"));
        return sdf.format(new Date());
    }

    private String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    public static String getDeviceType(String PID) {
        String type = "";

        if (PID.equals(BULB_PID)) {
            type = "Bulb";
        } else if (PID.equals(GATEWAY_PID)) {
            type = "GateWay";
        } else if (PID.equals(SWITCH_PID)) {
            type = "Switch";
        }

        return type;


    }
    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }


    private static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    /**
     * 字节数组转16进制
     *
     * @param bytes 需要转换的byte数组
     * @return 转换后的Hex字符串
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String getTime() {

        long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳

        String str = String.valueOf(time);

        return str;

    }

    public static String asciiToString(String value) {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }

    /**
     * 16进制转换成为string类型字符串
     *
     * @param s
     * @return
     */
    public static String hexStringToString(String s) {
        if (s == null || "".equals(s)) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }
    public String convertHexToString(String hex){

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for( int i=0; i<hex.length()-1; i+=2 ){

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char)decimal);

            temp.append(decimal);
        }

        return sb.toString();
    }
//    /**
//     * 16进制直接转换成为字符串(无需Unicode解码)
//     *
//     * @param hexStr
//     * @return
//     */
//    public static String hexStr2Str(String hexStr) {
//        String str = "0123456789ABCDEF";
//        char[] hexs = hexStr.toCharArray();
//        byte[] bytes = new byte[hexStr.length() / 2];
//        int n;
//        for (int i = 0; i < bytes.length; i++) {
//            n = str.indexOf(hexs[2 * i]) * 16;
//            n += str.indexOf(hexs[2 * i + 1]);
//            bytes[i] = (byte) (n & 0xff);
//        }
//        return new String(bytes);
//    }
  /* 将字符串转成ASCII值
     */
    public static String strToASCII(String data) {
        String requestStr = "";
        for (int i = 0; i < data.length(); i++) {
            char a = data.charAt(i);
            int aInt = (int) a;
            requestStr = requestStr + integerToHexString(aInt);
        }
        return requestStr;
    }
    /**
     * 将十进制整数转为十六进制数，并补位
     * */
    public static String integerToHexString(int s) {
        String ss = Integer.toHexString(s);
        if (ss.length() % 2 != 0) {
            ss = "0" + ss;//0F格式
        }
        return ss.toUpperCase();
    }
    /**
     * 16进制转ASCII
     *
     * @param hex
     * @return
     */
    public static String hex2Str(String hex) {
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {
            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char) decimal);
            temp.append(decimal);
        }
        return sb.toString();
    }

    /**
     * 转化十六进制编码为字符串
     */
    public static String toStringHex2(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
                        i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    //将二进制字符串转换成int数组
    public int[] BinstrToIntArray(String binStr) {
        char[] temp = binStr.toCharArray();
        int[] result = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            result[i] = temp[i] - 48;
        }
        return result;
    }

    // 将二进制字符串转换成Unicode字符串
    public String BinstrToStr(String binStr) {
        String[] tempStr = StrToStrArray(binStr);
        char[] tempChar = new char[tempStr.length];
        for (int i = 0; i < tempStr.length; i++) {
            tempChar[i] = BinstrToChar(tempStr[i]);
        }
        return String.valueOf(tempChar);
    }

    // 将初始二进制字符串转换成字符串数组，以空格相隔
    private String[] StrToStrArray(String str) {
        return str.split(" ");
    }

    //将二进制字符串转换为char
    public char BinstrToChar(String binStr) {
        int[] temp = BinstrToIntArray(binStr);
        int sum = 0;
        for (int i = 0; i < temp.length; i++) {
            sum += temp[temp.length - 1 - i] << i;
        }
        return (char) sum;
    }

    //将字符串转换成二进制字符串，以空格相隔
    public String StrToBinstr(String str) {
        char[] strChar = str.toCharArray();
        String result = "";
        for (int i = 0; i < strChar.length; i++) {
            result += Integer.toBinaryString(strChar[i]) + " ";
        }

        return result;
    }

    /**
     * 十六进制转byte[]数组
     */
    public static byte[] hexStr2bytes(String hex) {
        int m = 0, n = 0;
        int byteLen = hex.length() / 2; // 每两个字符描述一个字节
        byte[] ret = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            int intVal = Integer.decode("0x" + hex.substring(i * 2, m) + hex.substring(m, n));
            ret[i] = Byte.valueOf((byte) intVal);
        }
        return ret;
    }

    public static String str2HexStr(String str) {

        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    public static byte[] convertByteArray(int n) {
        byte[] buf = new byte[2];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = (byte) (n >> i & 0xff);
        }
        return buf;
    }

    /*lowercase letters transform to capital letter*/
    public static char lowtocap(char c) {
        if ((c >= 'a') && (c <= 'z')) {
//            c = c - 32;       // 或者c = c + ('a' - 'A');
            c = (char) (c + ('a' - 'A'));
        }
        return c;
    }

    /*ascii string transform to 16 jinzhi Hex*/
    public static void AsciiToHex(byte[] src, byte[] dest, int len) {
        int dh, dl;   // 16进制的高4位和低4位
        byte ch, cl;  //字符串的高位和低位
        int i;
        if (src == null || dest == null) {
            LogUtil.e("TAG", "src or dest is NULL\n");
            return;
        }
        if (len < 1) {
            LogUtil.e("TAG", "\"length is NULL\n");
//            printf("length is NULL\n");
            return;
        }
        for (i = 0; i < len; i++) {
            ch = src[2 * i];
            cl = src[2 * i + 1];
            dh = lowtocap((char) ch) - '0';
            if (dh > 9) {
                dh = lowtocap((char) ch) - 'A' + 10;   //lowtocap可以用库函数toupper
            }
            dl = lowtocap((char) cl) - '0';
            if (dl > 9) {
                dl = lowtocap((char) cl) - 'A' + 10;   //lowtocap可以用库函数toupper
            }
            dest[i] = (byte) (dh * 16 + dl);
        }
        if (len % 2 > 0)   //字符串个数为奇数
        {
            dest[len / 2] = (byte) (src[len - 1] - '0');
            if (dest[len / 2] > 9) {
                dest[len / 2] = (byte) (lowtocap((char) src[len - 1]) - 'A' + 10);
            }
        }
    }

    public int getTimes() {
        int times;
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
//        int hour = c.get(Calendar.HOUR_OF_DAY);
        int s = hour * 60;
        times = s + minute;
        return times;
    }

    public static String getAiTime(int time) {
//        LogUtil.e("time : " + time);
        int hour;
        int min;
        if (time < 60) {
            hour = 0;
            min = time;
        } else {
            hour = time / 60;
            int ss = hour * 60;
            min = time - ss;
        }
//        LogUtil.e("hour : " + hour);
//        LogUtil.e("min : " + min);
        String sss = hour + "时" + min + "分";
        return sss;
    }
    public static int getAiHour(int time) {
//        LogUtil.e("time : " + time);
        int hour;
        int min;
        if (time < 60) {
            hour = 0;
            min = time;
        } else {
            hour = time / 60;
            int ss = hour * 60;
            min = time - ss;
        }
//        LogUtil.e("hour : " + hour);
//        LogUtil.e("min : " + min);
//        String sss = hour + "时" + min + "分";
        return hour;
    }
}
