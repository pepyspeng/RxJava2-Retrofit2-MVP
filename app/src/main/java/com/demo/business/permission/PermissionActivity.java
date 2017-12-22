package com.demo.business.permission;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.demo.R;
import com.demo.base.BaseActivity;
import com.demo.business.permission.contract.IPermissionContract;
import com.demo.business.permission.contract.presneter.PermissionPresneterImpl;
import com.demo.widget.PopUpWindowAlertDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class PermissionActivity extends BaseActivity<IPermissionContract.View, IPermissionContract.Presenter>
        implements NavigationView.OnNavigationItemSelectedListener,IPermissionContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_rxpermission)
    Button btnRxpermission;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private RxPermissions rxPermissions;
    /**
     * 要申请的权限
     */
    private String[] permissions = {Manifest.permission.CAMERA};

    private IPermissionContract.Presenter presenter;

    public static void callActivity(Context context) {
        Intent intent = new Intent(context, PermissionActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected IPermissionContract.Presenter createPresenter() {
        presenter = new PermissionPresneterImpl(PermissionActivity.this);
        mPresneter = presenter;
        return presenter;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_permission;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initPermission();

        setSupportActionBar(toolbar);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void init() {

    }


    private void initPermission(){
        rxPermissions = new RxPermissions(this);
        RxView.clicks(btnRxpermission)
                .compose(rxPermissions.ensure(permissions))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if(aBoolean){
                            showToast("权限已拥有");
                        }else{
                            //点击拒绝
                            showAlertDialog("需要开启读写权限",1);
                        }
                    }
                });
    }

    @OnClick({R.id.btn_rxpermission})
    public void onClickView(View view){
        switch (view.getId()){
            case R.id.btn_rxpermission:

                break;
            case R.id.fab:
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
        }
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
                        ActivityCompat.requestPermissions(PermissionActivity.this, permissions, 1);
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.permission, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
