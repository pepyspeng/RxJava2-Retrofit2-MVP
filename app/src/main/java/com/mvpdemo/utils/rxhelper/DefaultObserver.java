package com.mvpdemo.utils.rxhelper;


import com.mvpdemo.R;
import com.mvpdemo.base.BaseApplication;
import com.mvpdemo.exception.RxException;
import com.mvpdemo.utils.ErrorUtils;
import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by pepys on 2017/12/12
 * description: 默认的订阅者
 */
public class DefaultObserver<T> implements Observer<T> {

    private RequestCallBack<T> mCallBack;

    private String errorMsg = BaseApplication.getResourceString(R.string.internet_error);


    public DefaultObserver(RequestCallBack<T> callBack) {
        mCallBack = callBack;
    }

    public DefaultObserver(RequestCallBack<T> callBack, int resId) {
        this(callBack, BaseApplication.getResourceString(resId));
    }

    public DefaultObserver(RequestCallBack<T> callBack, String errorMessage) {
        mCallBack = callBack;
        errorMsg = errorMessage;
    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        try {
            Logger.i("rx onNext");
            mCallBack.success(t);
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    @Override
    public void onError(Throwable e) {
        try {
            Logger.e(e, "rx error");
            if (e instanceof RxException) {
                RxException rxException = (RxException) e;
                mCallBack.onError(rxException.getResultCode(), rxException.getMessage());
            } else {
                mCallBack.onError(ErrorUtils.getExceptionCode(e), ErrorUtils.getExceptionMessage(e, errorMsg));
            }
            mCallBack.after();
        } catch (Exception ex) {
            Logger.e(ex, "");
        }
    }

    @Override
    public void onComplete() {
        try {
            Logger.i("rx completed");
            mCallBack.after();
        } catch (Exception ex) {
            Logger.e(ex, "");
        }
    }
}
