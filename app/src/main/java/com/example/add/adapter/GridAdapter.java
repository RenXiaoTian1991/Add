package com.example.add.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.add.R;


/**
 * Created by myself on 15/8/9.
 */
public class GridAdapter extends BaseAdapter {
    private Context context;
    private Integer[] imgs = {
            R.mipmap.btn_down, R.mipmap.btn_down,
            R.mipmap.btn_down, R.mipmap.btn_down,
            R.mipmap.btn_online,
            R.mipmap.btn_online,
            R.mipmap.btn_online,
            R.mipmap.btn_online,
    };
    public GridAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public Object getItem(int i) {
        return imgs[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view ==null){
             holder = new ViewHolder();
            view =LayoutInflater.from(context).inflate(R.layout.gridview_item,null);
            ImageView image = (ImageView) view.findViewById(R.id.grid_item);
            holder.view = image;
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();

        }
        holder.view.setImageResource(imgs[i]);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"你点击了第"+i,Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public static class ViewHolder{
        public ImageView view;
    }
}
