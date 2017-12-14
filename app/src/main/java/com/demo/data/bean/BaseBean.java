package com.demo.data.bean;


/**
 * Created by pepys on 2017/12/11
 * description:
 *
 */
public class BaseBean<T> extends NoDataBean {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
