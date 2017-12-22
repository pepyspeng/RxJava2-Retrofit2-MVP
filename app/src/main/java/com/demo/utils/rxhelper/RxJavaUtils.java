/*
 * Copyright (c) 2016 shawn <kaku201313@163.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.demo.utils.rxhelper;

import com.demo.R;
import com.demo.common.enums.ResultCode;
import com.demo.data.bean.BaseBean;
import com.demo.data.bean.NoDataBean;
import com.demo.exception.RxException;
import com.demo.utils.ErrorUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author shawn
 * @version 1.0 2016/8/20
 */
public class RxJavaUtils {

    // 对API调用了observeOn(MainThread)之后，线程会跑在主线程上，包括onComplete也是，
    // unsubscribe也在主线程，然后如果这时候调用call.cancel会导致NetworkOnMainThreadException
    // 加一句unsubscribeOn(io)
    public static <T> ObservableTransformer<T, T> defaultSchedulers() {
        return new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.unsubscribeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }

        };
    }


    public static <T> Function<BaseBean<T>, ObservableSource<BaseBean<T>>> defaultBaseFlatMap() {
        return new Function<BaseBean<T>, ObservableSource<BaseBean<T>>>() {
            @Override
            public ObservableSource<BaseBean<T>> apply(BaseBean<T> base) {
                //异常处理
                if (base.getStatus() == ResultCode.SUCCESS || base.getStatus() == ResultCode.SUCCESS_NO_MORE_DATA ) {
                    return Observable.just(base);
                } else {
                    String errorMessage = ErrorUtils.getErrorMessage(base, base.getStatus());
                    return Observable.error(new RxException(base.getStatus(), errorMessage));
                }
            }
        };
    }

    public static Function<NoDataBean, ObservableSource<NoDataBean>> defaultNoDataFlatMap() {
        return new Function<NoDataBean, ObservableSource<NoDataBean>>() {
            @Override
            public ObservableSource<NoDataBean> apply(NoDataBean base) {
                //异常处理
                if (base.getStatus() == ResultCode.SUCCESS || base.getStatus() == ResultCode.SUCCESS_NO_MORE_DATA) {
                    return Observable.just(base);
                } else {
                    String errorMessage = ErrorUtils.getErrorMessage(base, R.string.internet_error);
                    return Observable.error(new RxException(base.getStatus(), errorMessage));
                }
            }
        };
    }

    public static void cancelSubscription(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * 二次处理observable
     *
     * @param observable
     * @param responseObserver
     * @param <T>
     * @return
     */
    public static <T> Disposable getDisposable(Observable<BaseBean<T>> observable, final DefaultObserver<BaseBean<T>> responseObserver) {

        return observable
                .compose(RxJavaUtils.<BaseBean<T>>defaultSchedulers())
                .flatMap(RxJavaUtils.<T>defaultBaseFlatMap())
                .subscribe(new Consumer<BaseBean<T>>() {
                    @Override
                    public void accept(BaseBean<T> t) throws Exception {
                        if (responseObserver != null) responseObserver.onNext(t);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (responseObserver != null) responseObserver.onError(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        if (responseObserver != null) responseObserver.onComplete();
                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (responseObserver != null) responseObserver.onSubscribe(disposable);
                    }
                });
    }

}
