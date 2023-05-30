package com.ljj.blebulbtest.util;

import android.util.Log;


import java.util.ArrayList;


/**
 * @作者 ljj
 * @创建时间 2020/2/24 11:35
 * <p>
 * vendor 指令相关参数
 */

public class SendUtil {

    private static SendUtil sendUtil;
    private static byte send_cno = 0;
    private byte transitionTime_ON = 8;
    private byte transitionTime_OFF = 8;
    private byte transitionTime = 5;
    private int ONOFF = 0x01;

    public static SendUtil getInstance() {
        if (sendUtil == null) {
            synchronized (SendUtil.class) {
                if (sendUtil == null) {
                    sendUtil = new SendUtil();
                }
            }
        }
        return sendUtil;
    }

    public byte[] getAutoBrightness() {
        send_cno++;
        byte[] temp = new byte[4];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 01;
        temp[3] = 10;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }

//  public   byte[] setAutoBrightness(ArrayList<AutoBulbBean> list) {
//        send_cno++;
//        int size = list.size();
//        int length = size * 2;
//        int length2 = length + 5;
//        byte[] temp = new byte[length2];
//        temp[0] = 00;
//        temp[1] = send_cno;
//        temp[2] = 02;
//        temp[3] = 10;
//        temp[4] = (byte) size;
//        for (int i = 0; i < size; i++) {
//            int l = i * 2;
//            temp[l + 5] = list.get(i).getTime();
//            temp[l + 6] = list.get(i).getBrightness();
//        }
//        temp = obfs(temp);
//        temp[0] = Auth(temp);
//        return temp;
//    }

    public byte[] getToken() {
        send_cno++;
        byte[] temp = new byte[4];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 01;
        temp[3] = 9;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }

    public byte[] setAutoBrightnessone(byte time, byte lightness) {
        send_cno++;
        int part = 1;
        Log.e("send_cno", send_cno + "");
        byte[] temp = new byte[7];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 02;
        temp[3] = 10;
        temp[4] = (byte) part;
        temp[5] = time;
        temp[6] = lightness;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }

    public byte[] getRealMessage() {
        send_cno = send_cno++;
        Log.e("send_cno", send_cno + "");
        byte[] temp = new byte[4];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 01;
        temp[3] = 01;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }

    /**
     * 获取灯、群组的亮度
     */
    public  byte[] getRealLightness() {
        send_cno++;
        byte[] temp = new byte[4];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 01;
        temp[3] = 02;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }

    public  byte[] getAiCon(){
        send_cno++;
        byte[] temp = new byte[4];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 01;
        temp[3] = 10;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }

