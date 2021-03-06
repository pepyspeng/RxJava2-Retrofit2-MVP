package com.tapup.business.demo.juhe.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.tapup.R;
import com.tapup.base.BaseActivity;
import com.tapup.base.GlideApp;
import com.tapup.base.IBaseContract;
import com.tapup.business.demo.juhe.main.contract.IJuheMainContract;
import com.tapup.business.demo.juhe.main.contract.presneter.JuheMainPresneterImpl;
import com.tapup.data.bean.ChoiceBean;
import com.tapup.utils.recycleHelper.adapter.BaseRecyclerAdapter;
import com.tapup.utils.recycleHelper.adapter.SmartViewHolder;

import butterknife.BindView;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class ChoiceActivity extends BaseActivity implements IJuheMainContract.View,AdapterView.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.choice_recycle)
    RecyclerView choiceRecycle;
    private IJuheMainContract.Presenter presenter;

    private ChoiceBean mChoiceBean;

    @Override
    protected IBaseContract.Presenter createPresenter() {
        presenter = new JuheMainPresneterImpl(this);
        return presenter;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_choice;
    }

    public static void callActivity(Context context) {
        Intent intent = new Intent(context, ChoiceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.getChoiceList();

    }


    @Override
    protected void init() {
        setSupportActionBar(toolbar);

        choiceRecycle.setLayoutManager(new LinearLayoutManager(this));
        choiceRecycle.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
        choiceRecycle.setItemAnimator(new DefaultItemAnimator());
    }



    @Override
    public void choiceSuccess(ChoiceBean choiceList) {
        mChoiceBean = choiceList;
        choiceRecycle.setAdapter(new BaseRecyclerAdapter<ChoiceBean.ListBean>(mChoiceBean.getList(),R.layout.item_choice,this) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, ChoiceBean.ListBean model, int position) {

                holder.text(R.id.tv_title_content,model.getTitle())
                        .text(R.id.tv_title_desp,model.getTitle());
                ImageView imageView = (ImageView) holder.findViewById(R.id.choice_img);
                GlideApp.with(ChoiceActivity.this).load(model.getFirstImg()).into(imageView);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }


}
