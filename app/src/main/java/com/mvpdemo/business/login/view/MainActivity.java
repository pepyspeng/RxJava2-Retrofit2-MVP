package com.mvpdemo.business.login.view;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mvpdemo.R;
import com.mvpdemo.base.BaseActivity;
import com.mvpdemo.base.IBaseContract;
import com.mvpdemo.business.login.contract.ILoginContract;
import com.mvpdemo.business.login.contract.presneter.LoginPresneterImpl;
import com.mvpdemo.utils.ToastUtil;

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
    private LoginPresneterImpl presneter;
    private String style = null;

    @Override
    protected IBaseContract.Presenter<ILoginContract.View> createPresenter() {
        return presneter = new LoginPresneterImpl(MainActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }


    @OnClick({R.id.login_submit,R.id.btn_toast,R.id.btn_toast1,R.id.btn_toast2})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_submit:
                showToast("啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦");
                break;
            case R.id.btn_toast:
                ToastUtil.showToast("哈哈哈",Toast.LENGTH_SHORT, Gravity.CENTER);
                break;
            case R.id.btn_toast1:
                break;
            case R.id.btn_toast2:
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
        Toast.makeText(this, "成功..", Toast.LENGTH_LONG).show();
    }

    @Override
    public void loginFailure() {
        Toast.makeText(this, "失败..", Toast.LENGTH_LONG).show();
    }


}
