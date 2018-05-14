package com.tapup.business.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tapup.base.BaseFragment;

import java.util.List;


/**
 * Created by shawn on 2018/5/12
 * <p>
 * description :
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragments;

    public MainViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
