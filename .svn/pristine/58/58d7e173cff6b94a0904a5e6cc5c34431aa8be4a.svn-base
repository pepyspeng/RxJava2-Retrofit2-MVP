package com.tapup.business.home.view;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tapup.R;
import com.tapup.base.BaseActivity;
import com.tapup.base.IBaseContract;
import com.tapup.business.adapter.HomeNewsAdapter;
import com.tapup.data.bean.BaseBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Dawson
 */
public class NewsActivity extends BaseActivity{
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @Override
    protected IBaseContract.Presenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_news;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setBtnLeft(R.mipmap.left);
        setBtnRightVisition(false);
        setTitleName("资讯");
    }


    @Override
    protected void init() {
        setBtnLeft(R.mipmap.icon_main_navigation);
        setBtnRight(R.mipmap.icon_main_right);
        setBtnRightVisition(true);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        List<BaseBean> newsData = new ArrayList<>();
        newsData.add(new BaseBean());
        newsData.add(new BaseBean());
        newsData.add(new BaseBean());
        newsData.add(new BaseBean());
        newsData.add(new BaseBean());
        newsData.add(new BaseBean());
        newsData.add(new BaseBean());
        newsData.add(new BaseBean());
        newsData.add(new BaseBean());
        final HomeNewsAdapter homeNewsAdapter = new HomeNewsAdapter(R.layout.item_home_news, newsData);
        homeNewsAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);

        rvList.setAdapter(homeNewsAdapter);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    swipeLayout.setRefreshing(false);
            }
        });
        homeNewsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                rvList.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (false) {
                            //数据全部加载完毕
                            homeNewsAdapter.loadMoreEnd();
                        } else {
                            if (false) {
                                //成功获取更多数据
                                List<BaseBean> newsData = new ArrayList<>();
                                newsData.add(new BaseBean());
                                newsData.add(new BaseBean());
                                newsData.add(new BaseBean());
                                newsData.add(new BaseBean());
                                newsData.add(new BaseBean());
                                newsData.add(new BaseBean());
                                newsData.add(new BaseBean());
                                newsData.add(new BaseBean());
                                newsData.add(new BaseBean());
                                homeNewsAdapter.addData(newsData);
                                homeNewsAdapter.loadMoreComplete();
                            }
                            else {
                                homeNewsAdapter.loadMoreFail();

                            }
                        }
                    }

                }, 2000);
            }
        },rvList);
    }




    @OnClick({R.id.btnLeft, R.id.btnRight})
    public void onCLickView(View view) {
        switch (view.getId()) {
            case R.id.btnLeft:

                break;
            case R.id.btnRight:

                break;
        }
    }


}
