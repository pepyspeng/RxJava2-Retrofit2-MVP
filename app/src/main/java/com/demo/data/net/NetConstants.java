package com.demo.data.net;

import com.demo.base.BaseApplication;
import com.orhanobut.logger.Logger;

import java.io.File;

import static com.demo.common.enums.EnvironmentType.ENV_DEBUG;
import static com.demo.common.enums.EnvironmentType.ENV_INTEGRATION;
import static com.demo.common.enums.EnvironmentType.ENV_RELEASE;

/**
 * Created by zhupp on 2017/12/8.
 */

public class NetConstants {

    /**
     * 修改retrofit的baseurl不太方便,所以针对不同baseurl创建不同的实例;
     * 这里的count=3个内部环境(开发,集成,正式)
     */
    public static final int TYPE_COUNT = 3;

    //debug     测试环境
    public static final String DEBUG_BASE_URL = "http://v.juhe.cn/";
    //pre       预上线
    public static final String INTEGRATION_BASE_URL = "http://test1.wootide.net/globalTourism/";
    //release   正式环境
    public static final String RELEASE_BASE_URL = "http://110.53.51.220:8188/zjjqyt/";

    /**
     * 获取对应的host
     *
     * @param envType host类型
     * @return host
     */
    public static String getBaseUrl(int envType) {
        String host = "";
        switch (envType) {
            case ENV_DEBUG:
                host = DEBUG_BASE_URL;
                break;
            case ENV_INTEGRATION:
                host = INTEGRATION_BASE_URL;
                break;
            case ENV_RELEASE:
                host = RELEASE_BASE_URL;
                break;
            default:
                Logger.d("其他环境");
                break;
        }
        return host;
    }

    //缓存文件地址
    public static final File CACHE_DIR = BaseApplication.getAppContext().getCacheDir();

    //缓存文件名称,改文件放置在{@link App.getAppContext().getCacheDir()}下
    public static final String CACHE_FILE_NAME = "Cache";

    //HTTP缓存文件大小,改文件不包含图片
    public static final long HTTP_CACHE_SIZE = 1024 * 1024 * 100;

    //设缓存stale时长为两天
    public static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

    //设缓存age时长为0,表示不用换乘
    public static final long CACHE_AGE_SEC = 0;

    //连接超时
    public static final int CONNECT_TIME_OUT = 30;

    //读取超时
    public static final int READ_TIME_OUT = 30;

    //写入超时
    public static final int WRITE_TIME_OUT = 30;
}
