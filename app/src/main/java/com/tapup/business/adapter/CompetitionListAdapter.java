package com.tapup.business.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tapup.R;
import com.tapup.base.BaseApplication;
import com.tapup.data.bean.CompetitionBean;

import java.util.List;

/**
 * Created by pepys on 2018/5/13.
 * 赛事列表adapter
 */

public class CompetitionListAdapter extends BaseQuickAdapter<CompetitionBean,BaseViewHolder> {

    private int fragmentType;
    private int compertitionTypeImgResID;
    private int compertitionTypeTvResID;
    private int compertitionTypeTvColorResID;
    public void setFragmentType(int fragmentType) {
        this.fragmentType = fragmentType;
    }

    public CompetitionListAdapter(int layoutResId, @Nullable List<CompetitionBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CompetitionBean item) {
        switch (fragmentType){
            case 1:
                compertitionTypeImgResID = R.mipmap.icon_competition_signup;
                compertitionTypeTvResID = R.string.competition_signup;
                compertitionTypeTvColorResID = R.color.c0098ff;
                break;
            case 2:
                compertitionTypeImgResID = R.mipmap.icon_competition_signuping;
                compertitionTypeTvResID = R.string.competition_signuping;
                compertitionTypeTvColorResID = R.color.c_main_red;
                break;
            case 3:
                compertitionTypeImgResID = R.mipmap.icon_competition_doing;
                compertitionTypeTvResID = R.string.competition_doning;
                compertitionTypeTvColorResID = R.color.c01c97c;
                break;
            case 4:
                compertitionTypeImgResID = R.mipmap.icon_competition_end;
                compertitionTypeTvResID = R.string.competition_end;
                compertitionTypeTvColorResID = R.color.c_main_text;
                break;
        }
        helper.setImageResource(R.id.competition_adapter_img_type,compertitionTypeImgResID)
                .setText(R.id.competition_adapter_tv_type,compertitionTypeTvResID)
                .setText(R.id.competition_adapter_tv_title,item.getTitle())
                .setTextColor(R.id.competition_adapter_tv_type, BaseApplication.getResource().getColor(compertitionTypeTvColorResID));
    }
}
