package com.example.add.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.add.R;

import java.util.List;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> titles;
    private FragmentManager supportFragmentManager;

    public TabFragmentPagerAdapter(FragmentManager supportFragmentManager,
                                   List<Fragment> fragments, List<String> titles) {
        super(supportFragmentManager);
        this.supportFragmentManager = supportFragmentManager;
        this.fragments = fragments;
        this.titles = titles;
        supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.push_down_out,R.anim.push_down_in);
    }


    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && titles.size() > 0) {
            return titles.get(position);
        }
        return null;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);


//        Fragment fragment = (Fragment) super.instantiateItem(container,
//                position);
////得到tag，这点很重要
//        String fragmentTag = fragment.getTag();
//
//
//        if (fragmentsUpdateFlag[position % fragmentsUpdateFlag.length]) {
////如果这个fragment需要更新
//
//            FragmentTransaction ft = fm.beginTransaction();
////移除旧的fragment
//            ft.remove(fragment);
////换成新的fragment
//            fragment = fragments[position % fragments.length];
////添加新fragment时必须用前面获得的tag，这点很重要
//            ft.add(container.getId(), fragment, fragmentTag);
//            ft.attach(fragment);
//            ft.commit();
//
////复位更新标志
//            fragmentsUpdateFlag[position % fragmentsUpdateFlag.length] = false;
//        }
//
//
//        return fragment;
//    }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
