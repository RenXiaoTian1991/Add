package com.example.add.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.add.R;
import com.example.add.adapter.TabFragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by myself on 15/8/31.
 */
public class TabLayoutFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentManager manager;
    private TabFragmentPagerAdapter tabFragmentPagerAdapter;

    public TabLayoutFragment(FragmentManager  manager) {
        this.manager = manager;
        manager.beginTransaction().setCustomAnimations(R.anim.push_down_out,R.anim.push_down_in);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tablayout,null);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("百度糯米"));
        tabLayout.addTab(tabLayout.newTab().setText("豆瓣"));
        tabLayout.addTab(tabLayout.newTab().setText("百度糯米"));
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        initViewPager();
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void initViewPager() {
        ArrayList<String>  tabName = new ArrayList<String>();
        tabName.add("百度糯米");
        tabName.add("豆瓣");
        tabName.add("百度糯米");
        ArrayList<Fragment> tabFragment = new ArrayList<Fragment>();
        tabFragment.add(new DouBanFragment());
        tabFragment.add(new NuoMiFragment());
        tabFragment.add(new NuoMiFragment());
        tabFragmentPagerAdapter = new TabFragmentPagerAdapter(manager, tabFragment, tabName);
        viewPager.setAdapter(tabFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
    }
}
