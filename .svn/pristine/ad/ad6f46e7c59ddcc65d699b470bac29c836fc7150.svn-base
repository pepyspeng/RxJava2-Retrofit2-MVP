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
package com.tapup.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tapup.utils.rxhelper.RxJavaUtils;


import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

/**
 * Created by pepys on 2018/5/12
 * description : 禁止左右滑动
 */

public abstract class BaseFragment extends Fragment {

    protected IBaseContract.Presenter superPresenter;
    protected boolean isVisible;//fragment是否可见
    //使用rxbux一定要注意讲subscription添加到cache中,否则会内存泄漏
    protected List<Disposable> rxBusCache = new LinkedList<>();
    protected View mFragmentView;

    public abstract void init(View view);

    public abstract int getLayoutId();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArgument();
        createPresenter();
    }

    /**
     * create presenter
     *
     * @return
     */
    protected abstract IBaseContract.Presenter createPresenter();

    //setUserVisibleHint  adapter中的每个fragment切换的时候都会被调用，如果是切换到当前页，那么isVisibleToUser==true，否则为false
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
        } else {
            isVisible = false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        if (mFragmentView == null) {
            mFragmentView = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, mFragmentView);
            init(mFragmentView);
        } else {
            ViewGroup parent = (ViewGroup) mFragmentView.getParent();
            if (null != parent) {
                parent.removeView(mFragmentView);
            }
        }
        return mFragmentView;
    }

    protected void getArgument(){

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (rxBusCache != null && rxBusCache.size() > 0) {
            for (Disposable disposable : rxBusCache) {
                RxJavaUtils.cancelSubscription(disposable);
            }
        }
        superPresenter = getBasePresenter();
        if (superPresenter != null) {
            superPresenter.onDestroy();
        }
    }

    public abstract IBaseContract.Presenter getBasePresenter();

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
