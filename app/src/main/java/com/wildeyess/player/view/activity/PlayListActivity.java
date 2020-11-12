package com.wildeyess.player.view.activity;

import com.wildeyess.player.R;
import com.wildeyess.player.base.BaseActivity;
import com.wildeyess.player.view.fragment.RecommendFragment;

/**
 * create by wildeyess
 * create on 2020-10-24
 * description 视频全屏播放页
 */
public class PlayListActivity extends BaseActivity {
    public static int initPos;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_play_list;
    }

    @Override
    protected void init() {
        getSupportFragmentManager().beginTransaction().add(R.id.framelayout, new RecommendFragment()).commit();
    }
}
