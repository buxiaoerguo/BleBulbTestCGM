package com.ljj.blebulbtest.ui.setting;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanRecord;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ljj.blebulbtest.R;
import com.ljj.blebulbtest.databinding.ActivitySettingBinding;
import com.ljj.blebulbtest.ui.ScanDeviceBean;
import com.ljj.blebulbtest.ui.main.MainActivity;
import com.ljj.blebulbtest.ui.main.MainAdapter;
import com.ljj.blebulbtest.util.AppExecutors;
import com.ljj.blebulbtest.util.HexUtil;
import com.ljj.blebulbtest.util.LogUtil;
import com.ljj.blebulbtest.util.SdCardUtil;
import com.ljj.blebulbtest.util.SettingUtil;
import com.ljj.blebulbtest.util.bleUtils.BleCallBackListener;
import com.ljj.blebulbtest.util.bleUtils.BleUtil;
import com.ljj.blebulbtest.util.dialog.TypeAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SettingActivity extends AppCompatActivity {
    ActivitySettingBinding binding;
    SettingViewModel settingViewModel;
    SettingCallBackListener listener;
    SettingAdapter settingAdapter;
    public BleUtil bleUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        settingViewModel = new SettingViewModel(getApplication(), this, binding, listener);
        binding.setViewModel(settingViewModel);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initData();
        initClick();
        scheduled();
    }

    BleCallBackListener bleCallBackListener = new BleCallBackListener() {
        @Override
        public void onScan(BluetoothDevice ble, ScanRecord scanRecord, int rssi) {
            LogUtil.e("");
            String test = HexUtil.encodeHexStr(scanRecord.getBytes());

//            LogUtil.e("scanRecord:  " + test);

//            LogUtil.e("getname:" + ble.getName() + "");
//            LogUtil.e("mac:" + ble.getAddress() + "");
//            String title = test.substring(6, 10);
//            if (ble.getName().equals("7C78B20C0005")) {
//            if (ble.getAddress().equals("C0:5E:C3:33:C2:DB")) {
            if (ble.getAddress().equals(SettingUtil.DEVICE_MAC)) {
//            }
//            if (title.equals("0fff")) {
//                String WyzeCid = test.substring(10, 14);
//                String WyzeMid = test.substring(14, 16);
//                String WyzePid = test.substring(16, 18);
//                String WyzeMac = test.substring(18, 30);
//                String WyzeBind = test.substring(30, 32);
//                String NodeId = test.substring(32, 36);
//                LogUtil.e("WyzeCid:" + WyzeCid + "");
//                LogUtil.e("WyzeMid:" + WyzeMid + "");
//                LogUtil.e("WyzePid:" + WyzePid + "");
//                LogUtil.e("WyzeMac:" + WyzeMac + "");
//                LogUtil.e("WyzeBind:" + WyzeBind + "");
//                LogUtil.e("NodeId:" + NodeId + "");
//                if (WyzeBind.equals("00") && NodeId.equals("0000")) {
//                if (ble.getAddress().equals(SettingUtil.DEVICE_MAC)) {
                bleUtil.stopScan();
                progressFlag = 0;
                connectFlag = 0;
                SettingUtil.SCAN_SUCCESS_TIMES++;
                startTime = System.currentTimeMillis();
                bleUtil.connect(SettingUtil.DEVICE_MAC);

                endsTime = System.currentTimeMillis();
                long aver = endsTime - startsTime;
//                LogUtil.e("startsTime:  " + startsTime);
//                LogUtil.e("endsTime:  " + endsTime);
//                LogUtil.e("aver:  " + aver);
                if (aver > 30000) {
                    aver = 30000;
                }
                if (SettingUtil.AVERAGE_SCAN_TIME == 0) {
                    SettingUtil.ALL_SCAN_TIME = aver;
                } else {
                    SettingUtil.ALL_SCAN_TIME = SettingUtil.ALL_SCAN_TIME + aver;
                }
                SettingUtil.AVERAGE_SCAN_TIME = SettingUtil.ALL_SCAN_TIME / SettingUtil.SCAN_SUCCESS_TIMES;
                if (SettingUtil.MINIMUM_SCAN_TIME == 0) {
                    SettingUtil.MINIMUM_SCAN_TIME = aver;
                } else {
                    if (SettingUtil.MINIMUM_SCAN_TIME > aver) {
                        SettingUtil.MINIMUM_SCAN_TIME = aver;
                    }
                }
                if (SettingUtil.MAXIMUM_SCAN_TIME == 0) {
                    SettingUtil.MAXIMUM_SCAN_TIME = aver;
                } else {
//                long max =  - endTime;
                    if (SettingUtil.MAXIMUM_SCAN_TIME < aver) {
                        SettingUtil.MAXIMUM_SCAN_TIME = aver;
                    }
                }
                SettingUtil.getScanRate();
//                }
//                    int flag = 0;
//                    for (int j = 0; j < bleScanList.size(); j++) {
//                        if (bleScanList.get(j).getMac().equals(ble.getAddress())) {
//                            flag = 1;
//                        }
//                    }
//                    if (flag == 1) {
//                    } else {
//                        if (ble.getName() != null) {
//                            ScanDeviceBean bean = new ScanDeviceBean();
//                            bean.setName(ble.getName());
//                            bean.setChecked(false);
//                            bean.setMac(ble.getAddress());
//                            bean.setRssi(rssi);
//                            bean.setMode(0);
//                            bean.setType(0);
//                            bleScanList.add(bean);
//                            mainAdapter = new MainAdapter(MainActivity.this, bleScanList);
//                            binding.lvDevice.setAdapter(mainAdapter);
//                        }
//                    }
//                }
            }
        }

        @Override
        public void onConnectSuccess() {
            LogUtil.e("onConnectSuccess");
            SettingUtil.CONNECT_SUCCESS++;
            SuccessTime = System.currentTimeMillis();
            endTime = System.currentTimeMillis();
            long aver = endTime - startTime;
            if (aver > 30000) {
                aver = 30000;
            }
            if (SettingUtil.AVERAGE_CONNECT_TIME == 0) {
                SettingUtil.ALL_CONNECT_TIME = aver;
//                SettingUtil.AVERAGE_CONNECT_TIME = aver;
            } else {
                SettingUtil.ALL_CONNECT_TIME = SettingUtil.ALL_CONNECT_TIME + aver;
//                SettingUtil.AVERAGE_CONNECT_TIME = SettingUtil.AVERAGE_CONNECT_TIME / 2;
            }
            SettingUtil.AVERAGE_CONNECT_TIME = SettingUtil.ALL_CONNECT_TIME / SettingUtil.CONNECT_SUCCESS;
            if (SettingUtil.MINIMUM_CONNECT_TIME == 0) {
                SettingUtil.MINIMUM_CONNECT_TIME = aver;
            } else {
//                long min = startTime - endTime;
                if (SettingUtil.MINIMUM_CONNECT_TIME > aver) {
                    SettingUtil.MINIMUM_CONNECT_TIME = aver;
                }
            }
            if (SettingUtil.MAXIMUM_CONNECT_TIME == 0) {
                SettingUtil.MAXIMUM_CONNECT_TIME = aver;
            } else {
//                long max =  - endTime;
                if (SettingUtil.MAXIMUM_CONNECT_TIME < aver) {
                    SettingUtil.MAXIMUM_CONNECT_TIME = aver;
                }
            }
//            getddui
            SettingUtil.getConnRate();
            AppExecutors.getInstance().scheduledExecutor().schedule(new Runnable() {
                @Override
                public void run() {

                    bleUtil.disConnect();
                    dataChange();
                }
            }, 500, TimeUnit.MILLISECONDS);

        }

        @Override
        public void onCharacteristicChanged(byte[] data) {
            if (SettingUtil.STOP == 1) {
                bleUtil.disConnect();
            }


            SettingUtil.SEND_TIMES++;
            sendTimeEnd = System.currentTimeMillis();
            dataChange();
            if (SettingUtil.SEND_1000 == 1) {

                if (sendTimeStart != 0) {
                    SettingUtil.SEND_1000_Time = SettingUtil.SEND_1000_Time + sendTimeEnd - sendTimeStart;
                }
                SettingUtil.SEND_1000_rate = SettingUtil.SEND_1000_Time / SettingUtil.SEND_TIMES;
                if (SettingUtil.SEND_TIMES == 1000) {
                    bleUtil.disConnect();
                    dataChange();
                    return;
                }
            } else if (SettingUtil.SEND_1000 == 0) {
                if (sendTimeStart != 0) {
                    SettingUtil.SEND_1000_Time = SettingUtil.SEND_1000_Time + sendTimeEnd - sendTimeStart;
                }
                SettingUtil.SEND_1000_rate = SettingUtil.SEND_1000_Time / SettingUtil.SEND_TIMES;

                if (SettingUtil.SEND_TIMES == 100000) {
                    bleUtil.disConnect();
                    dataChange();
                    return;
                }
            }


            AppExecutors.getInstance().scheduledExecutor().schedule(new Runnable() {
                @Override
                public void run() {
                    LogUtil.e("getRealLightness : ");
                    if (SettingUtil.SEND_TIMES != 1000) {
                        sendTimeStart = System.currentTimeMillis();
//                                byte[] t = sendUtil.setRealLightness(100);
                        bleUtil.getLightness();
                    }
                }
            }, 300, TimeUnit.MILLISECONDS);
        }

        @Override
        public void onConnectFail() {

            bleUtil.close();
            LogUtil.e("onConnectFail");
            if (SettingUtil.TEST_TYPE == 1) {
                if (SettingUtil.SEND_1000 == 1) {
                    if (SettingUtil.SEND_TIMES != 1000) {
                        sendTimeStart = System.currentTimeMillis();
                        bleUtil.connect(SettingUtil.DEVICE_MAC);
                    } else {
                        dataChange();
                    }
                } else {
                    if (SettingUtil.SEND_TIMES != 100000) {
                        sendTimeStart = System.currentTimeMillis();
                        bleUtil.connect(SettingUtil.DEVICE_MAC);
                    } else {
                        dataChange();
                    }
                }

                return;
            }
            startTime = 0;
            startsTime = 0;

            endTime = 0;
            endsTime = 0;
            SettingUtil.checkConnect();
//            AppExecutors.getInstance().scheduledExecutor().schedule(new Runnable() {
//                @Override
//                public void run() {

                    progressFlag = 2;

//                    startTest();
                    dataChange();
//                }
//            }, 1, TimeUnit.MILLISECONDS);

        }
    };
    public long sendTimeStart = 0;
    public long sendTimeEnd = 0;

    private void dataChange() {
        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                settingAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initClick() {
        binding.lvSetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (msgList.get(i)) {
                    case "手机型号":
//                        viewHolder.tv_control.setText(SettingUtil.PHONE_MODEL);
                        break;
                    case "Android 版本":
//                        viewHolder.tv_control.setText(SettingUtil.PHONE_SYSTEM);
                        break;
                    case "App 版本":
//                        viewHolder.tv_control.setText(SettingUtil.PHONE_SYSTEM);
                        break;
                    case "测试次数":
                        testTimes();
//                        viewHolder.tv_control.setText(SettingUtil.TEST_TIME + "");
                        break;
                    case "信号强度":
//                        viewHolder.tv_control.setText(SettingUtil.TEST_DISTANCE);
                        break;
                    case "测试间隔":
                        testInterval();
//                        viewHolder.tv_control.setText(SettingUtil.TEST_INTERVAL + "s");
                        break;
                    case "选择设备":
                        finish();
//                        viewHolder.tv_control.setText(SettingUtil.CHOOSE_DEVICE);
                        break;
                    case "连接方式":
                        chooseType();
//                        String msg = "";
//                        if (SettingUtil.CONNECT_TYPE == 0) {
//                            msg = "MAC地址连接";
//                        } else {
//                            msg = "扫描连接";
//                        }
//                        viewHolder.tv_control.setText(msg);
                        break;
                    case "开始测试":
                        LogUtil.e("Start Test");
                        startTimes = 0;
                        SettingUtil.TEST_TYPE = 0;
                        SettingUtil.TEST_TIMES_NOW = 0;
                        SettingUtil.CONNECT_SUCCESS = 0;
                        SettingUtil.CONNECT_FAIL = 0;
                        SettingUtil.AVERAGE_CONNECT_TIME = 0;
                        SettingUtil.MAXIMUM_CONNECT_TIME = 0;
                        SettingUtil.MINIMUM_CONNECT_TIME = 0;

                        SettingUtil.MINIMUM_SCAN_TIME = 0;
                        SettingUtil.MAXIMUM_SCAN_TIME = 0;
                        SettingUtil.AVERAGE_SCAN_TIME = 0;
                        SettingUtil.SCAN_SUCCESS_TIMES = 0;
                        SettingUtil.SCAN_FAIL_TIMES = 0;
                        SettingUtil.SCAN_SUCCESS_RATE = 0;
                        SettingUtil.STOP = 0;
                        startTest();
                        dataChange();

                        break;
                    case "停止测试":
                        LogUtil.e("stop Test");
                        SettingUtil.STOP = 1;
                        dataChange();
                        break;

                    case "数据测试 10w包":
                        SettingUtil.STOP = 0;
                        SettingUtil.TEST_TYPE = 1;
                        SettingUtil.SEND_1000 = 0;
                        SettingUtil.SEND_TIMES = 0;
                        sendTimeStart = 0;
                        sendTimeEnd = 0;
                        bleUtil.connect(SettingUtil.DEVICE_MAC);
                        break;
                    case "数据测试 1000包收发速率":
                        SettingUtil.STOP = 0;
                        SettingUtil.TEST_TYPE = 1;
                        SettingUtil.SEND_TIMES_Error = 0;
                        SettingUtil.SEND_1000 = 1;
                        SettingUtil.SEND_TIMES = 0;
                        sendTimeStart = 0;
                        sendTimeEnd = 0;
                        bleUtil.connect(SettingUtil.DEVICE_MAC);
                        break;
                    case "统计结果":
//                viewHolder.tv_control.setText(SettingUtil.);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    private long startsTime = 0;
    private long startTime = 0;
    private long startTimes = 0;
    private long endTime = 0;
    private long endsTime = 0;

    private int cardTimes = 0;
    private int youstop = 0;


    private void startTest() {
        bleUtil.stopScan();
        if (SettingUtil.STOP == 1) {
//            bleUtil.close();
            LogUtil.e("onConnectFail");
            startTime = 0;
            startsTime = 0;

            endTime = 0;
            endsTime = 0;
            SettingUtil.checkConnect();
            return;
        }
        bleUtil.disConnect();
//        bleUtil.close();
        LogUtil.e("SettingUtil.TEST_TIME_NOW:" + SettingUtil.TEST_TIMES_NOW);
        LogUtil.e("SettingUtil.TEST_TIMES:" + SettingUtil.TEST_TIMES);
        if (SettingUtil.TEST_TIMES_NOW != SettingUtil.TEST_TIMES) {
            SettingUtil.TEST_TIMES_NOW++;
            if (SettingUtil.TEST_TIMES_NOW / 100 > 0) {
                if (SettingUtil.TEST_TIMES_NOW > cardTimes) {
                    cardTimes++;
                    SettingUtil.sdCard = SettingUtil.sdCard + "\r\n" + SettingUtil.RESULT;
                    SdCardUtil.writeTxtToSdcard(SettingUtil.DEVICE_MAC, SettingUtil.sdCard);
                }
            }
//            if ( SettingUtil.TEST_TIMES_NOW == )
            AppExecutors.getInstance().scheduledExecutor().schedule(new Runnable() {
                @Override
                public void run() {
//                    startTime = System.currentTimeMillis();
                    startsTime = System.currentTimeMillis();

                    if (SettingUtil.TEST_TIMES_NOW == 1) {
                        startTimes = System.currentTimeMillis();
                    }
                    LogUtil.e("startTimes:" + startTimes);
                    if (SettingUtil.CONNECT_TYPE == 0) {

                        bleUtil.connect(SettingUtil.DEVICE_MAC);
                    } else {

                        bleUtil.startScan();
                        progressFlag = 1;
//                        witeSleep();
                    }
                }
            }, SettingUtil.TEST_INTERVAL, TimeUnit.SECONDS);
            dataChange();
        } else {
            long s = System.currentTimeMillis();
            LogUtil.e("s:" + s);
            LogUtil.e("startTimes:" + startTimes);
            LogUtil.e("startTimes:" + startTimes);
            SettingUtil.TEST_TIME = s - startTimes;
            dataChange();
        }
    }

    long wTime = 0;
    long SuccessTime = 0;
    int progressFlag = 3;
    int connectFlag = 0;
    int scanFlag = 0;
    ScheduledExecutorService mScheduledExecutorService = Executors.newScheduledThreadPool(5);

    private void scheduled() {

        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
//                LogUtil.e("循环任务：" + "scheduleAtFixedRate#first:" + System.currentTimeMillis() / 1000 + "    " + progressFlag);
                switch (progressFlag) {
                    case 0:
                        connectFlag++;
                        if (connectFlag == 30) {
                            connectFlag = 0;
                            LogUtil.e("scheduled:" + progressFlag);
                            startTest();
                        }
                        break;
                    case 1:
                        scanFlag++;
                        if (scanFlag == 15) {
//                            bleUtil.disConnect();

                        } else if (scanFlag == 30) {
                            scanFlag = 0;
                            LogUtil.e("scheduled:" + progressFlag);
                            startTest();
                        }
                        break;
                    case 2:
                        scanFlag = 0;
                        progressFlag = 3;
                        LogUtil.e("scheduled:" + progressFlag);
                        startTest();
                        break;
                    case 3:
                        if (SettingUtil.TEST_TYPE == 1) {
                            if (SettingUtil.SEND_1000 == 1) {
                                if (SettingUtil.SEND_TIMES == 1000) {
                                    return;
                                }
                            }
                            if (Test_Times == SettingUtil.SEND_TIMES) {
                                Test_10++;
                                if (Test_10 == 30) {
                                    Test_10 = 0;
                                    SettingUtil.SEND_TIMES_Error++;

                                    bleUtil.disConnect();


//                                    bleUtil.getLightness();
                                }
                            } else {
                                Test_Times = SettingUtil.SEND_TIMES;
                                Test_10 = 0;
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    public int Test_10 = 0;
    public int Test_Times = 0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScheduledExecutorService.shutdown();

    }
//    private void witeSleep() {
//        LogUtil.e("witeSleep");
////        LogUtil.e("wTime:" + wTime);
////        LogUtil.e("startsTime:" + startsTime);
////        if (wTime == 0){
////
////        }else {
////            if (System.currentTimeMillis() - wTime > 2900) {
////                return;
////            }
////        }
////        1584374640415
////        1584374829018
//        AppExecutors.getInstance().scheduledExecutor().schedule(new Runnable() {
//            @Override
//            public void run() {
////                wTime = startsTime;
////                bleUtil.close();
////                wTime = System.currentTimeMillis();
////                if (wTime - SuccessTime > 30000) {
////                    LogUtil.e("wTime: " + wTime);
////                    LogUtil.e("SuccessTime : " + SuccessTime);
////                    LogUtil.e(">30000");
////
////                } else {
////                    witeSleep();
////                    return;
////                }
//                startTime = 0;
//                startsTime = 0;
//
//                endTime = 0;
//                endsTime = 0;
////                SettingUtil.checkConnect();
////                bleUtil.refreshDeviceCache();
////                AppExecutors.getInstance().scheduledExecutor().schedule(new Runnable() {
////                    @Override
////                    public void run() {
//                        startTest();
////                        witeSleep();
////                    }
////                }, 1000, TimeUnit.MILLISECONDS);
//            }
//        }, 30, TimeUnit.SECONDS);
//    }

    private void testTimes() {
        String title = "testTimes";
        final AlertDialog alert = new AlertDialog.Builder(this).create();
        Objects.requireNonNull(alert.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View view = LayoutInflater.from(this).inflate(R.layout.pop_up_new_network, null);
        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText(title);
        TextView tb_left = view.findViewById(R.id.tb_left);
        TextView tb_right = view.findViewById(R.id.tb_right);
        final EditText edit_text = view.findViewById(R.id.edit_text);
        tb_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        tb_right.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                LogUtil.e("User clicked OK button");
                String namess = "";
                if ("".equals(edit_text.getText().toString())) {
                    Toast.makeText(SettingActivity.this, "间隔时间不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    SettingUtil.TEST_TIMES = Integer.parseInt(edit_text.getText().toString());
                    settingAdapter.notifyDataSetChanged();
                    alert.dismiss();
                }

            }
        });
        alert.setCanceledOnTouchOutside(false);
        alert.setCancelable(false);
        alert.setView(view);
        alert.show();
        alert.getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

    }

    private void chooseType() {
        String title = "Choose Connect Type";
        final AlertDialog alert = new AlertDialog.Builder(this).create();
        Objects.requireNonNull(alert.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View view = LayoutInflater.from(this).inflate(R.layout.choose_type, null);
        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText(title);
        TextView tb_left = view.findViewById(R.id.tb_left);
        TextView tb_right = view.findViewById(R.id.tb_right);
        ListView lv_type = view.findViewById(R.id.lv_type);
        tb_left.setVisibility(View.GONE);
        tb_right.setVisibility(View.GONE);
        ArrayList<String> lists = new ArrayList<>();

        lists.add("MAC地址连接");
        lists.add("扫描连接");
        TypeAdapter adapter = new TypeAdapter(SettingActivity.this, lists);
//                settingAdapter = new Adapter<msgList>( );
        lv_type.setAdapter(adapter);
        lv_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (lists.get(i).equals("MAC地址连接")) {
                    SettingUtil.CONNECT_TYPE = 0;
                } else if (lists.get(i).equals("扫描连接")) {
                    SettingUtil.CONNECT_TYPE = 1;
                }
                alert.dismiss();
                dataChange();
            }
        });
        tb_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        tb_right.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                LogUtil.e("User clicked OK button");
                String namess = "";
//                if ("".equals(edit_text.getText().toString())) {
//                    Toast.makeText(SettingActivity.this, "测试间隔不能为空", Toast.LENGTH_SHORT).show();
//                } else {
//                    SettingUtil.TEST_INTERVAL = Integer.parseInt(edit_text.getText().toString());
//                    settingAdapter.notifyDataSetChanged();
                alert.dismiss();
//                }

            }
        });
        alert.setCanceledOnTouchOutside(false);
        alert.setCancelable(false);
        alert.setView(view);
        alert.show();
        alert.getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

    }

    private void testInterval() {
        String title = "Test Interval";
        final AlertDialog alert = new AlertDialog.Builder(this).create();
        Objects.requireNonNull(alert.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View view = LayoutInflater.from(this).inflate(R.layout.pop_up_new_network, null);
        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText(title);
        TextView tb_left = view.findViewById(R.id.tb_left);
        TextView tb_right = view.findViewById(R.id.tb_right);
        final EditText edit_text = view.findViewById(R.id.edit_text);
        tb_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        tb_right.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                LogUtil.e("User clicked OK button");
                String namess = "";
                if ("".equals(edit_text.getText().toString())) {
                    Toast.makeText(SettingActivity.this, "测试间隔不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    SettingUtil.TEST_INTERVAL = Integer.parseInt(edit_text.getText().toString());
                    settingAdapter.notifyDataSetChanged();
                    alert.dismiss();
                }

            }
        });
        alert.setCanceledOnTouchOutside(false);
        alert.setCancelable(false);
        alert.setView(view);
        alert.show();
        alert.getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

    }


    ArrayList<String> msgList;

    private void initData() {
        bleUtil = BleUtil.getInstance();
        bleUtil.initCallBack(bleCallBackListener);
        msgList = new ArrayList<>();
        msgList.add("手机型号");
        msgList.add("Android 版本");
        msgList.add("APP 版本");
        msgList.add("测试次数");
        msgList.add("信号强度");
        msgList.add("测试间隔");
        msgList.add("选择设备");
        msgList.add("连接方式");
        msgList.add("开始测试");
        msgList.add("停止测试");
        msgList.add("数据测试 10w包");
        msgList.add("数据测试 1000包收发速率");
        msgList.add("统计结果");
        settingAdapter = new SettingAdapter(SettingActivity.this, msgList);
        binding.lvSetting.setAdapter(settingAdapter);
    }


}