package com.tapup.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tapup.BuildConfig;
import com.tapup.common.enums.EnvironmentType;


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


    public static Resources getResource() {
        return application.getResources();
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


    private void initApplicationEnvironment() {
        if (BuildConfig.ENVIRONMENT.equals("debug_environ")) {
            mEnvType = EnvironmentType.ENV_DEBUG;
        } else if (BuildConfig.ENVIRONMENT.equals("pre_environ")) {
            mEnvType = EnvironmentType.ENV_RELEASE;
        }
    }

}
