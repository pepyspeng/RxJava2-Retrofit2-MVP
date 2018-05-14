package com.tapup.business.competition.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tapup.R;
import com.tapup.base.BaseApplication;
import com.tapup.base.BaseFragment;
import com.tapup.base.IBaseContract;
import com.tapup.business.adapter.CompetitionListAdapter;
import com.tapup.business.competition.contract.CompetitionContract;
import com.tapup.business.competition.presenter.CompetitionPresneterImpl;
import com.tapup.data.bean.CompetitionBean;
import com.tapup.utils.helper.ViewHelper;

import java.util.List;

import butterknife.BindView;

import static com.tapup.common.DataSetting.LIST_DATA_SIZE;

/**
 * Created by pepys on 2018/5/13.
 * description: 赛事列表
 */

public class ComprtitionListFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener,CompetitionContract.View{

    private final static String FRAGMENT_TYPE = "fragment_Type";
    private int fragmentType;  // 1：已报名  2：报名中 3：进行中 4：已结束
    private CompetitionContract.Presenter mPresenter;

    @BindView(R.id.competion_list_recycleView)
    RecyclerView competion_list_recycleView;
    @BindView(R.id.competion_list_swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    /**
     * 数据源
     */
    private List<CompetitionBean> mCompetitionList;
    /**
     * adapter
     */
    private CompetitionListAdapter competitionListAdapter;
    /**
     * 无数据界面
     */
    private View notDataView;
    /**
     * 是否是刷新
     */
    private boolean isRefresh = true;
    /**
     * 当前数据的页数
     */
    private int currentPage = 1;

    @Override
    public void onResume() {
        super.onResume();
    }

    public static ComprtitionListFragment newInstance(int fragmentType) {
        ComprtitionListFragment fragment = new ComprtitionListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_TYPE,fragmentType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_competition_list;
    }

    @Override
    protected IBaseContract.Presenter createPresenter() {
        mPresenter = new CompetitionPresneterImpl(this);
        return mPresenter;
    }

    @Override
    public IBaseContract.Presenter getBasePresenter() {
        return mPresenter;
    }

    @Override
    protected void getArgument(){
        Bundle bundle = getArguments();
        fragmentType = bundle.getInt(FRAGMENT_TYPE);
    }

    @Override
    public void init(View view) {
        initData();
        initListener();
        mPresenter.getCompetitionList();
    }

    private void initData() {
        mSwipeRefreshLayout.setColorSchemeColors(BaseApplication.getResource().getColor(R.color.c_main_red));
        mSwipeRefreshLayout.setOnRefreshListener(this);

        competitionListAdapter = new CompetitionListAdapter(R.layout.adapter_competition_list, mCompetitionList);
        competitionListAdapter.setFragmentType(fragmentType);
        competitionListAdapter.setOnLoadMoreListener( this,competion_list_recycleView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        competion_list_recycleView.setLayoutManager(linearLayoutManager);
        competion_list_recycleView.setHasFixedSize(true);
        competion_list_recycleView.setNestedScrollingEnabled(false);
        competion_list_recycleView.setAdapter(competitionListAdapter);

        notDataView = ViewHelper.getEmptyView(getActivity(),competion_list_recycleView);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        competitionListAdapter.setEmptyView(notDataView);
    }


    @Override
    public void onRefresh() {
        isRefresh = true;
        if (mPresenter != null) {
            if (competitionListAdapter != null && competitionListAdapter.getData().size() > 0) {
                mSwipeRefreshLayout.setRefreshing(true);
            }
            currentPage = 1;
            //下拉刷新
            competitionListAdapter.setEnableLoadMore(false);
            mPresenter.getCompetitionList();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        isRefresh = false;
        currentPage++;
        mSwipeRefreshLayout.setRefreshing(false);
        mPresenter.getCompetitionList();
    }


    @Override
    public void CompetitionListSuccess(List<CompetitionBean> competitionList) {
        if (null == competitionList || competitionList.size() == 0) {
            if (isRefresh) {
                competitionListAdapter.setNewData(competitionList);
                mSwipeRefreshLayout.setRefreshing(false);
                competitionListAdapter.setEmptyView(notDataView);
            } else {
                competitionListAdapter.loadMoreEnd();
            }
            return;
        } else {
            if (isRefresh) {
                competitionListAdapter.setNewData(competitionList);
                mSwipeRefreshLayout.setRefreshing(false);
            } else {
                competitionListAdapter.addData(competitionList);
                competitionListAdapter.loadMoreComplete();
            }
        }
        if (competitionList.size() % LIST_DATA_SIZE == 0) {
            competitionListAdapter.setEnableLoadMore(true);
        } else {
            competitionListAdapter.setEnableLoadMore(false);
        }
        mCompetitionList = competitionList;
    }

    @Override
    public void CompetitionListFailure() {

    }

    private void initListener() {
        competion_list_recycleView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });


    }

    @Override
    public int getFragmentType() {
        return fragmentType;
    }

    @Override
    public int getListPage() {
        return currentPage;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
