package com.example.add.fragment;

import android.graphics.drawable.AnimationDrawable;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.add.R;
import com.example.add.adapter.DoubanAdapter;
import com.example.add.bean.Book;
import com.example.add.bean.Books;
import com.example.add.enginge.DividerItemDecoration;
import com.example.add.utils.WeakHandler;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * Created by myself on 15/8/31.
 */
public class DouBanFragment extends Fragment {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private DoubanAdapter adapter;
    private static final String BASE_URL = "https://api.douban.com/v2/";
    private String url = BASE_URL+"book/search";
    private HttpUtils utils;
    private String name;
    private Gson gson;
    private BitmapUtils bitmapUtils;
    private Boolean isRefresh = false;
    private ArrayList<Book>  refreshData;
    private ArrayList<Book>  mDatas;
    private RelativeLayout rl_loading;
    private RelativeLayout rl_content;
    private ImageView iv_loading;
    private WeakHandler handler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 0:
                    refreshLayout.setRefreshing(false);
                    mDatas.addAll(0, refreshData);
                    adapter.notifyDataSetChanged();
                    isRefresh = false;
                    break;
            }
            return false;
        }
    });

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void fillData(ResponseInfo<Object> result,String url){
        rl_loading.setVisibility(View.GONE);
        rl_content.setVisibility(View.VISIBLE);
        stopAnim();
        if(url.equals(url+"?q="+"花千骨"+"&start=0&count=20")){
            Books books = jsonToBean((String) result.result, Books.class);
            refreshData = books.getBooks();
            if(books == null){
                return;
            }
            handler.sendEmptyMessage(0);
            return;
        }
        Books books = jsonToBean((String) result.result, Books.class);
        mDatas = books.getBooks();
        adapter = new DoubanAdapter(getActivity(),mDatas);
        recyclerView.setAdapter(adapter);

    }

    private Books jsonToBean(String result, Class<Books> booksClass) {
        gson = new Gson();
        Books books = gson.fromJson(result, Books.class);
        return books;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_douban, null);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_douban);
        recyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview_douban);
        rl_loading = (RelativeLayout) view.findViewById(R.id.rl_loading_douban);
        iv_loading = (ImageView) view.findViewById(R.id.iv_loading_douban);
        rl_content = (RelativeLayout) view.findViewById(R.id.douban_content);
        showLoading();
        initRecycle();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                reFreshData();
            }
        });


        return view;
    }

    private void reFreshData() {
        final String urlNow1 = url+"?q="+"花千骨"+"&start=0&count=20";
        utils.send(HttpRequest.HttpMethod.GET, urlNow1, new RequestCallBack<Object>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.i("abcd",urlNow1);
            }

            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                Log.i("abcd", (String) responseInfo.result);
                fillData(responseInfo,urlNow1);
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utils = new HttpUtils(6*1000);
        bitmapUtils = BitmapUtils.create(getActivity());
        bitmapUtils.configLoadingImage(R.mipmap.ic_launcher);
        mDatas = new ArrayList<Book>();
        name = "中国";
        getData(name);
    }

    public DouBanFragment() {
    }

    private void initRecycle() {
        LinearLayoutManager llMag = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(llMag);
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity().getApplicationContext(),DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    private void getData(String s) {
        final String urlNow = url+"?q="+s+"&start=20&count=10";
        utils.send(HttpRequest.HttpMethod.GET, urlNow, new RequestCallBack<Object>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                fillData(responseInfo,urlNow);
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }

    private void showLoading() {
        iv_loading.setImageResource(R.drawable.anim_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) iv_loading.getDrawable();
        animationDrawable.start();
    }

    private void stopAnim(){
        if(iv_loading.getDrawable() instanceof AnimationDrawable){
            AnimationDrawable animationDrawable = (AnimationDrawable) iv_loading.getDrawable();
            animationDrawable.stop();
        }
    }

}
