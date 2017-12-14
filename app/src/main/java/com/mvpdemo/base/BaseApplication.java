package com.mvpdemo.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.mvpdemo.BuildConfig;
import com.mvpdemo.common.enums.EnvironmentType;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

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

        Logger.addLogAdapter(new AndroidLogAdapter());

    }

    private void initApplicationEnvironment() {
        if (BuildConfig.ENVIRONMENT.equals("debug_test")) {
            mEnvType = EnvironmentType.ENV_DEBUG;
        }else if (BuildConfig.ENVIRONMENT.equals("pre_environ")) {
            mEnvType = EnvironmentType.ENV_RELEASE;
        }
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
}
