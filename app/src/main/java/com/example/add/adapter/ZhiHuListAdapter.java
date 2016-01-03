package com.example.add.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.add.R;
import com.example.add.bean.ZhiHuBean;
import com.lidroid.xutils.BitmapUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by myself on 15/9/6.
 */
public class ZhiHuListAdapter extends BaseAdapter{
    private Context mContext;
    private List<ZhiHuBean.Stories> list;
    private BitmapUtils bitmapUtils;
    int a = -1;

    private static final int TYPE_SIZE = 2;
    private static final int TYPE_DATE = 0;
    private static final int TYPE_INFO = 1;

    public ZhiHuListAdapter(Context mContext, ArrayList<ZhiHuBean.Stories> bean1,BitmapUtils bitmapUtils) {
        this.mContext = mContext;
        this.list = bean1;
        this.bitmapUtils = bitmapUtils;
    }

    public void setData(List<ZhiHuBean.Stories> data){
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
    public int getItemViewType(int position) {
        if(list.get(position).getTime()!=null){
            return TYPE_DATE;
        }else{
            return TYPE_INFO;
        }
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_SIZE;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder mViewHolder = null;
        ZhiHuBean.Stories deal = list.get(i);
        int type = getItemViewType(i);

            if (view == null) {
                mViewHolder = new ViewHolder();

                switch (type){
                    case TYPE_DATE:
                    view = LayoutInflater.from(mContext).inflate(R.layout.zhihulist_2,null);
                    mViewHolder.tv_item_title = (TextView) view.findViewById(R.id.text_zhihu_2);
                    view.setTag(mViewHolder);
                        break;
                    case TYPE_INFO:
                    view = LayoutInflater.from(mContext).inflate(R.layout.zhihulist_item,null);
                    mViewHolder.iv_item_main = (ImageView) view.findViewById(R.id.iv_three_zhihu);
                    mViewHolder.tv_item_title = (TextView) view.findViewById(R.id.tv_title_three_zhihu);
                    view.setTag(mViewHolder);
                        break;
                }

            } else {
                    mViewHolder = (ViewHolder) view.getTag();
            }


        switch (type){
            case TYPE_DATE:
                Date tmpDate = null;
                if(i== 0){
                    mViewHolder.tv_item_title.setText(deal.getTime());
                }else{
                    try {
                        tmpDate = new SimpleDateFormat("yyyyMMdd").parse(deal.getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String mData = DateFormat.getDateInstance(DateFormat.FULL).format(tmpDate);
                    String thisYear = Calendar.getInstance().get(Calendar.YEAR) + "年";
                    if (mData.startsWith(thisYear)) {
                        mData.replace(thisYear, "");
                    }
                    mViewHolder.tv_item_title.setText(mData);
                }

                break;
            case TYPE_INFO:
                mViewHolder.tv_item_title.setText(deal.getTitle());
                bitmapUtils.display(mViewHolder.iv_item_main, deal.images[0]);
                break;
        }
            return view;
    }

    public class ViewHolder {
        public ImageView iv_item_main;
        public TextView tv_item_title;
    }

}

