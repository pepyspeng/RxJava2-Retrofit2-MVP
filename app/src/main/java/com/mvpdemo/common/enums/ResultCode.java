package com.mvpdemo.common.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by pepys on 2017/12/11
 * description: 定义状态码
 *
 */
@IntDef({ResultCode.NET_ERROR, ResultCode.SERVICE_ERROR, ResultCode.DATABASE_ERROR,
        ResultCode.SUCCESS, ResultCode.SUCCESS_NO_MORE_DATA, ResultCode.ARGUMENT_ERROR,
        ResultCode.SIGN_ERROR, ResultCode.INTERFACE_ERROR})
@Retention(RetentionPolicy.SOURCE)
public @interface ResultCode {
    //************通用接口状态说明************
    //成功
    int SUCCESS = 0;//'操作成功'
    int SUCCESS_NO_MORE_DATA = 0x111110;//请求成功,但是已经到达最后一页  0<data.size<maxSize

    //失败类型
    int NET_ERROR = -1; //网络异常
    int SERVICE_ERROR = -2;//服务器异常
    int DATABASE_ERROR = -3;//数据库异常

    int ARGUMENT_ERROR = 1;//'参数错误'
    int SIGN_ERROR = 2;//'签名失败'
    int INTERFACE_ERROR = 3;//'接口异常'


}
