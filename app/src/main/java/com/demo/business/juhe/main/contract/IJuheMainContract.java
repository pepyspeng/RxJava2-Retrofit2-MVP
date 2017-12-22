package com.demo.business.juhe.main.contract;

import com.demo.base.IBaseContract;
import com.demo.data.bean.BaseBean;
import com.demo.data.bean.ChoiceBean;
import com.demo.utils.rxhelper.RequestCallBack;

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
