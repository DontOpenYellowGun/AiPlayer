package com.wildeyess.player.model.bean;

/**
 * create by wildeyess
 * create on 2020-10-21
 * description
 */
public class MainPageChangeEvent {
    private int page;

    public MainPageChangeEvent(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
