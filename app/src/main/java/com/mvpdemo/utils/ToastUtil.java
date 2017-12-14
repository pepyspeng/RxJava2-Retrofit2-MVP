package com.mvpdemo.utils;

import android.support.annotation.IntDef;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mvpdemo.R;
import com.mvpdemo.base.BaseApplication;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by zhupp on 2017/12/13.
 */

public class ToastUtil {

    @IntDef({Toast.LENGTH_SHORT, Toast.LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    private static Toast toast;
    private static TextView tv_message;

    public static void showToast(String content) {
        showToast(content, Toast.LENGTH_SHORT);
    }

    public static void showToast(String content, @Duration int duration) {
        showToast(content, duration, Gravity.BOTTOM);
    }

    public static void showToast(String content, @Duration int duration, int gravity) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getAppContext(), content, duration);
            View view = LayoutInflater.from(BaseApplication.getAppContext()).inflate(R.layout.view_toast, null);
            tv_message = (TextView) view.findViewById(R.id.tv_message);
            toast.setView(view);
        }
        toast.setGravity(gravity, 0, gravity == Gravity.BOTTOM ? DisplayUtil.dip2px(40) : 0);
        tv_message.setText(content);
        toast.setDuration(duration);
        toast.show();
    }


}
