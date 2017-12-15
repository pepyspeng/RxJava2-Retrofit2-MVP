package com.demo.business.login.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.R;
import com.demo.base.BaseActivity;
import com.demo.base.IBaseContract;
import com.demo.business.login.contract.ILoginContract;
import com.demo.business.login.contract.presneter.LoginPresneterImpl;
import com.demo.utils.ToastUtil;
import com.demo.widget.PopUpWindowAlertDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

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
    private String style = null;

    private RxPermissions rxPermissions;
    /**
     * 要申请的权限
     */
    private String[] permissions = {Manifest.permission.CAMERA};


    @Override
    protected IBaseContract.Presenter<ILoginContract.View> createPresenter() {
        return presneter = new LoginPresneterImpl(MainActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPermission();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    private void initPermission(){
        rxPermissions = new RxPermissions(this);
        RxView.clicks(btn_rxpermission)
                .compose(rxPermissions.ensure(permissions))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if(aBoolean){

                        }else{
                            //点击拒绝
                            showAlertDialog("需要开启读写权限",1);
                        }
                    }
                });
    }

    @OnClick({R.id.login_submit,R.id.btn_toast,R.id.btn_toast1})
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

        }
    }

    @Override
    public void showProgress() {
        showToast(getString(R.string.holding));
    }

    @Override
    public void hideProgress() {

    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        showAlertDialog("请在-应用设置-权限中，允许使用",2);
                    } else {
                        finish();
                    }
                } else {
                    showToast("权限获取成功");
                }
            }
        }
    }

    /**
     * 展示Dialog
     */
    private void showAlertDialog(String message, final int type) {
        PopUpWindowAlertDialog.Builder builder = new PopUpWindowAlertDialog.Builder(this);
        builder.setMessage(message, 18);
        builder.setTitle(null, 0);
        builder.setPositiveButton("立即开启", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (type){
                    case 1:
                        ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
                        break;
                    case 2:
                        goToAppSetting();
                        break;
                }

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create().show();
    }
    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 1);
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
