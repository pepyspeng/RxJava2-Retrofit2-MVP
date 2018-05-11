package com.tapup.business.demo.juhe.main.contract;

import com.tapup.base.IBaseContract;
import com.tapup.data.bean.BaseBean;
import com.tapup.data.bean.ChoiceBean;
import com.tapup.utils.rxhelper.RequestCallBack;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;


/**
 * Created by pepys on 2017/12/15
 * description:
 *
 */
public interface IJuheMainContract {
    interface View extends IBaseContract.View {
        void choiceSuccess(ChoiceBean choiceList);

    }

    interface Presenter extends IBaseContract.Presenter<View> {
        void getChoiceList();
    }

    interface interactor {

        Disposable getChoiceList(RequestCallBack<BaseBean<ChoiceBean>> callBack,HashMap<String, String> paramsMap);
    }
}
