package com.ljj.blebulbtest.ui.main;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.ljj.blebulbtest.R;
import com.ljj.blebulbtest.databinding.ActivityMainBinding;
import com.ljj.blebulbtest.ui.setting.SettingActivity;
import com.ljj.blebulbtest.util.LogUtil;

public class MainViewModel {
    public Context mContext;
    public MainCallBackListener listener;
    ActivityMainBinding binding;
    public MainViewModel(Application application, Activity mContext,ActivityMainBinding binding,MainCallBackListener mainCallBackListener){
        this.mContext = mContext;
        this.binding = binding;
        this.listener = mainCallBackListener;

        lvDeviceItemClick();
    }

    private void lvDeviceItemClick() {
        binding.lvDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LogUtil.e("i:" +i);

//                binding.tvScan.setText(mContext.getResources().getString(R.string.start_scan));
                listener.chooseDevice(i);
            }
        });
    }

    public void gotoSetting(View view){

        Intent intent = new Intent(mContext, SettingActivity.class);
        mContext.startActivity(intent);

    }

    public void chooseDevice(View view){

    }
    public void startScan(View view){
        if (binding.tvScan.getText().equals(mContext.getResources().getString(R.string.start_scan))) {
            binding.tvScan.setText(mContext.getResources().getString(R.string.stop_scan));
            listener.scan();
        }else {
            binding.tvScan.setText(mContext.getResources().getString(R.string.start_scan));
            listener.stopScan();
        }
    }
}
