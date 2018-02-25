package com.asus.cloudmusic.Activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.asus.cloudmusic.Base.BaseActivity;
import com.asus.cloudmusic.Bean.LocalSong;
import com.asus.cloudmusic.R;
import com.asus.cloudmusic.Util.DataBase_Util;
import com.asus.cloudmusic.Util.MusicData_Util;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by asus on 2018/1/27.
 */

public class MusicPlayer extends BaseActivity {
    private SlidingUpPanelLayout slidingUpView;
    private RelativeLayout rlClosePop;
    private ImageView btMusicList, ivBackground;
    private MusicData_Util musicDataUtil;
    private DataBase_Util dataBaseUtil;
    private int pos;
    private List<LocalSong> data;

    @Override
    protected void initEvent() {
        btMusicList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slidingUpView.getPanelState() != SlidingUpPanelLayout.PanelState.EXPANDED) {
                    slidingUpView.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    rlClosePop.setVisibility(View.VISIBLE);
                }
            }
        });
        rlClosePop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slidingUpView.getPanelState() != SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    slidingUpView.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    rlClosePop.setVisibility(View.INVISIBLE);
                }
            }
        });
        slidingUpView.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    rlClosePop.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void initBackground(int n) {
        String path = musicDataUtil.getAlbumArt(data.get(n).getId());
        if (path != null) {
            Glide.with(this).load(path)
                    .bitmapTransform(new BlurTransformation(this, 225), new CenterCrop(this))
                    .into(ivBackground);
        } else {
            Glide.with(this).load(R.drawable.unknown)
                    .bitmapTransform(new BlurTransformation(this, 225), new CenterCrop(this))
                    .into(ivBackground);
        }
    }

    @Override
    protected void initView() {
        slidingUpView = (SlidingUpPanelLayout) findViewById(R.id.player_sliding);
        rlClosePop = (RelativeLayout) findViewById(R.id.player_close_pop);
        btMusicList = (ImageView) findViewById(R.id.player_bt_list);
        ivBackground = (ImageView) findViewById(R.id.player_iv_background);
        initBackground(pos);
        rlClosePop.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData() {
        pos = 0;
        data = dataBaseUtil.getAllSortedData();
    }

    @Override
    protected void initUtil() {
        musicDataUtil = new MusicData_Util(getContentResolver(), this);
        dataBaseUtil = new DataBase_Util(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.musicplayer;
    }
}
