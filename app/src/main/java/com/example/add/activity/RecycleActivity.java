package com.example.add.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.add.R;
import com.example.add.adapter.HeadPagerAdapter;
import com.example.add.adapter.HomeAdapter;
import com.example.add.enginge.DividerItemDecoration;
import com.example.add.utils.IntentUtils;
import com.example.add.view.MyViewPager;
import com.example.add.view.PageIndicator;


import java.util.ArrayList;

/**
 * Created by myself on 15/8/18.
 */
public class RecycleActivity extends AppCompatActivity implements HomeAdapter.OnItemClickListener,View.OnClickListener{
    private ArrayList<String> mDatas;
    private RecyclerView recyclerView;
    HomeAdapter adapter;
    private Button remove,add;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        recyclerView = (RecyclerView)findViewById(R.id.id_recyclerview);
        remove = (Button) findViewById(R.id.add_recy);
        add = (Button) findViewById(R.id.remove_recy);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitleTextColor(Color.WHITE);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });

        remove.setOnClickListener(this);
        add.setOnClickListener(this);

        mDatas = new ArrayList<String>();
        for(int i =0;i<20;i++){
            mDatas.add("我是"+i);
        }
        initRecycle();

        MyViewPager viewPager = (MyViewPager) findViewById(R.id.viewPager_head);
        //PageIndicator indicator = (PageIndicator) findViewById(R.id.indicator_head);
        ArrayList<View> arr = new ArrayList<View>();
        View aa = LayoutInflater.from(this).inflate(R.layout.image_zhihu, null);
        ImageView image1 = (ImageView) aa.findViewById(R.id.image_zhihu);
        arr.add(image1);
        View bb = LayoutInflater.from(this).inflate(R.layout.image_zhihu, null);
        ImageView image2 = (ImageView) bb.findViewById(R.id.image_zhihu);
        arr.add(image2);
        View cc = LayoutInflater.from(this).inflate(R.layout.image_zhihu, null);
        ImageView image3 = (ImageView) cc.findViewById(R.id.image_zhihu);
        arr.add(image3);
        View dd = LayoutInflater.from(this).inflate(R.layout.image_zhihu, null);
        ImageView image4 = (ImageView) dd.findViewById(R.id.image_zhihu);
        arr.add(image4);

        viewPager.setAdapter(new HeadPagerAdapter(arr,this));
        viewPager.startAutoScroll();
        //indicator.setViewPager(viewPager);

    }

    private void initRecycle() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
         adapter = new HomeAdapter(this,mDatas);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setListener(this);
    }

    @Override
    public void onItenClick(View view, int position) {
        Toast.makeText(this,"你点击了"+position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_recy:
                adapter.addData(1);
                break;
            case R.id.remove_recy:
                IntentUtils.share(this);
                break;
        }
    }
}