    public byte[] setAi(int onoff) {
        send_cno++;
        byte[] temp = new byte[8];
        byte ss = (byte) onoff;
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 02;
        temp[3] = 10;
        temp[4] = 00;
        temp[5] = 00;
        temp[6] = 00;
        temp[7] = (byte) onoff;
//        temp[5] = transitionTime;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }
//    public byte[] setAi(int onoff, ArrayList<AiBean> list) {
//        send_cno++;
//        int size =  list.size();
//        int listss = size*4;
//        int len = 8+listss;
//        byte[] temp = new byte[len];
//        byte ss = (byte) onoff;
//
//        temp[0] = 00;
//        temp[1] = send_cno;
//        temp[2] = 02;
//        temp[3] = 10;
//        temp[4] = (byte)size;
//        String min = Integer.toHexString(list.get(0).getAiNowTime());
//        LogUtil.e("getAiNowTime:" + list.get(0).getAiNowTime());
//        LogUtil.e("min:" + min);
//        byte[] s= BaseUtil.hexStr2bytes(min);
//        LogUtil.e("s:" + s.length);
//        if (s.length ==1){
//            temp[5] = 00;
//            temp[6] = s[0];
//        }else {
//            temp[5] = s[0];
//            temp[6] = s[1];
//        }
//
////        for(int i = 0;i<size;i++){
////
////        }
//        temp[7] = (byte) onoff;
//        for(int i = 0;i<size;i++){
//            int  o = 4*i;
//            int o1 = o +1+7;
//            int o2 = o +2+7;
//            int o3 = o +3+7;
//            int o4 = o +4+7;
//            byte[] times= BaseUtil.hexStr2bytes( Integer.toHexString(list.get(i).getTime()));
//            byte[] lightness= BaseUtil.hexStr2bytes( Integer.toHexString(list.get(i).getLightness()));
//            if (times.length ==1){
//                temp[o1] = 00;
//                temp[o2] = times[0];
//            }else {
//                temp[o1] = times[0];
//                temp[o2] = times[1];
//            }
//            if (times.length ==1){
//                temp[o3] = 00;
//                temp[o4] = lightness[0];
//            }else {
//                temp[o3] = lightness[0];
//                temp[o4] = lightness[1];
//            }
//        }
////        temp[5] = transitionTime;
//        temp = obfs(temp);
//        temp[0] = Auth(temp);
//        return temp;
//    }
    public byte[] getEmissionPower() {
        send_cno++;
        Log.e("send_cno", send_cno + "");
        byte[] temp = new byte[4];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 01;
        temp[3] = 30;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }
//    public byte[] getDeviceInfo() {
//        send_cno++;
//        Log.e("send_cno", send_cno + "");
//        byte[] temp = new byte[4];
//        temp[0] = 00;
//        temp[1] = send_cno;
//        temp[2] = 01;
//        temp[3] = 30;
//        temp = obfs(temp);
//        temp[0] = Auth(temp);
//        return temp;
//    }

    public byte[] getDeviceInfo() {
        send_cno++;
        byte[] temp = new byte[4];
//        byte ss = (byte) lightness;
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 01;
        temp[3] = 48;
//        temp[4] = (byte) lightness;
//        temp[5] = transitionTime;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }

    public  byte[] getRunTime() {
        send_cno++;
        byte[] temp = new byte[4];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 01;
        temp[3] = 8;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }

    public byte[] getLog() {
        send_cno++;
        byte[] temp = new byte[5];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 01;
        temp[3] = 66;
        temp[4] = 00;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }

    public byte[] getLogTime() {
        send_cno++;
        byte[] temp = new byte[5];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 01;
        temp[3] = 66;
        temp[4] = 01;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }

    public  byte[] setUTCTime(byte[] message) {

        send_cno++;
        byte[] temp = new byte[8];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 02;
        temp[3] = 07;
        temp[4] = message[0];
        temp[5] = message[1];
        temp[6] = message[2];
        temp[7] = message[3];
        Log.e("TAG", "setUTCTime: "+BaseUtil.bytesToHex(temp) );
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }

    public byte[] getMessage() {
        send_cno++;
        Log.e("send_cno", send_cno + "");
        byte[] temp = new byte[3];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 06;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }

    //-20~8
    public byte[] setEmissionPower(byte message) {
        send_cno++;
        byte[] temp = new byte[5];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 01;
        temp[3] = 30;
        temp[4] = message;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }

    //-20~8
    public byte[] getMap(byte message) {
        send_cno++;
        Log.e("send_cno", send_cno + "");
        byte[] temp = new byte[1];
        temp[0] = message;
        return temp;
    }

    /**
     * 获取mac
     */

    public  byte[] getMac() {
        send_cno++;
        byte[] temp = new byte[4];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 01;
        temp[3] = 05;
        temp = obfs(temp);
        temp[0] = Auth(temp);

        return temp;
    }

    /**
     * 获取灯、群组的开关状态
     */

    public byte[] getRealOnOff() {
        send_cno++;
        Log.e("send_cno", send_cno + "");
        byte[] temp = new byte[4];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 01;
        temp[3] = 01;
        temp = obfs(temp);
        temp[0] = Auth(temp);

        return temp;
    }

    /**
     * 给灯、群组发送调节亮度指令
     */


    public byte[] setRealLightness(int lightness) {
        send_cno++;
        byte[] temp = new byte[6];
        byte ss = (byte) lightness;
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 02;
        temp[3] = 02;
        temp[4] = (byte) lightness;
        temp[5] = transitionTime;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }

    /**
     * 给灯、群组发送开关指令
     */
    public byte[] setRealOnOff(boolean onOff) {
        send_cno++;
        byte[] temp = new byte[7];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 02;
        temp[3] = 01;
        if (onOff) {
            temp[4] = 01;
            temp[5] = transitionTime_ON;
        } else {
            temp[4] = 00;
            temp[5] = transitionTime_OFF;
        }
        temp[6] = 00;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;


    }

    public byte[] setRealOnOff(boolean onOff, int transitionTime) {
        send_cno++;
        byte[] temp = new byte[7];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 02;
        temp[3] = 01;
        if (onOff) {
            temp[4] = 01;
            temp[5] = transitionTime_ON;
        } else {
            temp[4] = 00;
            temp[5] = (byte) transitionTime;
        }
        temp[6] = 00;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;


    }

    public byte[] getFlash() {
        send_cno++;
        Log.e("send_cno", send_cno + "");
        byte[] temp = new byte[6];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 07;
        temp[3] = (byte) ONOFF;
        temp[4] = 6;
        temp[5] = 3;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }

    public  byte[] getTurnOffDelay() {
        send_cno++;
        Log.e("send_cno", send_cno + "");
        byte[] temp = new byte[4];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 01;
        temp[3] = 04;
        temp = obfs(temp);
        temp[0] = Auth(temp);

        return temp;
    }




    public byte[] setTurnOffDelay(int delay_time) {
        send_cno++;
        byte[] temp = new byte[6];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 02;
        temp[3] = 04;
        temp[4] = (byte) delay_time;
        temp[5] = 00;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;


    }

    private byte[] obfs(byte[] data) {
        long seed = data[1];

        if (seed < 0) {
            seed += 256;
        }
        for (int i = 2; i < data.length; i++) {
            seed = (214013 * seed + 2531011) & 0xffffffff;
            data[i] ^= (seed >> 16) & 0xff;
        }
        return data;
    }

    private byte Auth(byte[] data) {
        byte result = data[0];
        for (int i = 1; i < data.length; i++) {
            result ^= data[i];
        }
        return result;
    }

    public byte[] getRealData(byte[] data) {
        if (Auth(data) != 0) {
//            show("Auth 错误", 5);
            return data;
        }

        data = obfs(data);
        return data;
    }

}
