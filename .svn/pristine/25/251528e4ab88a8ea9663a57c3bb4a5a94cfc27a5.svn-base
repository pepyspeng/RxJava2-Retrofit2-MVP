/*
 * Copyright (c) 2016 shawn <shawn0729@foxmail.com>
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
package com.tapup.utils.rxhelper;


import com.tapup.common.enums.ResultCode;

/**
 * @author shawn
 * @version 1.0 2016/5/29
 */
public interface RequestCallBack<T> {
    //注意,所有回调都在主线程执行,异步执行一般通过rxjava控制
    void beforeRequest();

    void success(T data);

    void onError(@ResultCode int resultCode, String errorMsg);

    void after();

}
