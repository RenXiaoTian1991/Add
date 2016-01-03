package com.example.add.fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.add.R;
import com.example.add.activity.RecycleActivity;
import com.example.add.adapter.HeadPagerAdapter;
import com.example.add.adapter.HomeAdapter;
import com.example.add.adapter.ThreeAdapter;
import com.example.add.bean.NuoMiBean;
import com.example.add.enginge.ConstantUrl;
import com.example.add.utils.Utils;
import com.example.add.utils.WeakHandler;
import com.example.add.view.MyViewPager;
import com.example.add.view.PageIndicator;
import com.example.add.view.XListView;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by myself on 15/8/21.
 */
public class NuoMiFragment extends Fragment implements AdapterView.OnItemClickListener
{
    private int type_data = 0;
    private View headView;
    private XListView list;
    private HttpUtils httpUtils;
    private BitmapUtils bitmapUtils;
    private String url;
    private ThreeAdapter adapter;
    private ArrayList<NuoMiBean.Deals> arr;
    private TextView tv;
    private RelativeLayout rl_loading;
    private ImageView iv_loading;
    private boolean isRefresh = false;
    private WeakHandler handler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            rl_loading.setVisibility(View.GONE);
            stopAnim();
            return false;
        }
    });
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置超时时间
        arr = new ArrayList<NuoMiBean.Deals>();
        httpUtils = new HttpUtils(6*1000);
        url = ConstantUrl.url+"?"+ConstantUrl.arg;
        bitmapUtils = BitmapUtils.create(getActivity());
        bitmapUtils.configLoadingImage(R.mipmap.ic_launcher);

        headView = LayoutInflater.from(getActivity()).inflate(R.layout.zhihu_head_list, null);



        Log.i("abc", "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("abc", "onDestroy");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("abc", "onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("abc", "onPause");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("abc", "onCreateView");
        View view = inflater.inflate(R.layout.three_layout,null);
        list = (XListView) view.findViewById(R.id.xlist_three);
        list.setLayoutAnimation(getAnimationController());
        list.setXListViewListener(new MyXlistListener());
        list.setPullLoadEnable(true);
        list.setVisibility(View.GONE);
        list.setOnItemClickListener(this);
        rl_loading = (RelativeLayout) view.findViewById(R.id.rl_loading_three);
        iv_loading = (ImageView) view.findViewById(R.id.iv_loading_three);
        showLoading();

//        MyViewPager viewPager = (MyViewPager) headView.findViewById(R.id.viewPager_head);

        MyViewPager viewPager = (MyViewPager) headView.findViewById(R.id.viewPager_head);
        PageIndicator indicator = (PageIndicator) headView.findViewById(R.id.indicator_head);
        ArrayList<View> arr = new ArrayList<View>();
        View aa = inflater.inflate(R.layout.image_zhihu,null);
        ImageView image1 = (ImageView) aa.findViewById(R.id.image_zhihu);
        arr.add(image1);
        View bb = inflater.inflate(R.layout.image_zhihu,null);
        ImageView image2 = (ImageView) bb.findViewById(R.id.image_zhihu);
        arr.add(image2);
        View cc = inflater.inflate(R.layout.image_zhihu,null);
        ImageView image3 = (ImageView) cc.findViewById(R.id.image_zhihu);
        arr.add(image3);
        View dd = inflater.inflate(R.layout.image_zhihu,null);
        ImageView image4 = (ImageView) dd.findViewById(R.id.image_zhihu);
        arr.add(image4);
        viewPager.setAdapter(new HeadPagerAdapter(arr,getActivity()));
        indicator.setViewPager(viewPager);

        list.addHeaderView(headView);

        getData();
        return view;
    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.addHeader("apiKey", ConstantUrl.apiKey);

        httpUtils.send(HttpRequest.HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                fillData(responseInfo);
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), RecycleActivity.class);
        startActivity(intent);
    }

    private class MyXlistListener implements XListView.IXListViewListener{

        @Override
        public void onRefresh() {
            isRefresh = true;
            type_data = 1;
            getData();
            list.setPullRefreshEnable(false);
        }

        @Override
        public void onLoadMore() {
            if(isRefresh == true){
                return;
            }
            type_data = 2;
            getData();
            list.setPullLoadEnable(false);
        }
    }
    //填充数据
    private void fillData(ResponseInfo<String> responseInfo) {
        Log.i("abc",responseInfo.result);
        handler.sendEmptyMessage(1);
        Gson gson = new Gson();
        NuoMiBean bean = gson.fromJson(responseInfo.result, NuoMiBean.class);
        ArrayList<NuoMiBean.Deals> data;
        data = (ArrayList<NuoMiBean.Deals>) bean.getDeals();
        switch (type_data){
            case 0:
                arr.addAll(data);
                arr.addAll(data);
                arr.addAll(data);
                break;
            case 1:
                list.stopRefresh();
                list.setPullRefreshEnable(true);
                arr.addAll(0, data);
                isRefresh = false;
                list.setRefreshTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                break;
            case 2:
                list.stopLoadMore();
                list.setPullLoadEnable(true);
                arr.addAll(data);
                break;
        }
        list.setVisibility(View.VISIBLE);
        if (adapter == null) {
            adapter = new ThreeAdapter(getActivity(), arr, bitmapUtils);
            list.setAdapter(adapter);
        }else{
            adapter.setData(arr);
            adapter.notifyDataSetChanged();
        }
    }
    private void showLoading() {
        rl_loading.setVisibility(View.VISIBLE);
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
    @Override
    public void onResume() {
        super.onResume();
        Log.i("abc", "onResume");
    }

    private LayoutAnimationController getAnimationController() {
        int duration=300;
        AnimationSet set = new AnimationSet(true);

//        Animation animation = new AlphaAnimation(0.0f, 1.0f);
//        animation.setDuration(duration);
//        set.addAnimation(animation);

//         animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
//                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
//                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);

        Animation animation = new TranslateAnimation(0.0f,0.0f, Utils.getHeightPixels(getActivity()),0.0f);
        animation.setDuration(duration);
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(set, 0.2f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        return controller;
    }
}
