package com.tapup.business.competition.contract;

import com.tapup.base.IBaseContract;
import com.tapup.data.bean.BaseBean;
import com.tapup.data.bean.CompetitionBean;
import com.tapup.utils.rxhelper.RequestCallBack;

import java.util.List;

import io.reactivex.disposables.Disposable;

public interface CompetitionContract {

    interface View extends IBaseContract.View {
        /*类型*/
        int getFragmentType();
        int getListPage();
        void CompetitionListSuccess(List<CompetitionBean> competitionList);
        void CompetitionListFailure();

    }

    interface Presenter extends IBaseContract.Presenter<View> {
        void getCompetitionList();
    }

    interface interactor {
        Disposable getCompetitionList(RequestCallBack<BaseBean<List<CompetitionBean>>> callBack);
    }
}