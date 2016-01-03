package com.example.add.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.add.R;
import com.example.add.adapter.GridAdapter;
import com.example.add.adapter.MyAdapter;
import com.example.add.adapter.MyPagerdapter;
import com.example.add.db.DBHelper;
import com.example.add.entity.UserEntity;
import com.example.add.utils.WeakHandler;

import java.util.ArrayList;

/**
 * Created by myself on 15/8/7.
 */
public class MyFragment extends Fragment implements View.OnClickListener{

    private ViewPager pager;
    private ArrayList<GridView> list;
    private GridView gridView,gridView2;
    private Button btn_create,btn_select,btn_delete;
    private MyAdapter listAdapter;
    private DBHelper heplper;
    private ListView listView;
    private ArrayList<UserEntity> entities;
    private RelativeLayout rl_load,rl_content;

    private Context mContext = getActivity();
    private WeakHandler handler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            rl_load.setVisibility(View.GONE);
            rl_content.setVisibility(View.VISIBLE);
            return false;
        }
    });
    MyPagerdapter adapter;
    ArrayList<UserEntity> arr;
    GridAdapter adapter2;
    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("MyFragment","onCreateView");
        View view = inflater.inflate(R.layout.fragment, null);


        pager = (ViewPager) view.findViewById(R.id.viewpager);
        //gridview相关
        View view2 = inflater.inflate(R.layout.gridview,null);
        gridView = (GridView) view2.findViewById(R.id.gridview);
        View view3 = inflater.inflate(R.layout.gridview,null);
        gridView2 = (GridView) view3.findViewById(R.id.gridview);
        rl_load = (RelativeLayout) view.findViewById(R.id.rl_loading_three);
        rl_content = (RelativeLayout) view.findViewById(R.id.rl_content_one);
        handler.sendEmptyMessageDelayed(0,3000);
        initBtn(view);
        initGrid();
        initPager();
        return view;
    }



    private void initBtn(View view) {
        btn_create = (Button) view.findViewById(R.id.btn_creat);
        btn_delete = (Button) view.findViewById(R.id.btn_delete);
        btn_select = (Button) view.findViewById(R.id.btn_select);
        listView = (ListView) view.findViewById(R.id.list);

        btn_select.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_create.setOnClickListener(this);

    }


    private void initPager() {
         adapter = new MyPagerdapter(list);
        pager.setAdapter(adapter);
    }

    private void initGrid() {
        adapter2 = new GridAdapter(getActivity());
        gridView.setAdapter(adapter2);
        gridView2.setAdapter(adapter2);
        list.add(gridView);
        list.add(gridView2);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("MyFragment", "onCreate");
        super.onCreate(savedInstanceState);
        list = new ArrayList<GridView>();
        heplper = DBHelper.getInstance(getActivity());
        entities = new ArrayList<UserEntity>();

        for(int i=0;i<10;i++){
            UserEntity entity = new UserEntity();
            entity.setName("张三"+i);
            entity.setAddress("南京");
            entity.setAge(i);
            entity.setSex("男");
            entities.add(entity);
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("MyFragment", "onDestroy");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("MyFragment", "onDetach");
    }

    @Override
    public void onStop() {
        super.onStop();
        list.clear();
        adapter.notifyDataSetChanged();
        Log.i("MyFragment", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("MyFragment", "onDestroyView");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("MyFragment", "onPause");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_creat:
                boolean bol = heplper.insertAll(entities);
                Log.i("abc",bol+"");
                break;
            case R.id.btn_select:
                if( heplper.search(UserEntity.class)!=null){
                    arr = (ArrayList<UserEntity>) heplper.search(UserEntity.class);
                    Log.i("abc","对的");
                    listAdapter = new MyAdapter(getActivity(),arr);
                    listView.setAdapter(listAdapter);
                }else{
                    Log.i("abc","cuole");
                }

                break;
            case R.id.btn_delete:
                heplper.deleteAll(UserEntity.class);
                break;
        }
    }




}
