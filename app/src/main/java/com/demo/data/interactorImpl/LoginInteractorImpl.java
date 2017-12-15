package com.demo.data.interactorImpl;

import com.demo.business.login.contract.ILoginContract;
import com.demo.data.Net.RetrofitInstance;
import com.demo.data.api.ILoginApi;
import com.demo.data.bean.BaseBean;
import com.demo.data.bean.LoginBean;
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
public class LoginInteractorImpl implements ILoginContract.interactor {

    @Override
    public Disposable login(RequestCallBack<BaseBean<LoginBean>> callBack) {
        ILoginApi loginApi = RetrofitInstance.getRetrofit().create(ILoginApi.class);
        Observable observable = loginApi.login(new HashMap<String, String>());
        Disposable disposable = RxJavaUtils.getDisposable(observable,new DefaultObserver<>(callBack));
        return disposable;
    }


}
