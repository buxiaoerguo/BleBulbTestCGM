package com.ljj.blebulbtest.util.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ljj.blebulbtest.R;
import com.ljj.blebulbtest.util.SettingUtil;

import java.util.ArrayList;



public class TypeAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> msgList;

    public TypeAdapter(Context context, ArrayList<String> msgList) {
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
        viewHolder.tv_msg.setVisibility(View.GONE);
        viewHolder.tv_control.setVisibility(View.GONE);
        return rowView;
    }


}