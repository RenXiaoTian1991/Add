package com.example.add.fragment;

import android.animation.ObjectAnimator;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.add.R;
import com.example.add.activity.ZhiHuDetailsActivity;
import com.example.add.adapter.HeadPagerAdapter;
import com.example.add.adapter.ZhiHuListAdapter;
import com.example.add.api.ZhihuApi;
import com.example.add.bean.ZhiHuBean;
import com.example.add.db.ZhiHuDao;
import com.example.add.enginge.ConstantUrl;
import com.example.add.utils.NetUtils;
import com.example.add.utils.Utils;
import com.example.add.utils.WeakHandler;
import com.example.add.view.MyViewPager;
import com.example.add.view.PageIndicator;
import com.example.add.view.XListView;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by myself on 15/8/21.
 */
public class ZhiHuListFragment extends Fragment implements AdapterView.OnItemClickListener
{
    private int type_data = 0;
    private ArrayList<String> mPosition = new ArrayList<String>();;
    private View headView;
    //private TextView txt_date;
    private long currentTime;
    private ZhiHuDao dao;
    private SimpleDateFormat format;
    private boolean isToolsHide = false;
    private Toolbar toolbar;
    private XListView list;
    private HttpUtils httpUtils;
    private BitmapUtils bitmapUtils;
    private String url;
    private ZhiHuListAdapter adapter;
    private ArrayList<ZhiHuBean.Stories> arr;
    private TextView tv;
    private RelativeLayout rl_loading;
    private ImageView iv_loading;
    private FloatingActionButton fab;
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
        arr = new ArrayList<ZhiHuBean.Stories>();
        httpUtils = new HttpUtils(6*1000);
        url = ConstantUrl.url+"?"+ConstantUrl.arg;
        bitmapUtils = BitmapUtils.create(getActivity());
        bitmapUtils.configLoadingImage(R.drawable.renxiaotian);
        dao = new ZhiHuDao(getActivity());

