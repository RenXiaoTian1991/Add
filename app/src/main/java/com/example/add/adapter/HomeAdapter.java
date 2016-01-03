package com.example.add.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.add.R;

import java.util.ArrayList;

/**
 * Created by myself on 15/8/18.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{
    private Context mContext;
    private ArrayList<String> mDatas;
    private OnItemClickListener listener;

    public void addData(int position){
        mDatas.add(position,"你添加了");
        notifyItemInserted(position);
    }

    public void removeData(int position){
        mDatas.remove(position);
        notifyItemRemoved(position);
    }
    public HomeAdapter(Context mContext, ArrayList<String> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    public interface OnItemClickListener {
        void onItenClick(View view,int position);
    }

    public void setListener(OnItemClickListener listener){
        this.listener = listener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycle_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        holder.tv.setText(mDatas.get(position));
//        if(listener !=null){
//            holder.tv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    listener.onItenClick(holder.tv,position);
//                }
//            });
//        }

    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView tv;

        public MyViewHolder(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_recycle_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(listener !=null){
                listener.onItenClick(view,getPosition());
            }
        }


    }
}
