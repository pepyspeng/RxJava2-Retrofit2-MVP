package com.tapup.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.tapup.BuildConfig;
import com.tapup.R;
import com.tapup.common.enums.EnvironmentType;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;

/**
 * Created by zhupp on 2017/12/8.
 */
public class BaseApplication extends Application {

    private static BaseApplication application;

    @EnvironmentType
    public static int mEnvType;

    public static Context getAppContext() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        initApplicationEnvironment();
        initSmartRefresh();
        Logger.addLogAdapter(new AndroidLogAdapter());

    }


    public static String getResourceString(int resId) {
        String str = "";
        try {
            str = getAppContext().getString(resId);
        } catch (Resources.NotFoundException e) {
            Logger.e(e, "找不到指定资源", resId);
        }
        return str;
    }

    private void initSmartRefresh() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new BezierRadarHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new BallPulseFooter(context);
            }
        });
    }

    private void initApplicationEnvironment() {
        if (BuildConfig.ENVIRONMENT.equals("debug_environ")) {
            mEnvType = EnvironmentType.ENV_DEBUG;
        } else if (BuildConfig.ENVIRONMENT.equals("pre_environ")) {
            mEnvType = EnvironmentType.ENV_RELEASE;
        }
    }

}
