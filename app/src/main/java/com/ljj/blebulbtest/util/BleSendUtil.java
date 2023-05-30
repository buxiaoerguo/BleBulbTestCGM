package com.ljj.blebulbtest.util;

import android.util.Log;


import java.util.ArrayList;


/**
 * @作者 ljj
 * @创建时间 2020/2/24 11:35
 * <p>
 * vendor 指令相关参数
 */

public class BleSendUtil {

    private static BleSendUtil sendUtil;
    private static byte send_cno = 0;
    private byte transitionTime_ON = 8;
    private byte transitionTime_OFF = 8;
    private byte transitionTime = 5;
    private int ONOFF = 0x01;

    public static BleSendUtil getInstance() {
        if (sendUtil == null) {
            synchronized (SendUtil.class) {
                if (sendUtil == null) {
                    sendUtil = new BleSendUtil();
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

    public byte[] getToken() {
        Log.e("send_cno", send_cno + "");
        send_cno++;
        byte[] temp = new byte[4];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 01;
        temp[3] = 9;
        temp = obfs(temp);
        temp[0] = Auth(temp);

        byte[] temps = new byte[9];
        temps[0] = 0;
        temps[1] = 0;
        temps[2] = 0;
        temps[3] = 0;
        temps[4] = 0;
        temps[5] = temp[0];
        temps[6] = temp[1];
        temps[7] = temp[2];
        temps[8] = temp[3];
        return temps;
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
    public byte[] getRealLightness() {
        send_cno++;
        byte[] temp = new byte[4];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 01;
        temp[3] = 02;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
//        return  getBleMessage(temp);
    }

    public byte[] getPower() {
        send_cno++;
        byte[] temp = new byte[4];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 01;
        temp[3] = 47;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }

    /*
     * 0:上电默认关灯
     * 1:上电默认开灯
     * 2:上电保持断电前的状态不变
     * */
    public byte[] setPower(int type) {
        send_cno++;
        byte[] temp = new byte[5];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 02;
        temp[3] = 47;
        temp[4] = (byte) type;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }

    //    1: 功能开关
//    2：亮度模型
    public byte[] getAiCon(int type) {
        send_cno++;
        byte[] temp = new byte[5];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 01;
        temp[3] = 10;
        temp[4] = (byte) type;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }

    public byte[] getBleKey(byte[] key) {
        send_cno++;
        byte[] temp = new byte[8];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 01;
        temp[3] = 63;
        temp[4] = key[0];
        temp[5] = key[1];
        temp[6] = key[2];
        temp[7] = key[3];
        LogUtil.e("temp:" + BaseUtil.bytesToHex(temp));
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }

    public byte[] setAi(int type, int onoff) {
        send_cno++;
        byte[] temp = new byte[6];
        byte ss = (byte) onoff;
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 02;
        temp[3] = 10;
        temp[4] = (byte) type;
        temp[5] = (byte) onoff;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }

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

    public byte[] getDeviceInfo() {
        send_cno++;
        byte[] temp = new byte[4];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 01;
        temp[3] = 48;
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }

    public byte[] getRunTime() {
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

    public byte[] setUTCTime(byte[] message) {

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
        Log.e("TAG", "setUTCTime: " + BaseUtil.bytesToHex(temp));
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

    public byte[] getMac() {
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
//        return  getBleMessage(temp);
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

    public byte BindMsg = 61; // 3d

    public byte[] setBindMsg(int type, byte[] msg) {
        send_cno++;
        byte[] temp = new byte[6 + msg.length];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 02;
        temp[3] = BindMsg;
        temp[4] = (byte) type;
        temp[5] = (byte) msg.length;
        for (int i = 0; i < msg.length; i++) {
            temp[6 + i] = msg[i];
        }
        temp = obfs(temp);
        temp[0] = Auth(temp);
        return temp;
    }

    public byte[] setUnBind() {
        send_cno++;
        byte[] temp = new byte[5];
        temp[0] = 00;
        temp[1] = send_cno;
        temp[2] = 02;
        temp[3] = BindMsg;
        temp[4] = (byte) 0xff;
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

    public byte[] getBleMessages(byte[] temp) {
        byte[] temps = new byte[temp.length + 5];
        for (int i = 0; i < 5; i++) {
            temps[i] = 0;
        }
        for (int i = 0; i < temp.length; i++) {
            temps[5 + i] = temp[i];
        }
        return temps;
    }

    public byte[] getBleMessage(byte[] temp) {
        byte[] temps = new byte[temp.length + 5];
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                temps[i] = 2;
            } else {
                temps[i] = 0;
            }
        }
        for (int i = 0; i < temp.length; i++) {
            temps[5 + i] = temp[i];
        }
        return temps;
    }

    public byte[] getTurnOffDelay() {
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

    public byte[] obfs0(byte[] data) {
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
            return data;
        }

        data = obfs(data);
        return data;
    }

    public byte[] getObfs(byte[] data) {
        data = obfs(data);
        return data;
    }

    public byte[] tokenTest(byte[] data) {
        long seed = data[0];
        for (int i = 1; i < data.length; i++) {
            seed = (214013 * seed + 2531011) & 0xffffffff;
            data[i] ^= (seed >> 16) & 0xff;
        }
        return data;
    }



}
