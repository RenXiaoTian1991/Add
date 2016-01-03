package com.example.add.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.add.R;
import com.example.add.activity.ZhiHuDetailsActivity;
import com.example.add.api.ZhihuApi;
import com.example.add.bean.ZhiHuBean;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;


/**
 * Created by myself on 15/8/31.
 */
public class ZhiHuAdapter extends RecyclerView.Adapter<ZhiHuAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<ZhiHuBean.Stories> mDatas;
    private BitmapUtils bitmapUtils;
    int a;


    public ZhiHuAdapter(Context mContext,ArrayList<ZhiHuBean.Stories> arrayList) {
        this.mContext = mContext;
        this.mDatas = arrayList;
        bitmapUtils = BitmapUtils.create(mContext);
        bitmapUtils.configLoadingImage(R.drawable.renxiaotian);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_daily_info,null));
        return holder;

    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ZhiHuBean.Stories book = mDatas.get(position);
       holder.tv_title.setText(book.title);
        bitmapUtils.display(holder.iv,book.images[0]);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView iv;
        public TextView tv_title;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_cover_zhihu);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title_zhihu);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ZhiHuBean.Stories news = mDatas.get(getPosition());
                    String news_url = ZhihuApi.getNewsContent(news.getId());
                    Intent intent = new Intent(mContext, ZhiHuDetailsActivity.class);
                    intent.putExtra("title", news.title);
                    intent.putExtra("url", news_url);
                    mContext.startActivity(intent);
                }
            });
        }

    }
}
