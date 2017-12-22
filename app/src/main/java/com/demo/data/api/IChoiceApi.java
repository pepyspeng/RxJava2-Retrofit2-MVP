package com.demo.data.api;

import com.demo.data.bean.BaseBean;
import com.demo.data.bean.ChoiceBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhupp on 2017/12/19.
 */

public interface IChoiceApi {

    @FormUrlEncoded
    @POST("weixin/query")
    Observable<BaseBean<ChoiceBean>> getChoice(@FieldMap HashMap<String, String> paramsMap);
}
