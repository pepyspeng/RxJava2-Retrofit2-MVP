package com.tapup.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tapup.R;
import com.tapup.business.MainActivity;
import com.tapup.utils.ToastUtil;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by pepys on 2018/5/11
 * description:
 */
public abstract class BaseActivity<V, T extends IBaseContract.Presenter<V>> extends AppCompatActivity {


    @BindView(R.id.btnLeft)
    AppCompatImageView btnLeft;
    @BindView(R.id.btnRight)
    AppCompatImageView btnRight;
    @BindView(R.id.layoutRightCustom)
    LinearLayout layoutRightCustom;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rlHead)
    RelativeLayout rlHead;
    FrameLayout flContent;

    private boolean exit;
    public Handler mainHandler;

    @ColorInt
    int defaultStatusColor = R.color.colorPrimary;

    protected String TAG = BaseActivity.class.getSimpleName();

    protected T mPresneter;
    protected Unbinder mUnbinder;


    /**
     * create presenter
     *
     * @return
     */
    protected abstract T createPresenter();

    /**
     * layout布局Id，强制实现
     *
     * @return
     */
    protected abstract int getLayoutID();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        flContent = (FrameLayout) findViewById(R.id.content);
        if (getLayoutID() != 0) {
            View contentView = LayoutInflater.from(this).inflate(getLayoutID(), null);
            flContent.addView(contentView);
        } else {
            throw new RuntimeException("the LayoutId did not set!");
        }
        TAG = this.getClass().getSimpleName();
//        setStatusBarTranslucent(); //目前主题是白色。适配很麻烦

        /*创建Presenter*/
        mPresneter = createPresenter();
        if (mPresneter != null) {
            /*内存泄漏 ， 关联View*/
            mPresneter.attachView((V) this);

        }
        mUnbinder = ButterKnife.bind(this);
        initKeyboard();
        init();

    }

    protected abstract void init();


    @OnClick({R.id.btnLeft, R.id.btnRight})
    public void onCLickView(View view) {
        switch (view.getId()) {
            case R.id.btnLeft:
                finish();
                break;
            case R.id.btnRight:

                break;
        }
    }

    public int getStatusColor() {
        return defaultStatusColor;
    }

    /**
     * 设置左边按钮图标
     *
     * @param resID
     */
    protected void setBtnLeft(int resID) {
        btnLeft.setBackground(getResources().getDrawable(resID));
    }

    /**
     * 设置标题是否显示
     *
     * @param isVisition boolean
     */
    protected void setTitleIsVisition(boolean isVisition) {
        rlHead.setVisibility(isVisition == true ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置右边按钮图标
     *
     * @param resID
     */
    protected void setBtnRight(int resID) {
        btnRight.setBackground(getResources().getDrawable(resID));
    }

    protected void setBtnRightVisition(boolean isVisition) {
        btnRight.setVisibility(isVisition == true ? View.VISIBLE : View.GONE);

    }

    protected void showToast(String message) {
        ToastUtil.showToast(message);
    }


    @Override
    public void onBackPressed() {
        if (this instanceof MainActivity) {
            if (!exit) {
                if (mainHandler == null) {
                    mainHandler = new MainHandler(this);
                }
                mainHandler.sendEmptyMessageDelayed(MainHandler.EXIT_APP, 2000);
                showToast(getResources().getString(R.string.exit));
                exit = true;
            } else {
                exit();
            }
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.left_in_x, R.anim.right_out_x);
        }
    }


    private void initKeyboard() {
        //点击空白处隐藏键盘
        findViewById(android.R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }

    //适配4.4及以上的沉浸式
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setStatusBarTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUISetStatusBarLightMode(this.getWindow(), true)) {//MIUI
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0
                    this.getWindow().setStatusBarColor(Color.WHITE);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4
                    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    SystemBarTintManager tintManager = new SystemBarTintManager(this);
                    tintManager.setStatusBarTintEnabled(true);
                    tintManager.setStatusBarTintResource(android.R.color.white);
                }
            } else if (FlymeSetStatusBarLightMode(this.getWindow(), true)) {//Flyme
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0
                    this.getWindow().setStatusBarColor(Color.WHITE);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4
                    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    SystemBarTintManager tintManager = new SystemBarTintManager(this);
                    tintManager.setStatusBarTintEnabled(true);
                    tintManager.setStatusBarTintResource(android.R.color.white);
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0
                this.getWindow().setStatusBarColor(Color.WHITE);
                this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setStatusBarTintResource(getStatusColor());
//        }
    }


    /**
     * 设置标题
     *
     * @param titleName string
     */
    protected void setTitleName(String titleName) {
        tvTitle.setText(titleName);
    }


    public void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            return;
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            AppCompatTextView tvToolbarCenter = (AppCompatTextView) toolbar.findViewById(R.id.tvToolbarCenter);
            tvToolbarCenter.setVisibility(View.VISIBLE);
            tvToolbarCenter.setText(title);
        }
    }


    public static class MainHandler extends Handler {
        private static final int EXIT_APP = 0x001;
        private WeakReference<BaseActivity> reference;

        MainHandler(BaseActivity activity) {
            reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseActivity activity = reference.get();
            if (activity != null) {
                switch (msg.what) {
                    case EXIT_APP:
                        activity.exit = false;
                        break;
                }
            }
        }
    }

    //退出
    private void exit() {
        finish();
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left_in_x, R.anim.right_out_x);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresneter != null){
            mPresneter.onDestroy();
        }
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }


    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }


}
