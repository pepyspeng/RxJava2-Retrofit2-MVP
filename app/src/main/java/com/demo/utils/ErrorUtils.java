package com.demo.utils;

import android.accounts.NetworkErrorException;
import android.database.DatabaseErrorHandler;
import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
import com.demo.R;
import com.demo.base.BaseApplication;
import com.demo.common.enums.ResultCode;
import com.demo.data.bean.NoDataBean;
import com.orhanobut.logger.Logger;

import java.net.UnknownServiceException;

/**
 * Created by hxy on 2017/2/14.
 * class description:
 */

public class ErrorUtils {
    public static String getErrorMessage(NoDataBean baseBean, String defaultMsg) {
        switch (baseBean.getStatus()) {
            case ResultCode.ARGUMENT_ERROR:
                Logger.e("错误码:" + baseBean.getStatus() + ", 错误信息,参数错误");
                break;
            case ResultCode.DATABASE_ERROR:
                Logger.e("错误码:" + baseBean.getStatus() + ", 错误信息,数据库异常");
                break;
            case ResultCode.INTERFACE_ERROR:
                Logger.e("错误码:" + baseBean.getStatus() + ", 错误信息,接口异常");
                break;
            case ResultCode.NET_ERROR:
                Logger.e("错误码:" + baseBean.getStatus() + ", 错误信息,网络异常");
                break;
            case ResultCode.SERVICE_ERROR:
                Logger.e("错误码:" + baseBean.getStatus() + ", 错误信息,服务器异常");
                break;
            case ResultCode.SIGN_ERROR:
                Logger.e("错误码:" + baseBean.getStatus() + ", 错误信息,签名失败");
                break;
            case ResultCode.SUCCESS_NO_MORE_DATA:
                Logger.i("请求成功,但是已经到达最后一页");
                break;
            case ResultCode.SUCCESS:
                Logger.i("操作成功");
                break;
            default:
                Logger.e("错误码:" + baseBean.getStatus() + ", 错误信息:" + baseBean.getMessage());
                break;
        }
        return TextUtils.isEmpty(baseBean.getMessage()) ? defaultMsg : baseBean.getMessage();
    }

    public static String getErrorMessage(NoDataBean baseBean, int resId) {
        return getErrorMessage(baseBean, BaseApplication.getResourceString(resId));
    }

    public static String getExceptionMessage(Throwable e, String defaultMsg) {
        String message = "";
        if (e instanceof NetworkErrorException) {
            message = BaseApplication.getResourceString(R.string.internet_error);
        } else if (e instanceof DatabaseErrorHandler) {
            message = BaseApplication.getResourceString(R.string.db_error);
        } else if (e instanceof IllegalArgumentException) {
            message = BaseApplication.getResourceString(R.string.request_error);
        } else if (e instanceof JsonSyntaxException) {
            message = BaseApplication.getResourceString(R.string.resolve_error);
        }

        return TextUtils.isEmpty(message) ? defaultMsg : message;
    }

    @ResultCode
    public static int getExceptionCode(Throwable e) {
        int resultCode = ResultCode.NET_ERROR;
        if (e instanceof NetworkErrorException) {
            //默认
        } else if (e instanceof DatabaseErrorHandler) {
            resultCode = ResultCode.DATABASE_ERROR;
        } else if (e instanceof UnknownServiceException) {//服务器异常有多种
            resultCode = ResultCode.SERVICE_ERROR;
        }

        return resultCode;
    }
}
