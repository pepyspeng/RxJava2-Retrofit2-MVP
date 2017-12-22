package com.demo.business.login.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.demo.R;
import com.demo.base.BaseActivity;
import com.demo.base.IBaseContract;
import com.demo.business.juhe.main.ChoiceActivity;
import com.demo.business.login.contract.ILoginContract;
import com.demo.business.login.contract.presneter.LoginPresneterImpl;
import com.demo.business.permission.PermissionActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by pepys on 2017/2/27.
 * description:
 */
public class MainActivity extends BaseActivity<ILoginContract.View, IBaseContract.Presenter<ILoginContract.View>> implements ILoginContract.View {

    @BindView(R.id.logion_name)
    EditText logion_name;
    @BindView(R.id.logion_pwd)
    EditText logion_pwd;
    @BindView(R.id.login_submit)
    Button login_submit;

    @BindView(R.id.btn_rxpermission)
    Button btn_rxpermission;

    private LoginPresneterImpl presneter;



    @Override
    protected IBaseContract.Presenter<ILoginContract.View> createPresenter() {
        return presneter = new LoginPresneterImpl(MainActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }


    @OnClick({R.id.login_submit,R.id.btn_toast,R.id.btn_toast1})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_submit:
                PermissionActivity.callActivity(MainActivity.this);
                break;
            case R.id.btn_toast:
                ChoiceActivity.callActivity(MainActivity.this);
                break;
            case R.id.btn_toast1:
                break;

        }
    }

    @Override
    public void showProgress() {
        showToast(getString(R.string.holding));
    }

    @Override
    public void hideProgress() {

    }


    @Override
    public String getName() {
        return logion_name.getText().toString();
    }

    @Override
    public String getPassword() {
        return logion_pwd.getText().toString();
    }

    @Override
    public void loginSuccess() {
        showToast("成功..");
    }

    @Override
    public void loginFailure() {
        showToast("失败..");
    }


}
