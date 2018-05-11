package com.tapup.business.demo.permission.contract.presneter;

import com.tapup.base.BasePresenterImpl;
import com.tapup.business.demo.permission.contract.IPermissionContract;
/**
 * Created by pepys on 2017/12/15
 * description:
 *
 */
public class PermissionPresneterImpl extends BasePresenterImpl<IPermissionContract.View> implements IPermissionContract.Presenter {

    /**
     * 持有view引用
     */
    private IPermissionContract.View view;
    /**
     * 持有model引用
     */
    private IPermissionContract.interactor interactor;

    public PermissionPresneterImpl(IPermissionContract.View view) {
        this.view = view;
    }



    @Override
    public void onStart() {
        //这里可以放入进界面就请求的
    }

}
