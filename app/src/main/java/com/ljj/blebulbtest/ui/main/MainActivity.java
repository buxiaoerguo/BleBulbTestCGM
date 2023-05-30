package com.ljj.blebulbtest.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanRecord;
import android.content.Intent;
import android.os.Bundle;

import com.ljj.blebulbtest.R;
import com.ljj.blebulbtest.databinding.ActivityMainBinding;
import com.ljj.blebulbtest.ui.ScanDeviceBean;
import com.ljj.blebulbtest.ui.setting.SettingActivity;
import com.ljj.blebulbtest.util.AppExecutors;
import com.ljj.blebulbtest.util.BaseUtil;
import com.ljj.blebulbtest.util.HexUtil;
import com.ljj.blebulbtest.util.LogUtil;
import com.ljj.blebulbtest.util.SettingUtil;
import com.ljj.blebulbtest.util.bleUtils.BleCallBackListener;
import com.ljj.blebulbtest.util.bleUtils.BleUtil;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MainViewModel mainViewModel;
    public BleUtil bleUtil;
    MainAdapter mainAdapter;
    ArrayList<ScanDeviceBean> bleScanList;
    ArrayList<ScanDeviceBean> bleShowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(getApplication(), this, binding, mainCallBackListener);
        binding.setViewModel(mainViewModel);
        bleUtil = BleUtil.getInstance();
        bleUtil.init(this, bleCallBackListener);
        if (!bleUtil.initBluetooth()) {
            openBle();
        }
        bleScanList = new ArrayList<>();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bleUtil.initCallBack(bleCallBackListener);
    }

    private void initData() {
        binding.tvTitle.setText(BaseUtil.TITLE);
        SettingUtil.PHONE_MODEL = android.os.Build.MANUFACTURER + "  " + android.os.Build.MODEL;
//        SettingUtil.PHONE_SYSTEM= android.os.Build.MANUFACTURER;
        SettingUtil.PHONE_SYSTEM = android.os.Build.VERSION.RELEASE;
        ;
    }

    MainCallBackListener mainCallBackListener = new MainCallBackListener() {
        @Override
        public void scan() {

            bleUtil.startScan();
            bleScanList.clear();
            if (mainAdapter != null) {
                mainAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void stopScan() {
            bleUtil.stopScan();
        }

        @Override
        public void chooseDevice(int position) {
            LogUtil.e("chooseDevice:  " + position);
            LogUtil.e("chooseDevice:  " + bleShowList.get(position).getMac());
            bleUtil.stopScan();
//            bleUtil.connect(bleScanList.get(position).getMac());
            SettingUtil.DEVICE_MAC = bleShowList.get(position).getMac();
            SettingUtil.RSSI = bleShowList.get(position).getRssi();
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }
    };
    BleCallBackListener bleCallBackListener = new BleCallBackListener() {
        @Override
        public void onScan(BluetoothDevice ble, ScanRecord scanRecord, int rssi) {
            String test = HexUtil.encodeHexStr(scanRecord.getBytes());

            LogUtil.e("scanRecord:  " + test);

            LogUtil.e("getname:" + ble.getName() + "");
            LogUtil.e("mac:" + ble.getAddress() + "");
//            String title = test.substring(6, 10);
            String title = test.substring(0, 18);
            String bro = test.substring(10, 14);
            String deviceType = test.substring(14, 18);

//            if (!bro.equals("7008")) {
//                return;
//            }
            String version = test.substring(34, 38);
            String name = test.substring(44, 68);
            String showName = test.substring(18, 30);

//               String names =  BaseUtil.strToASCII(name);
            String names = BaseUtil.getInstance().convertHexToString(name);
//               String names =  BaseUtil.asciiToString(name);
            LogUtil.e("version:" + version);
            LogUtil.e("name:" + name);
            LogUtil.e("rssi:" + rssi);
            int flag = -1;
            for (int j = 0; j < bleScanList.size(); j++) {
                if (bleScanList.get(j).getMac().equals(ble.getAddress())) {
                    flag = j;
                }
            }
            if (ble.getName() != null) {
                ScanDeviceBean bean;
                if (flag != -1) {
                    bean = bleScanList.get(flag);
                } else {
                    bean = new ScanDeviceBean();
                }
                bean.setName(showName);
                if (ble.getName() != null) {
                    bean.setBroName(ble.getName());
//                    bean.setBroName(showName);
                } else {
                    bean.setName("nameError");
                }
                bean.setChecked(false);
                bean.setMac(ble.getAddress());


                if (deviceType.equals("0306")) {
                    deviceType = "10";
                } else if (deviceType.equals("0307")) {
                    deviceType = "A0";
                }

//                if (deviceType.equals("10") || deviceType.equals("A0")) {
                    bean.setDeviceType(deviceType);
                    bean.setVersion(version);
                    ArrayList<Integer> stringList = new ArrayList<Integer>();
//                            stringList=

                    int pos = -1;
                    for (int i = 0; i < bleScanList.size(); i++) {
                        if (bleScanList.get(i).getMac().equals(ble.getAddress())) {
                            pos = i;
                        }
                    }
                    if (pos != -1) {
                        if (bleScanList.get(pos).getRssis().size() == 10) {
                            bleScanList.get(pos).getRssis().remove(0);
                            stringList = bleScanList.get(pos).getRssis();
                        }
                    }
                    stringList.add(rssi);
                    bean.setRssis(stringList);

                    int maxrsi = 0;
                    int minrsi = 0;
//                            ArrayList<Integer> aCheck = stringList;
                    if (stringList.size() > 2) {
                        ArrayList<Integer> aiList = new ArrayList<>();
                        for (int z = 0; z < stringList.size(); z++) {
                            int sxs = stringList.size();
                            for (int x = 0; x < stringList.size(); x++) {
                                if (stringList.get(z) <= stringList.get(x)) {
                                    sxs--;
                                }
                            }
                            if (sxs == 0) {
                                maxrsi = stringList.get(z);
                            }
                            if (sxs == (aiList.size() - 1)) {
                                maxrsi = stringList.get(z);
                            }
//                                stringList.get(z)(sxs);
                            LogUtil.e("sxs:" + sxs);
                        }
                        int s = 0;
                        for (int y = 0; y < stringList.size(); y++) {
                            s = stringList.get(y) + s;
//                                    for (int xy = 0; xy < stringList.size(); xy++) {
//                                        if (stringList.get(y).getCompare() == xy) {
//                                            aiList.add(stringList.get(y));
//                                        }
//                                    }
                        }
                        s = s - maxrsi - minrsi;
                        s = s / (stringList.size() - 2);
                        bean.setRssi(s);
                    } else {
                        bean.setRssi(rssi);
                    }
                    bean.setMode(0);
                    bean.setType(0);
                    if (flag != -1) {

                    } else {
                        bleScanList.add(bean);
                    }

                    bleShowList = new ArrayList<>();
                    for (int i = 0; i < bleScanList.size(); i++) {
                        if (bleScanList.get(i).getRssi() < -70) {

                        } else {

                            bleShowList.add(bleScanList.get(i));
                        }
                    }

                    mainAdapter = new MainAdapter(MainActivity.this, bleShowList);
                    binding.lvDevice.setAdapter(mainAdapter);
//                        }
//                    }
//                }
            }
//            }
        }

        @Override
        public void onConnectSuccess() {

        }

        @Override
        public void onCharacteristicChanged(byte[] data) {

        }

        @Override
        public void onConnectFail() {

        }
    };

    public void openBle() {
        if (!bleUtil.checkBle()) {
            //打开蓝牙
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 10);
//            startActivityForResult(intent, OPEN_BT_REQUESTCODE);
        }
    }

    @Override
    protected void onStop() {

        super.onStop();
        LogUtil.e("onStop : ");
//        AppExecutors.getInstance().scheduledExecutor().schedule(new Runnable() {
//            @Override
//            public void run() {
//                LogUtil.e("onStop  AppExecutors: " );
//            }
//        },3000, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.e("onStart : ");

    }
}