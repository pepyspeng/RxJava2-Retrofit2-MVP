package com.demo.business.login.contract.presneter;

import com.demo.base.BasePresenterImpl;
import com.demo.business.login.contract.ILoginContract;
import com.demo.data.bean.BaseBean;
import com.demo.data.bean.LoginBean;
import com.demo.data.interactorImpl.LoginInteractorImpl;

import io.reactivex.disposables.Disposable;

/**
 * Created by pepys on 2017/5/26.
 * description:Presneter 这是是处理view和model的地方
 */
public class LoginPresneterImpl extends BasePresenterImpl<ILoginContract.View> implements ILoginContract.Presenter {

    /**
     * 持有view引用
     */
    private ILoginContract.View view;
    /**
     * 持有model引用
     */
    private ILoginContract.interactor interactor = new LoginInteractorImpl();

    public LoginPresneterImpl(ILoginContract.View view) {
        this.view = view;
    }

    @Override
    public void login() {

        Disposable disposable1 = interactor.login(new RequestCallBackAdapter<BaseBean<LoginBean>>(){

        });

        cacheDisposable.add(disposable1);
    }

    @Override
    public void onStart() {
        //这里可以放入进界面就请求的
    }

}
