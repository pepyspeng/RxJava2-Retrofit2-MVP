package com.tapup.business;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tapup.R;
import com.tapup.base.BaseActivity;
import com.tapup.base.BaseFragment;
import com.tapup.base.IBaseContract;
import com.tapup.business.adapter.MainViewPagerAdapter;
import com.tapup.business.competition.view.CompetitionFragment;
import com.tapup.business.demo.login.contract.ILoginContract;
import com.tapup.business.demo.login.contract.presneter.LoginPresneterImpl;
import com.tapup.business.game.view.GameFragment;
import com.tapup.business.home.view.HomeFragment;
import com.tapup.business.market.view.MarketFragment;
import com.tapup.widget.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by pepys on 2018/5/12.
 * description:
 */
public class MainActivity extends BaseActivity<ILoginContract.View, IBaseContract.Presenter<ILoginContract.View>> implements ILoginContract.View {

    @BindView(R.id.vpContent)
    NoScrollViewPager vpContent;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle toggle;

    private ArrayList<BaseFragment> fragments = new ArrayList<>();

    private HomeFragment homeFragment;
    private CompetitionFragment competitionFragment;
    private GameFragment gameFragment;
    private MarketFragment marketFragment;
    private BaseFragment mCurrentFragment;

    @Override
    protected IBaseContract.Presenter<ILoginContract.View> createPresenter() {
        return new LoginPresneterImpl(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setBtnLeft(R.mipmap.icon_main_navigation);
        setBtnRight(R.mipmap.icon_main_right);
        setBtnRightVisition(true);

        initViewPage();
    }


    @Override
    protected void init() {

    }

    private void initFragment(){
        homeFragment = HomeFragment.newInstance();
        competitionFragment = CompetitionFragment.newInstance();
        gameFragment = GameFragment.newInstance();
        marketFragment = MarketFragment.newInstance();
        fragments.add(homeFragment);
        fragments.add(competitionFragment);
        fragments.add(gameFragment);
        fragments.add(marketFragment);
    }


    private void initViewPage(){
        initFragment();
        vpContent.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager(),fragments));
        vpContent.setOffscreenPageLimit(1);
        vpContent.setCurrentItem(0);
        mCurrentFragment = homeFragment;
        vpContent.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switchToolbar(position);
                mCurrentFragment = fragments.get(position);
            }
        });
    }

    private void switchToolbar(int position) {
    }


    @OnClick({R.id.btnLeft, R.id.btnRight,R.id.radio_main_home,R.id.radio_main_competition,R.id.radio_main_game,R.id.radio_main_market})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btnLeft:

                break;
            case R.id.btnRight:

                break;
            case R.id.radio_main_home:
                vpContent.setCurrentItem(0, true);
                break;
            case R.id.radio_main_competition:
                vpContent.setCurrentItem(1, true);
                break;
            case R.id.radio_main_game:
                vpContent.setCurrentItem(2, true);
                break;
            case R.id.radio_main_market:
                vpContent.setCurrentItem(3, true);
                break;
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void loginSuccess() {

    }

    @Override
    public void loginFailure() {

    }

    //toolbar支持
    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayShowTitleEnabled(false);
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
    }
    private void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null && mDrawerLayout != null) {
            toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            mDrawerLayout.addDrawerListener(toggle);
            toggle.syncState();
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

}
