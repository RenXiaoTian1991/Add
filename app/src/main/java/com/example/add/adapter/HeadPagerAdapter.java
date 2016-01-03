package com.example.add.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.add.R;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myself on 15/9/6.
 */
public class HeadPagerAdapter extends PagerAdapter {

    private int mChildCount;
    private Context context;
    private BitmapUtils bitmapUtils;
    private ArrayList<View> mDatas;
    private String[] str = new String[]{"http://img5.imgtn.bdimg.com/it/u=345465132,12674375&fm=21&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=2275163573,3277714823&fm=11&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2613454725,1376871092&fm=11&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=3339325381,2938445949&fm=21&gp=0.jpg"};

    public HeadPagerAdapter(ArrayList<View> arr,Context context) {
        this.mDatas = arr;
        bitmapUtils = BitmapUtils.create(context);
        bitmapUtils.configLoadingImage(R.drawable.ic_favorite);
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
        ImageView im = (ImageView) mDatas.get(position);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"敬请期待",Toast.LENGTH_SHORT).show();
            }
        });
        bitmapUtils.display(im,str[position]);
        container.addView(im);
        return im;
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
