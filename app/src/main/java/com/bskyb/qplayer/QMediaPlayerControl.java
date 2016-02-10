package com.bskyb.qplayer;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.google.android.exoplayer.ExoPlayer;

/**
 * Created by jco59 on 10/02/2016.
 */
public class QMediaPlayerControl implements MediaPlayerControl {


    private final View videoSurfaceContainer;
    private final WindowManager windowManager;
    private ExoPlayer player;
    private boolean isFullScreen;

    public QMediaPlayerControl(View videoSurfaceContainer, WindowManager windowManager, ExoPlayer player) {
        this.videoSurfaceContainer = videoSurfaceContainer;
        this.windowManager = windowManager;
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
/*
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        android.widget.FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) videoSurfaceContainer.getLayoutParams();
        params.width = metrics.widthPixels;
        params.height = metrics.heightPixels;
        params.leftMargin = 0;
        videoSurfaceContainer.setLayoutParams(params);
*/
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
