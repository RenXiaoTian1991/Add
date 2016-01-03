package com.example.add.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.add.R;
import com.example.add.entity.UserEntity;
import java.util.List;


/**
 * 包名：com.example.DBAppDemo.adapter
 * 描述：
 * User 张伟
 * Date 2015/7/17 0017.
 * Time 上午 11:22.
 * 修改日期：
 * 修改内容：
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<UserEntity> userEntities;
    private LayoutInflater layoutInflater;
    public MyAdapter(Context context, List<UserEntity> userEntities){
        this.context = context;
        this.userEntities = userEntities;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setUserEntities(List<UserEntity> userEntities) {
        this.userEntities = userEntities;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return userEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return userEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder = null;
        if(convertView==null){
            convertView =layoutInflater.inflate(R.layout.item_db,parent,false);
            viewholder = new Viewholder(convertView);
            convertView.setTag(viewholder);
        }else{
            viewholder = (Viewholder) convertView.getTag();
        }
        UserEntity entity = userEntities.get(position);
        viewholder.tv_id.setText(entity.getId()+"");
        viewholder.tv_name.setText(entity.getName());
        viewholder.tv_address.setText(entity.getAddress());
        viewholder.tv_arg.setText(entity.getAge()+"");
        viewholder.tv_sex.setText(entity.getSex());
        return convertView;
    }

    class Viewholder{
        public TextView tv_id;
        public TextView tv_name;
        public TextView tv_address;
        public TextView tv_arg;
        public TextView tv_sex;
        public Viewholder(View view){
            tv_id = (TextView) view.findViewById(R.id.tv_id);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_address = (TextView) view.findViewById(R.id.tv_address);
            tv_arg = (TextView) view.findViewById(R.id.tv_arg);
            tv_sex = (TextView) view.findViewById(R.id.tv_sex);
        }
    }

}
