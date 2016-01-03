package com.example.add.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.add.R;
import com.example.add.bean.NuoMiBean;
import com.example.add.utils.Utils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myself on 15/8/21.
 */
public class ThreeAdapter extends BaseAdapter {

    private Context mContext;
    //private NuoMiBean bean;
    private List<NuoMiBean.Deals> list;
    private BitmapUtils bitmapUtils;
    private static final int MAX_ANIM = 4;
    private boolean animateItems = true;
    private int lastAnimatedPosition = -1;
    int a = -1;

    public ThreeAdapter(Context mContext, ArrayList<NuoMiBean.Deals> bean1,BitmapUtils bitmapUtils) {
        this.mContext = mContext;
        this.list = bean1;
        this.bitmapUtils = bitmapUtils;
    }

    public void setData(List<NuoMiBean.Deals> data){
        this.list = data;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        a = i;
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder mViewHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.three_item,null);
            mViewHolder = new ViewHolder();
            mViewHolder.iv_item_main = (ImageView) view.findViewById(R.id.iv_three);
            mViewHolder.tv_item_name = (TextView) view.findViewById(R.id.tv_title_three);
            mViewHolder.tv_item_description = (TextView) view.findViewById(R.id.tv_content_three);
            view.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) view.getTag();
        }

        NuoMiBean.Deals deal = list.get(i);
        String title = deal.getTitle();
        String content = deal.getDescription();
        String img = deal.getTiny_image();
        mViewHolder.tv_item_name.setText(title);
        mViewHolder.tv_item_description.setText(content);
        bitmapUtils.display(mViewHolder.iv_item_main,img);
        return view;
    }

    public class ViewHolder {
        public ImageView iv_item_main;
        public TextView tv_item_name;
        public TextView tv_item_description;
    }

    private void runEnterAnimation(View view, int position) {
        if (!animateItems || position >= MAX_ANIM - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(Utils.getHeightPixels(mContext));
            //view.setTranslationY(300);
            view.animate()
                    .translationY(0)
                    .setStartDelay(100 * position)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }
}
