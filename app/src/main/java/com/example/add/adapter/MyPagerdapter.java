package com.example.add.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by myself on 15/8/9.
 */
public class MyPagerdapter extends PagerAdapter {

    private ArrayList<GridView> list;

    public MyPagerdapter(ArrayList<GridView> data) {
        this.list = data;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewGroup parent = (ViewGroup) list.get(position).getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
