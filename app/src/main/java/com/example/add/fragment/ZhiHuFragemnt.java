package com.example.add.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaCryptoException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.add.R;
import com.example.add.adapter.ZhiHuAdapter;
import com.example.add.api.ZhihuApi;
import com.example.add.bean.ZhiHuBean;
import com.example.add.enginge.DividerItemDecoration;
import com.example.add.utils.WeakHandler;
import com.example.add.view.LoadMoreRecyclerView;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by myself on 15/9/6.
 */
public class ZhiHuFragemnt extends Fragment{

    private LoadMoreRecyclerView recyclerView;
    private long currentTime;
    SimpleDateFormat format;
    private HttpUtils utils;
    private ArrayList<ZhiHuBean.Stories>  mDatas;
    private ArrayList<ZhiHuBean.Stories> arr;
    private Gson gson;
    private ZhiHuAdapter adapter;
    private ImageView iv_loading;
    private RelativeLayout rl_loading;
    private int getType = 0;
    private WeakHandler handler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 0:
                    mDatas.addAll(arr);
                    break;
                case 1:
                    mDatas.addAll(arr);
                    break;
                case 2:

                    break;
            }
            if(adapter == null){
                adapter = new ZhiHuAdapter(getActivity(),mDatas);
                recyclerView.setAdapter(adapter);
            }else{
                adapter.notifyDataSetChanged();
            }
            return false;
        }
    });
    public static ZhiHuFragemnt newInstance(String argument){
        Bundle bundle = new Bundle();
        ZhiHuFragemnt zhiHuFragemnt = new ZhiHuFragemnt();
        if(argument !=null){
            bundle.putString("argument",argument);
            zhiHuFragemnt.setArguments(bundle);
        }
        return zhiHuFragemnt;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utils = new HttpUtils();
        currentTime = System.currentTimeMillis();
        Date date = new Date(currentTime);
        format = new SimpleDateFormat("yyyyMMdd");
        mDatas = new ArrayList<ZhiHuBean.Stories>();
        String str = format.format(date);
        getData(str);
    }

    private void getData(String str){
        utils.send(HttpRequest.HttpMethod.GET, ZhihuApi.getDailyNews(str), new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                fillData(responseInfo);
                Log.i("abc",responseInfo.result.toString());
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }
    private void fillData(ResponseInfo<Object> responseInfo) {
          stopAnim();
          recyclerView.setVisibility(View.VISIBLE);
          ZhiHuBean bean = jsonToBean(responseInfo.result.toString(), ZhiHuBean.class);
          arr = bean.stories;
          handler.sendEmptyMessage(getType);
    }

    private ZhiHuBean jsonToBean(String result, Class<ZhiHuBean> booksClass) {
        gson = new Gson();
        ZhiHuBean books = gson.fromJson(result, ZhiHuBean.class);
        return books;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zhihufragment_layout, null);
        recyclerView = (LoadMoreRecyclerView) view.findViewById(R.id.recy_zhihu);
        iv_loading = (ImageView) view.findViewById(R.id.iv_loading_zhihu);
        rl_loading = (RelativeLayout) view.findViewById(R.id.rl_loading_zhihu);
        showLoading();
        initRecycle();
        recyclerView.setOnLoadMoreListener(new LoadMoreRecyclerView.onLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getType = 1;
                currentTime = (currentTime - 60*60*24*1000);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(currentTime);
                Date date = cal.getTime();
                String str1 = format.format(date);
                Toast.makeText(getActivity(),"正在请求"+str1+"的数据",Toast.LENGTH_SHORT).show();
                getData(str1);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initRecycle() {
        LinearLayoutManager llMag = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(llMag);
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity().getApplicationContext(),DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
        rl_loading.setVisibility(View.GONE);
    }
}
