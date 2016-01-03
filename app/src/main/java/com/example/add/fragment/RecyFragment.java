package com.example.add.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.add.R;
import com.example.add.adapter.HomeAdapter;
import com.example.add.enginge.DividerItemDecoration;
import com.example.add.utils.WeakHandler;

import java.util.ArrayList;

/**
 * Created by myself on 15/8/28.
 */
public class RecyFragment extends Fragment implements View.OnClickListener,HomeAdapter.OnItemClickListener{
    private View view;
    private RecyclerView recyclerView;
    private Button remove,add;
    private ArrayList<String> mDatas;
    private HomeAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isRefreshing = false;
    private boolean isLoadMore = false;
    private LinearLayoutManager llMag;
    private WeakHandler handler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch(message.what){
                case 2:
                    isLoadMore = false;
                    ArrayList<String> arr1 = new ArrayList<String>();
                    for (int a = 20;a<50;a++){
                        String str = "我是"+a;
                        arr1.add(str);
                    }
                    mDatas.addAll(arr1);
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
                    swipeRefreshLayout.setRefreshing(false);
                    ArrayList<String> arr = new ArrayList<String>();
                    for (int a = 20;a<50;a++){
                        String str = "我是"+a;
                        arr.add(str);
                    }
                    mDatas.addAll(0,arr);
                    adapter.notifyDataSetChanged();
                    isRefreshing = false;
                    break;
            }
            return false;
        }
    });
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatas = new ArrayList<String>();
        for(int i =0;i<20;i++){
            mDatas.add("我是"+i);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_recycle,null);
        findViews();
        remove.setOnClickListener(this);
        add.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!isRefreshing){
                    handler.sendEmptyMessageDelayed(1,2000);
                    isRefreshing = true;
                }
            }
        });
        initRecycle();
        return view;
    }


    private void initRecycle() {
        llMag = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(llMag);
        adapter = new HomeAdapter(getActivity().getApplicationContext(),mDatas);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity().getApplicationContext(),DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setListener(this);
        recyclerView.setOnScrollListener(new MyScrollListener());
    }

    @Override
    public void onItenClick(View view, int position) {
        Toast.makeText(getActivity(), "你点击了" + position, Toast.LENGTH_SHORT).show();
    }

    private void findViews() {
        recyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
        remove = (Button) view.findViewById(R.id.add_recy);
        add = (Button) view.findViewById(R.id.remove_recy);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_kongjian);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_recy:
                adapter.addData(1);
                break;
            case R.id.remove_recy:
                adapter.removeData(1);
                break;
        }
    }

    private class MyScrollListener extends RecyclerView.OnScrollListener{
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int lastVisibleCount = llMag.findLastVisibleItemPosition();

            if(lastVisibleCount >= llMag.getItemCount()-1 && dy>0){
                if(!isLoadMore){
                    isLoadMore = true;
                    handler.sendEmptyMessageDelayed(2,2000);
                }
            }
            super.onScrolled(recyclerView, dx, dy);
        }
    }
}
