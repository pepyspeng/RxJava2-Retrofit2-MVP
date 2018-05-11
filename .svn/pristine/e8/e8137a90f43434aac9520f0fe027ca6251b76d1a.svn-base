package com.tapup.data.api;

import com.tapup.data.bean.BaseBean;
import com.tapup.data.bean.ChoiceBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by zhupp on 2017/12/19.
 */

public interface IChoiceApi {

    @FormUrlEncoded
    @POST("weixin/query")
    Observable<BaseBean<ChoiceBean>> getChoice(@FieldMap HashMap<String, String> paramsMap);

    @FormUrlEncoded
    @POST("{path}")
    Observable<BaseBean<ChoiceBean>> getChoice(@Path("path") String path,@FieldMap HashMap<String, String> paramsMap);
}
