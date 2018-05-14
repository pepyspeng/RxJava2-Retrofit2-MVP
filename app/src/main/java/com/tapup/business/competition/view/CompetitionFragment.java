package com.tapup.business.competition.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.tapup.R;
import com.tapup.base.BaseFragment;
import com.tapup.base.IBaseContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by pepys on 2018/5/12.
 *  * description:
 */

public class CompetitionFragment extends BaseFragment {

    @BindView(R.id.competition_tablayout)
    SlidingTabLayout competition_tablayout;

    @BindView(R.id.competition_vivewpager)
    ViewPager competition_vivewpager;


    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();
    private MyPagerAdapter pagerAdapter;
    private Fragment signUpFragment;
    private Fragment signUpingFragment;
    private Fragment doingFragment;
    private Fragment endFragment;
    public static CompetitionFragment newInstance() {
        CompetitionFragment fragment = new CompetitionFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_competition;
    }

    @Override
    protected IBaseContract.Presenter createPresenter() {
        return null;
    }

    @Override
    public IBaseContract.Presenter getBasePresenter() {
        return null;
    }

    @Override
    public void init(View view) {
        initData();
        initView(view);
    }


    private void initData() {

    }

    private void initView(View view) {
        initViewpage();
    }

    private void initViewpage(){
        pagerAdapter = new MyPagerAdapter(getFragmentManager());
        mTitles.add("已报名");
        mTitles.add("报名中");
        mTitles.add("进行中");
        mTitles.add("已结束");
        signUpFragment = ComprtitionListFragment.newInstance(1);
        signUpingFragment = ComprtitionListFragment.newInstance(2);
        doingFragment = ComprtitionListFragment.newInstance(3);
        endFragment = ComprtitionListFragment.newInstance(4);
        mFragments.add(signUpFragment);
        mFragments.add(signUpingFragment);
        mFragments.add(doingFragment);
        mFragments.add(endFragment);
        competition_vivewpager.setAdapter(pagerAdapter);
        competition_tablayout.setViewPager(competition_vivewpager);

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
