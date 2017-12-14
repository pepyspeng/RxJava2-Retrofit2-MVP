package com.mvpdemo.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by hxy on 2016/5/20 0020. <p> description :
 */
public class DisplayUtil {
    /**
     * 屏幕密度缩放比例
     *
     * @return 原生的密度缩放比例
     */
    public static float getDensity() {
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        return dm.density;
    }

    /**
     * 屏幕字体密度缩放比例
     *
     * @return 原生的字体密度缩放比例
     */
    public static float getScaledDensity() {
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        return dm.scaledDensity;
    }


    /**
     * 获取屏幕的宽
     *
     * @return
     */
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕的高
     *
     * @return
     */
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }


    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(float pxValue) {
        return (int) (pxValue / Resources.getSystem().getDisplayMetrics().density + 0.5f);
    }


    public static int dip2px(float dipValue) {
        return (int) (dipValue * Resources.getSystem().getDisplayMetrics().density + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(float pxValue) {
        return (int) (pxValue / Resources.getSystem().getDisplayMetrics().scaledDensity + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        return (int) (spValue * Resources.getSystem().getDisplayMetrics().scaledDensity + 0.5f);
    }
}