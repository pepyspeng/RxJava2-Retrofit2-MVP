package com.mvpdemo.business.login.contract;

import com.mvpdemo.base.IBaseContract;
import com.mvpdemo.data.bean.BaseBean;
import com.mvpdemo.data.bean.LoginBean;
import com.mvpdemo.utils.rxhelper.RequestCallBack;

import io.reactivex.disposables.Disposable;

public interface ILoginContract {

    interface View extends IBaseContract.View {
        /*得到用户名*/
        String getName();
        /*得到密码*/
        String getPassword();
        void loginSuccess();
        void loginFailure();

    }

    interface Presenter extends IBaseContract.Presenter<View> {
        /*执行请求处理*/
        void login();
    }

    interface interactor {

        Disposable login(RequestCallBack<BaseBean<LoginBean>> callBack);
    }
}