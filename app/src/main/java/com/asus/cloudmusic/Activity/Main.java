package com.asus.cloudmusic.Activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asus.cloudmusic.Adapter.BottomViewPagerAdapter;
import com.asus.cloudmusic.Adapter.FragPagerAdapter;
import com.asus.cloudmusic.Base.BaseActivity;
import com.asus.cloudmusic.Bean.LocalSong;
import com.asus.cloudmusic.R;
import com.asus.cloudmusic.Service.PlayerService;
import com.asus.cloudmusic.Util.DataBase_Util;
import com.asus.cloudmusic.Util.MusicData_Util;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class Main extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, ServiceConnection {

    private PlayerService.MusicController control;
    private TabLayout.Tab music;
    private TabLayout.Tab life;
    private TabLayout tabLayout;
    private ViewPager vpPager, bottomVPager;
    private ImageView btSidemenu, btMusicList;
    private RelativeLayout rlClosePop;
    private RelativeLayout[] btIntent;
    private SlidingUpPanelLayout slidingUpView;
    private List<View> viewList;
    private List<LocalSong> localSongs;
    private ImageView[] ivBottomPark;
    private TextView[] tvBottomTitle;
    private TextView[] tvBottomSong;
    private BottomViewPagerAdapter bottomViewPagerAdapter;
    private DataBase_Util dataBaseUtil;
    private MusicData_Util musicDataUtil;
    private int curPos;

    @Override
    public void initUtil() {
        dataBaseUtil = new DataBase_Util(this);
        musicDataUtil = new MusicData_Util(this.getContentResolver(), this);
    }

    @Override
    public void initData() {
        localSongs = dataBaseUtil.getAllSortedData();
        curPos = 0;
        initService();
    }

    private void initService() {
        Intent intent = new Intent(this, PlayerService.class);
        startService(intent);
        bindService(intent, this, BIND_AUTO_CREATE);
    }

    @Override
    public void initView() {
        btMusicList = (ImageView) findViewById(R.id.bt_musiclist);
        btSidemenu = (ImageView) findViewById(R.id.bt_sidemenu);
        slidingUpView = (SlidingUpPanelLayout) findViewById(R.id.main_slindingupview);
        rlClosePop = (RelativeLayout) findViewById(R.id.main_closepop);
        vpPager = (ViewPager) findViewById(R.id.vp_pager);
        bottomVPager = (ViewPager) findViewById(R.id.list_vp_album);
        tabLayout = (TabLayout) findViewById(R.id.tab_pager);
        initBottomViewPager();
//        sideMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        vpPager.setAdapter(new FragPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(vpPager);
        music = tabLayout.getTabAt(0);
        life = tabLayout.getTabAt(1);
        music.setIcon(getResources().getDrawable(R.drawable.tab_music));
        life.setIcon(getResources().getDrawable(R.drawable.tab_life_unselect));
        rlClosePop.setVisibility(View.INVISIBLE);
//        sideMenu.setTouchModeAbove(TOUCHMODE_NONE);
    }

    private void initBottomViewPager() {
        LayoutInflater inflater = LayoutInflater.from(this);
        ivBottomPark = new ImageView[3];
        tvBottomSong = new TextView[3];
        tvBottomTitle = new TextView[3];
        btIntent = new RelativeLayout[3];
        viewList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            View itemView = inflater.inflate(R.layout.viewpager_bottom_child, null);
            itemView.setLayoutParams(layoutParams);
            tvBottomTitle[i] = (TextView) itemView.findViewById(R.id.bottom_tv_title);
            tvBottomSong[i] = (TextView) itemView.findViewById(R.id.bottom_tv_song);
            ivBottomPark[i] = (ImageView) itemView.findViewById(R.id.bottom_iv_park);
            btIntent[i] = (RelativeLayout) itemView.findViewById(R.id.bottom_click);
            btIntent[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(Main.this, MusicPlayer.class);
                    startActivity(intent);
                }
            });
            viewList.add(itemView);
        }
        bottomViewPagerAdapter = new BottomViewPagerAdapter(viewList, this, this.getContentResolver());
        bottomVPager.setAdapter(bottomViewPagerAdapter);
        initPager(0, localSongs.size() - 1);
        initPager(1, 0);
        initPager(2, 1);
        bottomVPager.setCurrentItem(1);
    }

    @Override
    public void initEvent() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == tabLayout.getTabAt(0)) {
                    music.setIcon(getResources().getDrawable(R.drawable.tab_music));
                } else if (tab == tabLayout.getTabAt(1)) {
                    life.setIcon(getResources().getDrawable(R.drawable.tab_life));
                    vpPager.setCurrentItem(1);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab == tabLayout.getTabAt(0)) {
                    music.setIcon(getResources().getDrawable(R.drawable.tab_music_unselect));
                } else if (tab == tabLayout.getTabAt(1)) {
                    life.setIcon(getResources().getDrawable(R.drawable.tab_life_unselect));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        btSidemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sideMenu.showMenu();
            }
        });
        btMusicList.setOnClickListener(this);
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
        bottomVPager.setOnPageChangeListener(this);
    }


    @Override
    public int getLayoutResId() {
        return R.layout.main;
    }

    @Override
    public void onClick(View v) {
        if (slidingUpView.getPanelState() != SlidingUpPanelLayout.PanelState.EXPANDED) {
            slidingUpView.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            rlClosePop.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            curPos = (curPos - 1 + localSongs.size()) % localSongs.size();
            initPager(1, curPos);
        } else if (position == 2) {
            curPos = (curPos + 1) % localSongs.size();
            initPager(1, curPos);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 0) {
            bottomVPager.setCurrentItem(1, false);
            int pos1 = (curPos - 1 + localSongs.size()) % localSongs.size();
            int pos2 = (curPos + 1) % localSongs.size();
            initPager(0, pos1);
            initPager(2, pos2);
        }
    }

    private void initPager(int position, int n) {
        String path = musicDataUtil.getAlbumArt(localSongs.get(n).getId());
        if (path != null) {
            Bitmap map = BitmapFactory.decodeFile(path);
            ivBottomPark[position].setImageBitmap(map);

        } else {
            ivBottomPark[position].setImageResource(R.drawable.unknown);
        }
        tvBottomSong[position].setText(localSongs.get(n).getSinger());
        tvBottomTitle[position].setText(localSongs.get(n).getTitle());
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        control= (PlayerService.MusicController) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        control=null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(this);

    }
}
