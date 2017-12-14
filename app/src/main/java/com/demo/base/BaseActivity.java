package com.demo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.demo.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by pepyspeng on 2017/2/25.
 */

public abstract class BaseActivity<V,T extends IBaseContract.Presenter<V>> extends FragmentActivity {

    protected T mPresneter ;
    /**
     * create presenter
     * @return
     */
    protected abstract T createPresenter();

    protected Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*创建Presenter*/
        mPresneter = createPresenter();
        /*内存泄漏 ， 关联View*/
        mPresneter.attachView((V) this);
        setContentView(getLayoutID());
        ButterKnife.bind(this);
    }

    protected void showToast(String message){
        ToastUtil.showToast(message);
    }

    protected abstract int getLayoutID();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresneter.onDestroy();
        if(mUnbinder !=null){
            mUnbinder.unbind();
        }
}
    }