        currentTime = System.currentTimeMillis()+60*60*24*1000;
        //Date date = new Date(currentTime);
        format = new SimpleDateFormat("yyyyMMdd");
        String str = null;
        getData(str);
    }

    public void setToolBar(Toolbar toolBar){
        this.toolbar = toolBar;
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

//        View headView = inflater.inflate(R.layout.zhihu_head_list,null);
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.zhihu_head_list, null);
        MyViewPager viewPager = (MyViewPager) headView.findViewById(R.id.viewPager_head);
        PageIndicator indicator = (PageIndicator) headView.findViewById(R.id.indicator_head);
        final ArrayList<View> arr1 = new ArrayList<View>();
        View aa = inflater.inflate(R.layout.image_zhihu,null);
        ImageView image1 = (ImageView) aa.findViewById(R.id.image_zhihu);
        arr1.add(image1);
        View bb = inflater.inflate(R.layout.image_zhihu,null);
        ImageView image2 = (ImageView) bb.findViewById(R.id.image_zhihu);
        arr1.add(image2);
        View cc = inflater.inflate(R.layout.image_zhihu,null);
        ImageView image3 = (ImageView) cc.findViewById(R.id.image_zhihu);
        arr1.add(image3);
        View dd = inflater.inflate(R.layout.image_zhihu,null);
        ImageView image4 = (ImageView) dd.findViewById(R.id.image_zhihu);
        arr1.add(image4);
        viewPager.setAdapter(new HeadPagerAdapter(arr1, getActivity()));
        indicator.setViewPager(viewPager);
        viewPager.startAutoScroll();

        View view = inflater.inflate(R.layout.three_layout,null);
        list = (XListView) view.findViewById(R.id.xlist_three);
        list.addHeaderView(headView);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.setSelection(0);
            }
        });
        //txt_date = (TextView) view.findViewById(R.id.text_date_xlist);
        list.setLayoutAnimation(getAnimationController());
        list.setXListViewListener(new MyXlistListener());
        list.setPullLoadEnable(true);
        list.setVisibility(View.INVISIBLE);
        list.setOnItemClickListener(this);
        rl_loading = (RelativeLayout) view.findViewById(R.id.rl_loading_three);
        iv_loading = (ImageView) view.findViewById(R.id.iv_loading_three);
        showLoading();




        list.setOnTouchListener(new View.OnTouchListener() {
            float downY;
            float disY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downY = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        disY = motionEvent.getY() - downY;
                        float juli = ViewConfiguration.get(getActivity()).getScaledTouchSlop();
                        if (Math.abs(disY) > juli) {
                            boolean isUp = disY < 0;
                            if (isUp) {
                                if (!isToolsHide) {
                                    hideTools();
                                }
                            } else {
                                if (isToolsHide) {
                                    showTools();
                                }
                            }
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        downY = motionEvent.getY();
                        break;
                }
                return false;
            }
        });

        return view;

    }




        /**
     * 显示工具栏
     */
    private void showTools() {

        ObjectAnimator anim = ObjectAnimator.ofFloat(fab, "y", fab.getY(),
                fab.getY() - 400);
        anim.setDuration(300);
        anim.start();

        isToolsHide = false;
    }

    /**
     * 隐藏工具栏
     */
    private void hideTools() {

        ObjectAnimator anim = ObjectAnimator.ofFloat(fab, "y", fab.getY(),
                fab.getY() + 400);
        anim.setDuration(300);
        anim.start();

        isToolsHide = true;

    }



    private void getData(String str) {

        if(NetUtils.checkNet(getActivity())==false){
             ArrayList<ZhiHuBean.Stories> ttt = (ArrayList<ZhiHuBean.Stories>) dao.listAll();
             arr.addAll(ttt);
            Log.i("abc---adapter",ttt.size()+"");
            for (ZhiHuBean.Stories stor :ttt){
                Log.i("abc----db",stor.getTitle()==null?"null":stor.getTitle());
            }
            if(adapter == null && ttt.size()!=0){
                adapter = new ZhiHuListAdapter(getActivity(), arr,bitmapUtils);
                list.setAdapter(adapter);
            }else {if (ttt.size()!=0){
                adapter.notifyDataSetChanged();
            }
            }
        }else{
            if(str==null){
                httpUtils.send(HttpRequest.HttpMethod.GET, ZhihuApi.ZHIHU_TODAY_DATA, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        fillData(responseInfo);
                        Log.i("abc", responseInfo.result.toString());
                    }
                    @Override
                    public void onFailure(HttpException error, String msg) {

                    }
                });
            }else{
                httpUtils.send(HttpRequest.HttpMethod.GET, ZhihuApi.getDailyNews(str), new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        fillData(responseInfo);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                    }
                });
            }

        }


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int offset = 0;
        for (int a =0;i<i;a++){
            if(arr.get(a).getTime()!=null){
                offset ++;
            }
        }
        ZhiHuBean.Stories news = arr.get(i - 2 - offset);
        if(news.getTime()!=null){
            return;
        }
        String news_url = ZhihuApi.getNewsContent(news.getId());
        Intent intent = new Intent(getActivity(), ZhiHuDetailsActivity.class);
        intent.putExtra("title", news.title);
        intent.putExtra("url", news_url);
        getActivity().startActivity(intent);
    }

    private class MyXlistListener implements XListView.IXListViewListener{

        @Override
        public void onRefresh() {
            isRefresh = true;
            type_data = 1;
            currentTime = System.currentTimeMillis()+60*60*24*1000;
//            Date date = new Date();
//            format = new SimpleDateFormat("yyyyMMdd");
            String str = null;
            getData(str);
            list.setPullRefreshEnable(false);
        }

        @Override
        public void onLoadMore() {
            if(isRefresh == true){
                return;
            }
            currentTime = (currentTime - 60*60*24*1000);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(currentTime);
            Date date = cal.getTime();
            String str1 = format.format(date);
            type_data = 2;
            getData(str1);
            list.setPullLoadEnable(false);
        }
    }
    //填充数据
    private void fillData(ResponseInfo<String> responseInfo) {
        handler.sendEmptyMessage(1);
        Gson gson = new Gson();
        ZhiHuBean bean = gson.fromJson(responseInfo.result, ZhiHuBean.class);
        ArrayList<ZhiHuBean.Stories> data;
        data = bean.stories;
        ZhiHuBean.Stories stor = new ZhiHuBean().new Stories();
        if(type_data == 0){
            stor.setTime("今日要闻");
        }else{
            stor.setTime(bean.date);
        }
            data.add(0,stor);

        switch (type_data){
            case 0:
                arr.addAll(data);
               for (int i = 0;i<data.size();i++){
                    dao.add(data.get(i));
               }

                Log.i("abcd",data.get(2).toString());
                Log.i("abcde", dao.selectOneByTitle(data.get(2)).toString());
                break;
            case 1:
                list.stopRefresh();
                list.setPullRefreshEnable(true);
                arr.clear();
                arr.addAll(data);
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
            adapter = new ZhiHuListAdapter(getActivity(), arr,bitmapUtils);
            list.setAdapter(adapter);

            list.setOnScrollListener(new XListView.OnXScrollListener() {
                @Override
                public void onXScrolling(View view) {

                }

                @Override
                public void onScrollStateChanged(AbsListView absListView, int i) {

                }

                @Override
                public void onScroll(AbsListView absListView, int i, int i1, int i2) {

                    int position = 0;

                    ZhiHuBean.Stories stories = null;
                    if (i >= 2) {
                        stories = (ZhiHuBean.Stories) adapter.getItem(i - 2);


                    } else {
                        stories = (ZhiHuBean.Stories) adapter.getItem(i);
                    }
                    if (stories.getTime() != null) {

                        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(stories.getTime());

                        position = i - 2;
                        mPosition.add(stories.getTime());

                        return;
                    }else{
                        //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("首页");
                    }

                }
            });

        }else{
            adapter.setData(arr);
            adapter.notifyDataSetChanged();
        }
    }

    //                        if (stories.getTime() == null) {
//
//                            if ((i - 2) < position) {
//                            } else {
//
//                                Toast.makeText(getActivity(),"后一天",Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
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

