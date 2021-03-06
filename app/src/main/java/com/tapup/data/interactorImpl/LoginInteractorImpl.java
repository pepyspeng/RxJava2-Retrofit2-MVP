package com.tapup.data.interactorImpl;

import com.tapup.business.demo.login.contract.ILoginContract;
import com.tapup.data.net.RetrofitInstance;
import com.tapup.data.api.ILoginApi;
import com.tapup.data.bean.BaseBean;
import com.tapup.data.bean.LoginBean;
import com.tapup.utils.rxhelper.DefaultObserver;
import com.tapup.utils.rxhelper.RequestCallBack;
import com.tapup.utils.rxhelper.RxJavaUtils;

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
