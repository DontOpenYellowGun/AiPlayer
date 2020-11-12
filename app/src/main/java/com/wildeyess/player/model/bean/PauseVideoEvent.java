package com.wildeyess.player.model.bean;

/**
 * create by wildeyess
 * create on 2020-10-21
 * description
 */
public class PauseVideoEvent {
    private boolean playOrPause;

    public PauseVideoEvent(boolean playOrPause) {
        this.playOrPause = playOrPause;
    }

    public boolean isPlayOrPause() {
        return playOrPause;
    }
}
