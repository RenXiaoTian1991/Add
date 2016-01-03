package com.example.add.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Gallery;
import android.widget.TextView;


import com.example.add.R;
import com.example.add.bean.Bean;

import java.util.ArrayList;


/**
 * Created by myself on 15/8/17.
 */
public class GalleryAdapter extends BaseAdapter {
    private ArrayList<Bean> arr;
    private Context context;
    public GalleryAdapter(ArrayList<Bean> arr,Context context) {
        this.arr = arr;
        this.context = context;
    }

    public void setData(ArrayList<Bean> arr){
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(context).inflate(R.layout.gal_item,null);
        TextView tv = (TextView) v.findViewById(R.id.tv_txt);
        if(arr.get(i).isChecked()==true){
            tv.setText(arr.get(i).getTitle());
        }
//        TextView tv = new TextView(context);
//        tv.setText(arr.get(i));
//        tv.setTextColor(Color.parseColor("#000000"));
//        tv.setMinWidth(100);
//        tv.setLayoutParams(new Gallery.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return v;
    }
}
