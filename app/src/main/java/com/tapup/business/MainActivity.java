package com.tapup.business;

import android.os.Bundle;

import com.tapup.R;
import com.tapup.base.BaseActivity;
import com.tapup.base.IBaseContract;
import com.tapup.business.demo.login.contract.ILoginContract;
import com.tapup.business.demo.login.contract.presneter.LoginPresneterImpl;

/**
 * Created by pepys on 2017/2/27.
 * description:
 */
public class MainActivity extends BaseActivity<ILoginContract.View, IBaseContract.Presenter<ILoginContract.View>> implements ILoginContract.View {



    @Override
    protected IBaseContract.Presenter<ILoginContract.View> createPresenter() {
        return new LoginPresneterImpl(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleIsVisition(false);
    }


    @Override
    protected void init() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void loginSuccess() {

    }

    @Override
    public void loginFailure() {

    }
}
