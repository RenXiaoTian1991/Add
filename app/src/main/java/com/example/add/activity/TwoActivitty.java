package com.example.add.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.add.R;
import com.example.add.adapter.TabFragmentPagerAdapter;
import com.example.add.fragment.MyFragment;
import com.example.add.fragment.OtherFragment;
import com.example.add.fragment.NuoMiFragment;
import com.example.add.view.SmartTabLayout;

import java.util.ArrayList;

/**
 * Created by myself on 15/8/7.
 */
public class TwoActivitty extends FragmentActivity {
    private ViewPager pager;
    private FrameLayout indicator;
    private SmartTabLayout tab;
    private ArrayList<String> tabName;
    private ArrayList<Fragment> tabFragment;
    TabFragmentPagerAdapter tabFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two);
        getTabName();
        getTabFragMent();
        initView();
        tabFragmentPagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), tabFragment, tabName);
        pager.setOffscreenPageLimit(tabFragment.size());
        pager.setAdapter(tabFragmentPagerAdapter);
        tab.setViewPager(pager);
    }


    private void initView() {
        pager = (ViewPager) findViewById(R.id.viewpager);
        indicator = (FrameLayout) findViewById(R.id.indicator);
        View view = LayoutInflater.from(this).inflate(R.layout.demo_indicator_trick1, null);
        indicator.addView(view);
        tab = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
        tab.setDefaultTabTextColor(this.getResources().getColor(R.color.black));
        tab.setSelectedIndicatorColors(R.color.red);
    }

    public void getTabName() {
        tabName = new ArrayList<String>();
        tabName.add("数据库");
        tabName.add("百度糯米");
        tabName.add("百度糯米");
        tabName.add("第四个");
    }

    public void getTabFragMent() {

          tabFragment = new ArrayList<Fragment>();
//        for (int i = 0; i < 4; i++) {
//            Fragment fragment = new MyFragment();
//            tabFragment.add(fragment);
//        }
        Bundle bundle = new Bundle();
        //bundle.putCharSequence("第二个","我的还是我的");
        tabFragment.add(new MyFragment());
        tabFragment.add(Fragment.instantiate(this, NuoMiFragment.class.getName(), bundle));
        tabFragment.add(Fragment.instantiate(this, NuoMiFragment.class.getName(), bundle));
        tabFragment.add(new OtherFragment());
    }
}
