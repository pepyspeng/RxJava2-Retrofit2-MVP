package com.tapup.business.competition.presenter;

import com.tapup.base.BasePresenterImpl;
import com.tapup.business.competition.contract.CompetitionContract;
import com.tapup.data.bean.BaseBean;
import com.tapup.data.bean.CompetitionBean;
import com.tapup.data.interactorImpl.CompetitionInteractorImpl;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by pepys on 2018/5/13
 * description:赛事列表
 */
public class CompetitionPresneterImpl extends BasePresenterImpl<CompetitionContract.View> implements CompetitionContract.Presenter {

    /**
     * 持有view引用
     */
    private CompetitionContract.View view;
    /**
     * 持有model引用
     */
    private CompetitionContract.interactor interactor = new CompetitionInteractorImpl();

    public CompetitionPresneterImpl(CompetitionContract.View view) {
        this.view = view;
    }


    @Override
    public void onStart() {

    }

    @Override
    public void getCompetitionList() {
        Disposable disposable = interactor.getCompetitionList(new RequestCallBackAdapter<BaseBean<List<CompetitionBean>>>(){
            @Override
            public void success(BaseBean<List<CompetitionBean>> data) {
                if(view.getListPage() == 1){
                    for (int i = 0; i < 10; i++) {
                        data.getResult().add(new CompetitionBean("S9世界总决赛--" + (i+1)));
                    }
                    view.CompetitionListSuccess(data.getResult());
                }else if(view.getListPage() == 2){
                    for (int i = 0; i < 10; i++) {
                        data.getResult().add(new CompetitionBean("S9世界总决赛--" + (i+11)));
                    }
                    view.CompetitionListSuccess(data.getResult());
                }else{
                    view.CompetitionListSuccess(null);
                }


            }
        });
        cacheDisposable.add(disposable);
    }
}
