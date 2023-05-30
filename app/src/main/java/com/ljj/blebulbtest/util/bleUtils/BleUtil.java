package com.ljj.blebulbtest.util.bleUtils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.ljj.blebulbtest.util.AppExecutors;
import com.ljj.blebulbtest.util.BaseUtil;
import com.ljj.blebulbtest.util.BleHelper;
import com.ljj.blebulbtest.util.BleSendUtil;
import com.ljj.blebulbtest.util.LogUtil;
import com.ljj.blebulbtest.util.SettingUtil;
import com.ljj.blebulbtest.util.xxtea.XXTEA;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import androidx.core.app.ActivityCompat;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 *
 *@author ljj
 *
 *@date 2020/12/14 16:54
 *
 * @description 基础蓝牙工具类
 */
public class BleUtil {
    public BleHelper bleHelper;
    public BleSendUtil sendUtil;
    public Context mContext;
    public Activity activity;
    private BluetoothAdapter mBleAdapter;
    private static BleUtil instance;
    public BleCallBackListener listener;
    public BluetoothManager bluetoothManager;

    public void init(Activity context, BleCallBackListener listener) {
        if (this.mContext == null && context != null) {
            this.mContext = context.getApplicationContext();
            this.activity = context;
            initCallBack(listener);
            bleHelper = new BleHelper();
            sendUtil = new BleSendUtil();
            bluetoothManager = (BluetoothManager) this.mContext.getSystemService(Context.BLUETOOTH_SERVICE);
            mBleAdapter = bluetoothManager.getAdapter();
        }
    }
    public void initCallBack(BleCallBackListener listener) {
        this.listener = listener;
    }
    public static BleUtil getInstance() {
        if (instance == null) {
            synchronized (BleUtil.class) {
                if (instance == null) {
                    instance = new BleUtil();
                }
            }
        }
        return instance;
    }
    @SuppressLint("MissingPermission")
    public boolean initBluetooth() {
        boolean onOff = true;
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null || !adapter.isEnabled()) {

            onOff = false;
        } else if (!requestPermissions()) {

            onOff = true;
        }
        return onOff;
    }
    @SuppressLint("ObsoleteSdkInt")
    public boolean requestPermissions() {
        ArrayList<String> permissionList = new ArrayList<String>();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (mContext.checkSelfPermission(Manifest.permission.BLUETOOTH) != PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.BLUETOOTH);
            }
            if (mContext.checkSelfPermission(Manifest.permission.CAMERA) != PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.CAMERA);
            }
            if (mContext.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (mContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (mContext.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (mContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (mContext.checkSelfPermission(Manifest.permission.GET_ACCOUNTS) != PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.GET_ACCOUNTS);
            }
        }

        if (permissionList.size() > 0) {
            LogUtil.e("requestPermissions: permissionList size = " + permissionList.size());

            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            LogUtil.e("requestPermissions: permissions length = " + permissions.length);
            for (int i = 0; i < permissions.length; i++) {
                LogUtil.e("requestPermissions: permissions[" + i + "] = " + permissions[i]);
            }

            ActivityCompat.requestPermissions(activity, permissions, 11);
            return true;
        }
        return false;
    }

    public void startScan() {
        mBleAdapter.getBluetoothLeScanner().startScan(callback);
    }

    public void stopScan() {
        mBleAdapter.getBluetoothLeScanner().stopScan(callback);
    }

    private BluetoothGatt mBluetoothGatt;

    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;

    }

    public void disConnect() {

        if (mBleAdapter == null || mBluetoothGatt == null) {
            LogUtil.e("BluetoothAdapter not initialized.");
            return;
        }
        mBluetoothGatt.disconnect();
//        mBluetoothGatt = null;
    }

    /**
     * 连接设备
     *
     * @param address 设备mac地址
     */
    public void connect(String address) {

        if (mBleAdapter == null || address == null) {
            LogUtil.e("No device found at this address：" + address);
            return;
        }

        try {
            BluetoothDevice remoteDevice = mBleAdapter.getRemoteDevice(address);
            if (remoteDevice == null) {
                LogUtil.e("Device not found.  Unable to connect.");
                return;
            }
            //第四步：连接蓝牙
//            mBluetoothGatt = remoteDevice.connectGatt(mContext, false, mGattCallBack,BluetoothDevice.TRANSPORT_LE);
            connectDevice(remoteDevice);
            LogUtil.e("connecting mac-address:" + address);
        } catch (Exception e) {
            LogUtil.e("蓝牙地址错误，请重新绑定");
        }
    }


    public boolean refreshDeviceCache() {
        if (mBluetoothGatt != null) {
            try {
                BluetoothGatt localBlueToothGatt = mBluetoothGatt;
                Method localMethod = localBlueToothGatt.getClass().getMethod("refresh", new Class[0]);
                if (localMethod != null) {
                    Boolean bool = ((Boolean) localMethod.invoke(localBlueToothGatt, new Object[0])).booleanValue();
                    return bool;
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e2) {
                e2.printStackTrace();
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 连接设备Gatt
     *
     * @param device 设备
     */
    private void connectDevice(final BluetoothDevice device) {
        if (mBleAdapter == null) {
            LogUtil.e( "connectDevice: BluetoothAdapter not initialized.");
            return;
        }
        if (!mBleAdapter.isEnabled()) {
            LogUtil.e( "connectDevice: BluetoothAdapter is disabled");
            return;
        }
        if (device == null) {
            LogUtil.e( "connectDevice: Unspecified device.");
            return;
        }
        //防止连接出现133错误, 不能发现Services
        if (mBluetoothGatt != null ){
//                || getState() == State.Connecting) {
            LogUtil.e( "connectDevice: closeGatt");
            mBluetoothGatt.disconnect();
            close();
        }
        if (mBluetoothGatt != null && mBluetoothGatt.getDevice() != null && mBluetoothGatt.getDevice().equals(device)) {
            LogUtil.e( "connectDevice: Trying to use an existing mBluetoothGatt for connection.");
            mBluetoothGatt.connect();
        } else {
//            LogUtil.e( "connectDevice: Trying to create a new connection.");
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                mBluetoothGatt = device.connectGatt(MyApplication.getInstance(), false, mGattCallback, BluetoothDevice.TRANSPORT_LE);
//            } else {
//                mBluetoothGatt = device.connectGatt(MyApplication.getInstance(), false, mGattCallback);
            mBluetoothGatt = device.connectGatt(mContext, false, mGattCallBack);
//            }
        }
    }
    BluetoothGattCallback mGattCallBack = new BluetoothGattCallback() {
        @Override
        public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyUpdate(gatt, txPhy, rxPhy, status);
        }

        @Override
        public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyRead(gatt, txPhy, rxPhy, status);
        }

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            LogUtil.e("status:"+ status +"newState:" + newState);
            //102
            if (newState == 2) {
                LogUtil.e(" Gatt success: " + gatt.getDevice().getAddress());
                if (SettingUtil.TEST_TYPE == 1) {
                    gatt.discoverServices();
                } else {
                    listener.onConnectSuccess();
                }
            } else {
                if (status == 133 ||status == 62 ){
                    LogUtil.e("status"+ status );
                    disConnect();
                    close();
                    connect(SettingUtil.DEVICE_MAC);
                }else {
                    LogUtil.e("Gatt fail: " + gatt.getDevice().getAddress());
                    listener.onConnectFail();
                }
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            LogUtil.e("onServicesDiscovered");
            gatt.requestMtu(158);
            //101
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            LogUtil.e("onCharacteristicRead");
            //104
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            LogUtil.e("onCharacteristicWrite");
            String address = gatt.getDevice().getAddress();
            for (int i = 0; i < characteristic.getValue().length; i++) {
                LogUtil.i("address: " + address + ",Write: " + characteristic.getValue()[i]);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            LogUtil.e("onCharacteristicChanged");
            // 105

            byte[] data = characteristic.getValue();
            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                @Override
                public void run() {
                    String dataStr = BaseUtil.bytesToHex(data);
                    LogUtil.e("data:" + dataStr);
                    if (dataStr.substring(0, 2).equals("00")) {
                        byte[] resData = new byte[data.length - 6];
                        for (int i = 0; i < data.length - 6; i++) {
                            resData[i] = data[6 + i];
                        }
                        LogUtil.e("resData:" + BaseUtil.bytesToHex(resData));
                        resData = sendUtil.obfs0(resData);
                        LogUtil.e("resData:" + BaseUtil.bytesToHex(resData));
                        byte[] key = new byte[4];
                        key[0] = resData[4];
                        key[1] = resData[5];
                        key[2] = resData[6];
                        key[3] = resData[7];
                        LogUtil.e("resData:" + BaseUtil.bytesToHex(resData));
                        String hes = BaseUtil.bytesToHex(key);
                        LogUtil.e("key:" + hes);
                        long sss10 = Long.parseLong(hes, 16);
                        LogUtil.e("sss10:" + sss10);
                        Long test = bleHelper.DHKGE(bleHelper.random, sss10);
                        LogUtil.e("test : " + test);
                        long ketts = test;

                        LogUtil.e("ketts : " + ketts);
                        String keys = Integer.toHexString((int) ketts);
                        LogUtil.e("keys :" + keys);
                        bleHelper.cryptKey = keys.getBytes();
                        LogUtil.e("bleHelper.cryptKey :" + BaseUtil.bytesToHex(bleHelper.cryptKey));
                        AppExecutors.getInstance().scheduledExecutor().schedule(new Runnable() {
                            @Override
                            public void run() {
                                LogUtil.e("writeChar0 : ");

//                                byte[] t = sendUtil.getFlash();
                                byte[] t = sendUtil.getRealLightness();
                                sendBleMessage(t);
                            }
                        }, 300, TimeUnit.MILLISECONDS);
                    } else {

                        listener.onCharacteristicChanged(data);
//                        AppExecutors.getInstance().scheduledExecutor().schedule(new Runnable() {
//                            @Override
//                            public void run() {
//                                LogUtil.e("setRealLightness : ");
////                                byte[] t = sendUtil.setRealLightness(100);
//                                byte[] t = sendUtil.getRealLightness();
//                                sendBleMessage(t);
//                            }
//                        }, 300, TimeUnit.MILLISECONDS);
                    }
                }
            });
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
            LogUtil.e("onDescriptorRead");
            //106
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            LogUtil.e("onDescriptorWrite");
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
            LogUtil.e("onReadRemoteRssi");
            //107
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
            LogUtil.e("onMtuChanged" + mtu);
            BluetoothGattService linkLossService = gatt.getService(UUID.fromString(BaseUtil.serviceUUID));
            BluetoothGattCharacteristic data = linkLossService.getCharacteristic(UUID.fromString(BaseUtil.characteristicUUIDs));
            int ss = mBluetoothGatt.getServices().size();
            for (int i = 0; i < ss; i++) {
                for (int j = 0; j < mBluetoothGatt.getServices().get(i).getCharacteristics().size(); j++) {
                }
            }
            if (null != data) {
            } else {
            }
            mBluetoothGatt.setCharacteristicNotification(data, true);
            BluetoothGattDescriptor descriptor = data.getDescriptor(
                    UUID.fromString("00002902-0000-1000-8000-00805F9B34FB"));
            descriptor.setValue(true ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE :
                    BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(descriptor);
            AppExecutors.getInstance().scheduledExecutor().schedule(new Runnable() {
                @Override
                public void run() {
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            LogUtil.e("103 randNum: ");
                            randNum();
                        }
                    });
                }
            }, 1000, TimeUnit.MILLISECONDS);
            //103
        }
    };
    ScanCallback callback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            //对结果处理
            BluetoothDevice bluetoothDevice = result.getDevice();
            int rssi = result.getRssi();
            ScanRecord scanRecord = result.getScanRecord();
            listener.onScan(bluetoothDevice, scanRecord, rssi);
        }
    };

    public boolean checkBle() {
        if (!mBleAdapter.isEnabled()) {
            return false;
        } else {
            return true;
        }
    }

    public void randNum() {
        long min = 400000000;
        long max = 616127803;
        long rangeLong = min + (((long) (new Random().nextDouble() * (max - min))));
        long b = rangeLong;
        Long ds = Long.valueOf(b);
        bleHelper.random = ds;
        long d = bleHelper.DHKeyGenerateDefault(b);
        int sss = (int) d;

        LogUtil.e("bleHelper.random:" + bleHelper.random);
        LogUtil.e("d:" + d);


        String hex = Integer.toHexString(sss);
        byte[] ss = BaseUtil.hexStr2bytes(hex);
        if (ss.length != 4) {
            randNum();
        } else {
            dhKeyGenerate(ss);
        }
    }

    private byte[] checkKey(byte[] old) {
        byte[] data;
        byte[] t = new byte[old.length + 1];
        for (int i = 0; i < old.length + 1; i++) {
            if (i == 0) {
                t[0] = (byte) old.length;
            } else {
                t[i] = old[i - 1];
            }
        }
        int oa = t.length / 8;
        int os = t.length % 8;
        if (os != 0) {
            int real = oa + 1;
            int size = real * 8;
            data = new byte[size];
            for (int i = 0; i < t.length; i++) {
                data[i] = t[i];
            }
            int ts = 8 - os;
            for (int i = 0; i < ts - 1; i++) {
                data[t.length + i] = 0;
            }
        } else {
            data = t;
        }
        if (bleHelper.cryptKey != null) {
            bleHelper.data = data;
            byte[] tests = XXTEA.encrypt(bleHelper.data, bleHelper.cryptKey, false);
            byte[] realMsg = sendUtil.getBleMessage(tests);
            return realMsg;
        } else {
            return old;
        }
    }

    public void dhKeyGenerate(byte[] key) {
        byte[] t = sendUtil.getBleKey(key);
        byte[] s = new byte[t.length + 1];
        s[0] = (byte) t.length;
        for (int i = 0; i < t.length + 1; i++) {
            if (i == 0) {
                s[0] = (byte) t.length;
            } else {
                s[i] = t[i - 1];
            }
        }
        byte[] st = sendUtil.getBleMessages(s);
        if (mBluetoothGatt.getServices().size() != 0) {
            LogUtil.e("getServices Size:" + mBluetoothGatt.getServices().size());
            if (mBluetoothGatt.getService(UUID.fromString(BaseUtil.serviceUUID)).getCharacteristics().size() != 0) {
                LogUtil.e("serviceSize :" + mBluetoothGatt.getService(UUID.fromString(BaseUtil.serviceUUID)).getCharacteristics().size());
                writeCharacteristic(BaseUtil.serviceUUID, BaseUtil.characteristicUUID, st);
            }
        }

    }

    public void sendBleMessage(byte[] msg) {
        byte[] message = checkKey(msg);
        if (message == msg) {
            randNum();
        } else {
            byte[] datas = new byte[1];
            datas[0] = 0;
            writeCharacteristic(BaseUtil.serviceUUID, BaseUtil.characteristicUUID, message);
        }
    }

    public boolean writeCharacteristic(String serviceUUID, String characteristicUUID, byte[] value) {
        if (mBluetoothGatt != null) {
            BluetoothGattService service =
                    mBluetoothGatt.getService(UUID.fromString(serviceUUID));
            BluetoothGattCharacteristic characteristic =
                    service.getCharacteristic(UUID.fromString(characteristicUUID));
            characteristic.setValue(value);
            return mBluetoothGatt.writeCharacteristic(characteristic);
        }
        return false;
    }

    public void getLightness() {
        byte[] t = sendUtil.getRealLightness();
        sendBleMessage(t);
    }
}
