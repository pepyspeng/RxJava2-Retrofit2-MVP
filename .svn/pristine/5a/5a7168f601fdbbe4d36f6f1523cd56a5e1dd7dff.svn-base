package com.tapup.business.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tapup.R;
import com.tapup.base.BaseFragment;
import com.tapup.base.IBaseContract;
import com.tapup.business.adapter.HomeNewsAdapter;
import com.tapup.data.bean.BaseBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author pepys
 * @date 2018/5/12
 */

public class HomeFragment extends BaseFragment {


    @BindView(R.id.rv_list)
    RecyclerView rvList;
    Unbinder unbinder;
    @BindView(R.id.tv_more)
    TextView tvMore;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
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
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<BaseBean> newsData = new ArrayList<>();
        newsData.add(new BaseBean());
        newsData.add(new BaseBean());
        newsData.add(new BaseBean());
        HomeNewsAdapter homeNewsAdapter = new HomeNewsAdapter(R.layout.item_home_news, newsData);
        rvList.setAdapter(homeNewsAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({ R.id.tv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_more:
                Intent newsIntent  = new Intent(getActivity(),NewsActivity.class);
                startActivity(newsIntent);
                break;
                default:break;
        }
    }
}

