package com.example.add.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.add.R;
import com.example.add.bean.Bean;

import java.util.ArrayList;

/**
 * Created by myself on 15/8/17.
 */
public class PopAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Bean> arr;

    public PopAdapter(Context context, ArrayList<Bean> arr) {
        this.context = context;
        this.arr = arr;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int i) {
        return arr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int postion, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.pop_item, null);
            mViewHolder = new ViewHolder();
            mViewHolder.tv_popup_item = (TextView) convertView.findViewById(R.id.tv_pop_item);
            mViewHolder.cb_popup_item = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        Bean bean = arr.get(postion);
        mViewHolder.tv_popup_item.setText(bean.getTitle());
        mViewHolder.cb_popup_item.setChecked(bean.isChecked());
        return convertView;
    }

    public class ViewHolder {
        public TextView tv_popup_item;
        public CheckBox cb_popup_item;
    }
}
