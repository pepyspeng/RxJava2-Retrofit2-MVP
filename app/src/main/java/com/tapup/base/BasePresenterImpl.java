package com.tapup.base;


import com.tapup.common.enums.ResultCode;
import com.tapup.utils.rxhelper.RequestCallBack;
import com.tapup.utils.rxhelper.RxJavaUtils;
import com.orhanobut.logger.Logger;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public abstract class BasePresenterImpl<k extends IBaseContract.View> implements IBaseContract.Presenter<k> {

    protected k mView;

    protected List<Disposable> cacheDisposable = new LinkedList<>();
    @Override
    public void attachView( k view) {
        this.mView = view;
    }

    @Override
    public void onDestroy() {
        cancelRequest();
        mView = null;
    }

    /**
     * 这个计划是在view销毁的时候同时取消请求
     */
    public void cancelRequest() {
        for (Disposable subscription : cacheDisposable) {
            RxJavaUtils.cancelSubscription(subscription);
        }
        cacheDisposable = null;
    }

    public class RequestCallBackAdapter<T> implements RequestCallBack<T> {

        @Override
        public void beforeRequest() {
            if (mView != null) {
                mView.showProgress();
            }
        }

        @Override
        public void success(T data) {
        }

        @Override
        public void onError(@ResultCode int resultCode, String errorMsg) {
            Logger.e("请求失败,ResultCode: " + resultCode + ", errorMsg: " + errorMsg);
        }

        @Override
        public void after() {
            if (mView != null) {
                mView.hideProgress();
            }
        }
    }
}
