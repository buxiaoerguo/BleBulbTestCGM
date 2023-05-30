package com.ljj.blebulbtest.ui.setting;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.ljj.blebulbtest.databinding.ActivityMainBinding;
import com.ljj.blebulbtest.databinding.ActivitySettingBinding;
import com.ljj.blebulbtest.ui.main.MainCallBackListener;

public class SettingViewModel {
    public SettingActivity mContext;
    public SettingCallBackListener listener;
    ActivitySettingBinding binding;
    public SettingViewModel(Application application, SettingActivity mContext, ActivitySettingBinding binding,SettingCallBackListener listener){
        this.mContext = mContext;
        this.binding = binding;
        this.listener = listener;
//        initClick();
    }




    public void cancel(View view){
        mContext.finish();
    }
}
