package com.tapup.data.bean;


import com.tapup.common.enums.ResultCode;

/**
 * Created by pepys on 2017/2/16.
 * description: 返回数据没有data
 */
public class NoDataBean {
    @ResultCode
    private int status;
    private String message;

    @ResultCode
    public int getStatus() {
        return status;
    }

    public void setStatus(@ResultCode int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
