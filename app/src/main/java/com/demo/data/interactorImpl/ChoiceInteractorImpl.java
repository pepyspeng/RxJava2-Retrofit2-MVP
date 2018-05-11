package com.demo.data.interactorImpl;

import com.demo.business.juhe.main.contract.IJuheMainContract;
import com.demo.data.api.IChoiceApi;
import com.demo.data.bean.BaseBean;
import com.demo.data.bean.ChoiceBean;
import com.demo.data.net.RetrofitInstance;
import com.demo.utils.rxhelper.DefaultObserver;
import com.demo.utils.rxhelper.RequestCallBack;
import com.demo.utils.rxhelper.RxJavaUtils;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;


/**
 * Created by pepys on 2017/5/26.
 * description: 这里才是真正的数据请求
 */
public class ChoiceInteractorImpl implements IJuheMainContract.interactor {

    IChoiceApi choiceApi;

    public ChoiceInteractorImpl() {
        choiceApi = RetrofitInstance.getRetrofit().create(IChoiceApi.class);
    }

    @Override
    public Disposable getChoiceList(RequestCallBack<BaseBean<ChoiceBean>> callBack, HashMap<String, String> paramsMap) {

        Observable observable = choiceApi.getChoice("weixin/query",paramsMap);
        Disposable disposable = RxJavaUtils.getDisposable(observable, new DefaultObserver<>(callBack));
        return disposable;
    }
}
