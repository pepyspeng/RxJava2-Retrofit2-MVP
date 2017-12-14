package com.demo.base;

import android.support.annotation.NonNull;


/**
 * Created by pepys on 2017/5/26.
 * description:Contract基类
 */
public interface IBaseContract {
    interface View {
        /**
         * 显示loading
         */
        void showProgress();
        /**
         * 隐藏loading
         */
        void hideProgress();
    }

    interface Presenter<T> {
        /**
         * 初始化操作
         */
        void onStart();
       /**
         * 关联View
         * @param view
         */
        void attachView(@NonNull T view);
        /**
         * 防止内存泄漏，在presneter销毁时同事销毁view
         */
        void onDestroy();
    }
}
