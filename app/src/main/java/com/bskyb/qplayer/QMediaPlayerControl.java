package com.bskyb.qplayer;

import android.view.View;

import com.google.android.exoplayer.ExoPlayer;

/**
 * Created by jco59 on 10/02/2016.
 */
public class QMediaPlayerControl implements MediaPlayerControl {


    private final View videoSurfaceContainer;
    private ExoPlayer player;
    private boolean isFullScreen;

    public QMediaPlayerControl(View videoSurfaceContainer, ExoPlayer player) {
        this.videoSurfaceContainer = videoSurfaceContainer;
        this.player = player;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public boolean isFullScreen() {
        return isFullScreen;
    }

    @Override
    public void toggleFullScreen() {
        isFullScreen = !isFullScreen;
        if (isFullScreen) {
            videoSurfaceContainer.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            videoSurfaceContainer.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_VISIBLE
            );
        }
    }


    @Override
    public int getBufferPercentage() {
        return player.getBufferedPercentage();
    }

    @Override
    public int getCurrentPosition() {
        return player.getDuration() == ExoPlayer.UNKNOWN_TIME ? 0
                : (int) player.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return player.getDuration() == ExoPlayer.UNKNOWN_TIME ? 0
                : (int) player.getDuration();
    }

    @Override
    public boolean isPlaying() {
        return player.getPlayWhenReady();
    }

    @Override
    public void start() {
        player.setPlayWhenReady(true);
    }

    @Override
    public void pause() {
        player.setPlayWhenReady(false);
    }

    @Override
    public void seekTo(int timeMillis) {
        long seekPosition = player.getDuration() == ExoPlayer.UNKNOWN_TIME ? 0
                : Math.min(Math.max(0, timeMillis), getDuration());
        player.seekTo(seekPosition);
    }
}
