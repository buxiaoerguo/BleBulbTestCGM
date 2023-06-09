package com.ljj.blebulbtest.ui;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 *@author ljj
 *
 *@date 2020/9/1 11:09
 *
 * @description 扫描设备类
 *
 * uuid：设备uuid
 * name：设备名称
 * mac：设备mac地址
 * isChecked： 设备是否被选中
 * mode：扫描方式
 *       -- 0 ： ble
 *          1 ： mesh
 * type：扫描出来的设备种类：
 *       -- 0： 灯
 *          1： 网关
 */
public class ScanDeviceBean implements Serializable {
    public String name;
    public String mac;
    private boolean isChecked;
    public int type;
    public int rssi;
    public int mode;
    public int step;
    public String version;
    public String deviceType;
    public int addResult;//1success 2 error
    public String broName;

    public String getBroName() {
        return broName;
    }

    public void setBroName(String broName) {
        this.broName = broName;
    }

    public ArrayList<Integer> rssis;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public ArrayList<Integer> getRssis() {
        return rssis;
    }

    public void setRssis(ArrayList<Integer> rssis) {
        this.rssis = rssis;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public int getAddResult() {
        return addResult;
    }

    public void setAddResult(int addResult) {
        this.addResult = addResult;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
