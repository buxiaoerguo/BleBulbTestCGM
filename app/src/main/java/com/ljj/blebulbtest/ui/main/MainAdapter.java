package com.ljj.blebulbtest.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ljj.blebulbtest.R;
import com.ljj.blebulbtest.ui.ScanDeviceBean;

import java.util.ArrayList;

public class MainAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<ScanDeviceBean> deviceList;

    public MainAdapter(Context context, ArrayList<ScanDeviceBean> deviceList) {
        this.deviceList = deviceList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (deviceList != null) {

            return deviceList.size();
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
        TextView tv_uuid;
        TextView tv_msg;
        TextView tv_versoin;
        TextView tv_type;
//        TextView tv_all;
        RelativeLayout ll_type;
        LinearLayout ll_all;
        TextView sCheckBox;
    }

    @Override
    public View getView(final int position, View rowView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        ViewHolder viewHolder = null;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.add_device_item, null);
            viewHolder = new ViewHolder();

            viewHolder.ll_type = rowView.findViewById(R.id.ll_type);
            viewHolder.tv_versoin = rowView.findViewById(R.id.tv_version);
            viewHolder.tv_type = rowView.findViewById(R.id.tv_type);
            viewHolder.ll_all = rowView.findViewById(R.id.ll_all);
            viewHolder.tv_name = rowView.findViewById(R.id.tv_name);
            viewHolder.tv_uuid = rowView.findViewById(R.id.tv_uuid);
            viewHolder.tv_msg = rowView.findViewById(R.id.tv_msg);
            viewHolder.sCheckBox = rowView.findViewById(R.id.tv_choose);


            rowView.setTag(viewHolder);
        } else {
            view = rowView;
            viewHolder = (ViewHolder) view.getTag();
        }
//        viewHolder.tv_all.setVisibility(View.INVISIBLE);
//        if (deviceList.get(position).getType() == 0) {
//            viewHolder.tv_type.setText(getString(R.string.Add_type_bulb));
//        } else {
//            viewHolder.tv_type.setText(getString(R.string.Add_type_gateWay));
//        }
        if (position == 0) {
            viewHolder.ll_type.setVisibility(View.VISIBLE);
        } else {
            if (deviceList.get(position - 1).getType() == deviceList.get(position).getType()) {
                viewHolder.ll_type.setVisibility(View.GONE);
            } else {
                viewHolder.ll_type.setVisibility(View.VISIBLE);
            }
        }
//        viewHolder.sCheckBox.setChecked(deviceList.get(position).isChecked());

        if (deviceList.get(position).getRssi() < -70) {
            viewHolder.ll_all.setVisibility(View.GONE);
//            viewHolder.tv_msg.setTextColor(Color.rgb(220,20,60));
//        }else if (deviceList.get(position).getRssi() < -70){
                viewHolder.tv_msg.setTextColor(Color.RED);
//                viewHolder.tv_msg.setBackgroundColor(Color.RED);
        }else {
            viewHolder.tv_msg.setTextColor(Color.BLACK);
//            viewHolder.tv_msg.setBackgroundColor(Color.WHITE);
        }
        if (deviceList.get(position).getName() .equals("000000000000") ||deviceList.get(position).getName() .equals("ffffffffffff")  ){
            viewHolder.tv_name.setTextColor(Color.RED);
//            viewHolder.tv_msg.setBackgroundColor(Color.RED);
        }else {
            viewHolder.tv_name.setTextColor(Color.BLACK);
//            viewHolder.tv_msg.setBackgroundColor(Color.WHITE);
        }
//        viewHolder.tv_name.setText("Mac："+deviceList.get(position).getName());
        viewHolder.tv_name.setText("Mac："+deviceList.get(position).getMac());
        viewHolder.tv_uuid.setText("Name："+deviceList.get(position).getBroName());
//        viewHolder.tv_msg.setText(deviceList.get(position).getDeviceType()+"   " + deviceList.get(position).getVersion() +"   " + deviceList.get(position).getRssi());
        viewHolder.tv_type.setText( "Version："+ deviceList.get(position).getDeviceType());
        viewHolder.tv_versoin.setText(  " "+deviceList.get(position).getVersion());
        viewHolder.tv_msg.setText( "Rssi:" + deviceList.get(position).getRssi());
//        viewHolder.tv_uuid.setText(deviceList.get(position).getMac());
//        viewHolder.tv_uuid.setVisibility(View.GONE);
        ViewHolder finalViewHolder = viewHolder;
//        viewHolder.tv_all.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String type = String.valueOf(deviceList.get(position).getType());
//                byte[] datas = new byte[1];
//                datas[0] = 0;
////                BroadCastManager.getInstance().bleDeviceBroadCast(mContext, type, type, 2, datas);
//            }
//        });
//        viewHolder.ll_all.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String check = "";
//                if (finalViewHolder.sCheckBox.isChecked()) {
//                    finalViewHolder.sCheckBox.setChecked(false, true);
//                    check = "false";
//                } else {
//                    finalViewHolder.sCheckBox.setChecked(true, true);
//                    check = "true";
//                }
//                deviceList.get(position).setChecked(finalViewHolder.sCheckBox.isChecked());
//                String pos = String.valueOf(position);
//                byte[] datas = new byte[1];
//                datas[0] = 0;
//                BroadCastManager.getInstance().bleDeviceBroadCast(mContext, check, pos, 1, datas);
//            }
//        });
//        viewHolder.sCheckBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String check = "";
//                if (finalViewHolder.sCheckBox.isChecked()) {
//                    finalViewHolder.sCheckBox.setChecked(false, true);
//                    check = "false";
//                } else {
//                    finalViewHolder.sCheckBox.setChecked(true, true);
//                    check = "true";
//                }
//                deviceList.get(position).setChecked(finalViewHolder.sCheckBox.isChecked());
//                String pos = String.valueOf(position);
//                byte[] datas = new byte[1];
//                datas[0] = 0;
//                BroadCastManager.getInstance().bleDeviceBroadCast(mContext, check, pos, 1, datas);
//            }
//        });
        return rowView;
    }


}