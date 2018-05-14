package com.tapup.data.bean;

/**
 * Created by pepys on 2018/5/13.
 * 赛事列表bean
 */

public class CompetitionBean {
    private String title;

    public CompetitionBean(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
