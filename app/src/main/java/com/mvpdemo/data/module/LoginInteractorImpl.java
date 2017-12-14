package com.mvpdemo.data.module;

import com.mvpdemo.business.login.contract.ILoginContract;
import com.mvpdemo.data.Net.RetrofitInstance;
import com.mvpdemo.data.api.ILoginApi;
import com.mvpdemo.data.bean.BaseBean;
import com.mvpdemo.data.bean.LoginBean;
import com.mvpdemo.utils.rxhelper.DefaultObserver;
import com.mvpdemo.utils.rxhelper.RequestCallBack;
import com.mvpdemo.utils.rxhelper.RxJavaUtils;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;


/**
 * Created by pepys on 2017/5/26.
 * description: 这里才是真正的数据请求
 */
public class LoginInteractorImpl implements ILoginContract.interactor {

    @Override
    public Disposable login(RequestCallBack<BaseBean<LoginBean>> callBack) {
        ILoginApi loginApi = RetrofitInstance.getRetrofit().create(ILoginApi.class);
        Observable observable = loginApi.login(new HashMap<String, String>());
        Disposable disposable = RxJavaUtils.getDisposable(observable,new DefaultObserver<>(callBack));
        return disposable;
    }


}
