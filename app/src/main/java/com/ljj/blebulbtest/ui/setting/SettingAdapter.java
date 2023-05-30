package com.ljj.blebulbtest.ui.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ljj.blebulbtest.R;
import com.ljj.blebulbtest.util.BaseUtil;
import com.ljj.blebulbtest.util.SettingUtil;

import java.util.ArrayList;


public class SettingAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> msgList;

    public SettingAdapter(Context context, ArrayList<String> msgList) {
        this.msgList = msgList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (msgList != null) {
            return msgList.size();
        } else {
            return 0;
        }

    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView tv_name;
        TextView tv_control;
        TextView tv_msg;
    }

    @Override
    public View getView(final int position, View rowView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        ViewHolder viewHolder = null;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.setting_item, null);
            viewHolder = new ViewHolder();

            viewHolder.tv_name = rowView.findViewById(R.id.tv_name);
            viewHolder.tv_control = rowView.findViewById(R.id.tv_control);
            viewHolder.tv_msg = rowView.findViewById(R.id.tv_msg);


            rowView.setTag(viewHolder);
        } else {
            view = rowView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_name.setText(msgList.get(position));
        if (msgList.get(position).equals("统计结果")){
            viewHolder.tv_msg.setVisibility(View.VISIBLE);
        }else {
            viewHolder.tv_msg.setVisibility(View.GONE);
        }
        switch (msgList.get(position)) {
            case "手机型号":
                viewHolder.tv_control.setText(SettingUtil.PHONE_MODEL);
                break;
            case "Android 版本":
                viewHolder.tv_control.setText(SettingUtil.PHONE_SYSTEM);
                break;
            case "APP 版本":
                viewHolder.tv_control.setText(BaseUtil.TITLE);
                break;
            case "测试次数":
                viewHolder.tv_control.setText(SettingUtil.TEST_TIMES +"");
                break;
            case "信号强度":
                viewHolder.tv_control.setText(SettingUtil.RSSI + "");
                break;
            case "测试间隔":
                viewHolder.tv_control.setText(SettingUtil.TEST_INTERVAL + "s");
                break;
            case "选择设备":
                viewHolder.tv_control.setText(SettingUtil.DEVICE_MAC);
                break;
            case "数据测试 10w包" :
                viewHolder.tv_control.setText(" ");
                break;
            case "数据测试 1000包收发速率" :
                viewHolder.tv_control.setText(" ");
                break;
            case "连接方式":
                String msg = "";
                if (SettingUtil.CONNECT_TYPE == 0){
                    msg = "MAC地址连接";
                }else {
                    msg = "扫描连接";
                }
                viewHolder.tv_control.setText(msg);
                break;
            case "开始测试":
//                LogUtil.e("Start Test");
//                viewHolder.tv_control.setText(SettingUtil.);
                viewHolder.tv_control.setText("");
                break;
            case "停止测试":
//                LogUtil.e("stop Test");
                viewHolder.tv_control.setText("");
//                viewHolder.tv_control.setText(SettingUtil.);
                break;
            case "统计结果":
                viewHolder.tv_control.setText("");
                SettingUtil.checkResult();
                viewHolder.tv_msg.setText(SettingUtil.RESULT);
//                viewHolder.tv_control.setText(SettingUtil.);
                break;

            default:
                break;
        }
        return rowView;
    }


}