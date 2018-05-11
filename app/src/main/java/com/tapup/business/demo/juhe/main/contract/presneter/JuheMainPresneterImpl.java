package com.tapup.business.demo.juhe.main.contract.presneter;

import com.tapup.base.BasePresenterImpl;
import com.tapup.business.demo.juhe.main.contract.IJuheMainContract;
import com.tapup.common.Config;
import com.tapup.data.bean.BaseBean;
import com.tapup.data.bean.ChoiceBean;
import com.tapup.data.interactorImpl.ChoiceInteractorImpl;

import java.util.HashMap;

/**
 * Created by pepys on 2017/12/15
 * description:
 */
public class JuheMainPresneterImpl extends BasePresenterImpl<IJuheMainContract.View> implements IJuheMainContract.Presenter {

    /**
     * 持有view引用
     */
    private IJuheMainContract.View view;
    /**
     * 持有model引用
     */
    private IJuheMainContract.interactor interactor = new ChoiceInteractorImpl();

    public JuheMainPresneterImpl(IJuheMainContract.View view) {
        this.view = view;
    }


    @Override
    public void onStart() {

    }

    @Override
    public void getChoiceList() {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("pno", "");//当前页数，默认1
        paramsMap.put("ps", "");//每页返回条数，最大100，默认20
        paramsMap.put("key", Config.JUHE_KEY);//应用APPKEY(应用详细页查询)
        paramsMap.put("dtype", "");//返回数据的格式,xml或json，默认json

        interactor.getChoiceList(new RequestCallBackAdapter<BaseBean<ChoiceBean>>() {
            @Override
            public void success(BaseBean<ChoiceBean> data) {
                view.choiceSuccess(data.getResult());
            }
        },paramsMap );
    }
}
