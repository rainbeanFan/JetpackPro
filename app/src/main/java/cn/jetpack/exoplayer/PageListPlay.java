package cn.jetpack.exoplayer;

import android.app.Application;
import android.view.LayoutInflater;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import cn.commonlibrary.global.AppGlobal;
import cn.jetpack.R;

public class PageListPlay {
    public ExoPlayer exoPlayer;
    public StyledPlayerView playerView;
    public PlayerControlView controlView;
    public String playUrl;

    public PageListPlay() {
        Application application = AppGlobal.getApplication();
        //创建exoplayer播放器实例
        exoPlayer = new ExoPlayer.Builder(application)
                .setRenderersFactory(new DefaultRenderersFactory(application))
                .setTrackSelector(new DefaultTrackSelector(application))
                .setLoadControl(new DefaultLoadControl())
                .build();


        //加载咱们布局层级优化之后的能够展示视频画面的View
        playerView = (StyledPlayerView) LayoutInflater.from(application).inflate(R.layout.layout_exo_player_view, null, false);

        //加载咱们布局层级优化之后的视频播放控制器
        controlView = (PlayerControlView) LayoutInflater.from(application).inflate(R.layout.layout_exo_player_contorller_view, null, false);

        //别忘记 把播放器实例 和 playerView，controlView相关联
        //如此视频画面才能正常显示,播放进度条才能自动更新
        playerView.setPlayer(exoPlayer);
        controlView.setPlayer(exoPlayer);
    }

    public void release() {

        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(false);
            exoPlayer.stop(true);
            exoPlayer.release();
            exoPlayer = null;
        }

        if (playerView != null) {
            playerView.setPlayer(null);
            playerView = null;
        }

        if (controlView != null) {
            controlView.setPlayer(null);
//            controlView.setVisibilityListener(null);
            controlView = null;
        }
    }

    /**
     * 切换与播放器exoplayer 绑定的exoplayerView。用于页面切换视频无缝续播的场景
     *
     * @param newPlayerView
     * @param attach
     */
    public void switchPlayerView(StyledPlayerView newPlayerView, boolean attach) {
        playerView.setPlayer(attach ? null : exoPlayer);
        newPlayerView.setPlayer(attach ? exoPlayer : null);
    }
}