package com.tapup.data.api;


import com.tapup.data.bean.BaseBean;
import com.tapup.data.bean.LoginBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by zhupp on 2017/12/11.
 */

public interface ILoginApi {


    @POST()
    @FormUrlEncoded
    Observable<BaseBean<LoginBean>> login( @FieldMap HashMap<String, String> paramsMap);

    @POST("{[path}")
    @FormUrlEncoded
    Observable<BaseBean<LoginBean>> login(@Path("path")String path, @FieldMap HashMap<String, String> paramsMap);
}
