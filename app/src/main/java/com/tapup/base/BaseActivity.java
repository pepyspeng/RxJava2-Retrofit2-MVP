package com.tapup.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tapup.R;
import com.tapup.business.MainActivity;
import com.tapup.utils.ToastUtil;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by pepys on 2018/5/11
 * description:
 *
 */
public abstract class BaseActivity<V, T extends IBaseContract.Presenter<V>> extends AppCompatActivity {


    @BindView(R.id.btnLeft)
    ImageView btnLeft;
    @BindView(R.id.btnRight)
    Button btnRight;
    @BindView(R.id.layoutRightCustom)
    LinearLayout layoutRightCustom;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rlHead)
    RelativeLayout rlHead;
    FrameLayout flContent;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle toggle;

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
        setStatusBarTranslucent();
        initToolBar();

        initDrawerLayout();

        /*创建Presenter*/
        mPresneter = createPresenter();
        /*内存泄漏 ， 关联View*/
        mPresneter.attachView((V) this);

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
     * 设置标题是否显示
     *
     * @param isVisition boolean
     */
    protected void setTitleIsVisition(boolean isVisition) {
        rlHead.setVisibility(isVisition == true ? View.VISIBLE : View.GONE);
    }


    protected void showToast(String message) {
        ToastUtil.showToast(message);
    }

    private void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null && mDrawerLayout != null) {
            toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            mDrawerLayout.addDrawerListener(toggle);
            toggle.syncState();
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (this instanceof MainActivity) {
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
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(getStatusColor());
        }
    }

    //toolbar支持
    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayShowTitleEnabled(false);
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
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
        mPresneter.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }


}
