package com.tapup.data.interactorImpl;

import com.tapup.business.competition.contract.CompetitionContract;
import com.tapup.data.api.CompetitionApi;
import com.tapup.data.bean.BaseBean;
import com.tapup.data.bean.CompetitionBean;
import com.tapup.data.net.RetrofitInstance;
import com.tapup.utils.rxhelper.RequestCallBack;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;


/**
 * Created by pepys on 2018/5/13.
 * description: 赛事列表
 */
public class CompetitionInteractorImpl implements CompetitionContract.interactor {

    CompetitionApi competitionApi;

    public CompetitionInteractorImpl() {
        competitionApi = RetrofitInstance.getRetrofit().create(CompetitionApi.class);
    }


    @Override
    public Disposable getCompetitionList(RequestCallBack<BaseBean<List<CompetitionBean>>> callBack) {
        List<CompetitionBean> competitipnList = new ArrayList<>();
        BaseBean<List<CompetitionBean>> baseBean = new BaseBean<>();
        baseBean.setResult(competitipnList);
        callBack.success(baseBean);
        return null;
    }
}
