package com.example.add.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * Created by myself on 15/9/6.
 */
public class DouBanViewpagerAdapter extends PagerAdapter {

    private int mChildCount;
    private Context context;
    private ArrayList<View> mDatas;

    public DouBanViewpagerAdapter(ArrayList<View> arr, Context context) {
        this.mDatas = arr;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        ViewGroup parent = (ViewGroup) mDatas.get(position).getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        container.addView(mDatas.get(position));
        return mDatas.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((mDatas.get(position)));
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        mChildCount = getCount();
    }

//    @Override
//    public int getItemPosition(Object object) {
//        if (mChildCount > 0) {
//            mChildCount--;
//            return POSITION_NONE;
//        }
//        return super.getItemPosition(object);
//    }
}
