package com.example.add.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.add.R;
import com.example.add.bean.Book;
import com.example.add.utils.Utils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;


/**
 * Created by myself on 15/8/31.
 */
public class DoubanAdapter extends RecyclerView.Adapter<DoubanAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Book> mDatas;
    private BitmapUtils bitmapUtils;
    private static final int MAX_ANIM = 4;
    private boolean animateItems = true;
    private int lastAnimatedPosition = -1;


    public DoubanAdapter(Context mContext,ArrayList<Book> arrayList) {
        this.mContext = mContext;
        this.mDatas = arrayList;
        bitmapUtils = BitmapUtils.create(mContext);
        bitmapUtils.configLoadingImage(R.drawable.menu_header);
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
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.douban_item,null));
        return holder;

    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        runEnterAnimation(holder.itemView, position);
        Book book = mDatas.get(position);
        holder.tv_title.setText(book.getTitle());
        String desc = "作者: " + book.getAuthor()[0] + "\n副标题: " + book.getSubtitle()
                + "\n出版年: " + book.getPubdate() + "\n页数: " + book.getPages() + "\n定价:" + book.getPrice();
        holder.tv_content.setText(desc);
        bitmapUtils.display(holder.iv,book.getImage());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView iv;
        public TextView tv_title;
        public TextView tv_content;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_douban);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title_douban);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content_douban);
        }
    }
}
