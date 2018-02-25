package com.asus.cloudmusic.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asus.cloudmusic.Adapter.BottomViewPagerAdapter;
import com.asus.cloudmusic.Adapter.LocalMusicAdapter;
import com.asus.cloudmusic.Base.BaseActivity;
import com.asus.cloudmusic.Bean.LocalSong;
import com.asus.cloudmusic.Constant.Text;
import com.asus.cloudmusic.R;
import com.asus.cloudmusic.Util.Anim_Util;
import com.asus.cloudmusic.Util.DataBase_Util;
import com.asus.cloudmusic.Util.MusicData_Util;
import com.asus.cloudmusic.View.SearchBar;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by asus on 2018/1/22.
 */

public class MusicList extends BaseActivity implements com.asus.cloudmusic.View.SearchBar.OnChooseChangeListener, ViewPager.OnPageChangeListener {

    private TextView tvHint, tvTitle;
    private RelativeLayout rlSearch, rlClosePop;
    private RecyclerView rvList;
    private ImageView btMusicList;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private SearchBar searchBar;
    private ViewPager bottomVpager;

    private List<View> viewList;
    private ImageView[] ivBottomPark;
    private TextView[] tvBottomTitle;
    private TextView[] tvBottomSong;
    private BottomViewPagerAdapter bottomViewPagerAdapter;
    private MusicData_Util musicDataUtil;
    private DataBase_Util dataBaseUtil;
    private RelativeLayout[] btClick;
    private int curPos;

    private ArrayMap<String, Integer> letters;
    private List<LocalSong> data;
    private LocalMusicAdapter localMusicAdapter;

    @Override
    protected void initEvent() {
        searchBar.setOnChooseChangeListener(this);
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_DRAGGING && rlSearch.getVisibility() == INVISIBLE) {
                    rlSearch.startAnimation(Anim_Util.appear());
                    rlSearch.setVisibility(View.VISIBLE);
                } else if (newState == SCROLL_STATE_SETTLING) {
                    rlSearch.setVisibility(View.VISIBLE);
                } else if (newState == SCROLL_STATE_IDLE && rlSearch.getVisibility() == VISIBLE) {
                    rlSearch.startAnimation(Anim_Util.disappear());
                    rlSearch.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                rlSearch.setVisibility(View.VISIBLE);
            }
        });

        btMusicList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slidingUpPanelLayout.getPanelState() != SlidingUpPanelLayout.PanelState.EXPANDED) {
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    rlClosePop.setVisibility(View.VISIBLE);
                }
            }
        });
        rlClosePop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slidingUpPanelLayout.getPanelState() != SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    rlClosePop.setVisibility(View.INVISIBLE);
                }
            }
        });
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
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

    private void initBottomViewPager() {
        LayoutInflater inflater = LayoutInflater.from(this);
        ivBottomPark = new ImageView[3];
        tvBottomSong = new TextView[3];
        tvBottomTitle = new TextView[3];
        btClick=new RelativeLayout[3];
        viewList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            View itemView = inflater.inflate(R.layout.viewpager_bottom_child, null);
            itemView.setLayoutParams(layoutParams);
            tvBottomTitle[i] = (TextView) itemView.findViewById(R.id.bottom_tv_title);
            tvBottomSong[i] = (TextView) itemView.findViewById(R.id.bottom_tv_song);
            ivBottomPark[i] = (ImageView) itemView.findViewById(R.id.bottom_iv_park);
            btClick[i]= (RelativeLayout) itemView.findViewById(R.id.bottom_click);
            btClick[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent();
                    intent.setClass(MusicList.this,MusicPlayer.class);
                    startActivity(intent);
                }
            });
            viewList.add(itemView);
        }
        bottomViewPagerAdapter = new BottomViewPagerAdapter(viewList, this, this.getContentResolver());
        bottomVpager.setAdapter(bottomViewPagerAdapter);
        initPager(0, data.size() - 1);
        initPager(1, 0);
        initPager(2, 1);
        bottomVpager.setCurrentItem(1);
    }

    private void initPager(int position, int n) {
        String path = musicDataUtil.getAlbumArt(data.get(n).getId());
        if (path != null) {
            Bitmap map = BitmapFactory.decodeFile(path);
            ivBottomPark[position].setImageBitmap(map);

        } else {
            ivBottomPark[position].setImageResource(R.drawable.unknown);
        }
        tvBottomSong[position].setText(data.get(n).getSinger());
        tvBottomTitle[position].setText(data.get(n).getTitle());
    }

    @Override
    protected void initView() {
        rlClosePop = (RelativeLayout) findViewById(R.id.closepop);
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.music_slidingup);
        btMusicList = (ImageView) findViewById(R.id.list_bt_musiclist);
        rlSearch = (RelativeLayout) findViewById(R.id.rl_search);
        bottomVpager = (ViewPager) findViewById(R.id.list_vp_album);
        tvHint = (TextView) findViewById(R.id.list_tv_hint);
        tvTitle = (TextView) findViewById(R.id.list_title);
        rvList = (RecyclerView) findViewById(R.id.list_rvlist);
        searchBar = (SearchBar) findViewById(R.id.list_searchview);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        localMusicAdapter = new LocalMusicAdapter(data, this);
        rvList.setAdapter(localMusicAdapter);
        tvTitle.setText(Text.guideTitles[0]);
        rlSearch.setVisibility(View.INVISIBLE);
        rlClosePop.setVisibility(View.INVISIBLE);
        initBottomViewPager();
        bottomVpager.setOnPageChangeListener(this);
    }

    @Override
    protected void initData() {
        data = dataBaseUtil.getAllSortedData();
        curPos = 0;
        letters = new ArrayMap<>();
        for (int i = 0; i < data.size(); i++) {
            if (!letters.containsKey(data.get(i).getHeadChar())) {
                letters.put(data.get(i).getHeadChar(), i);
            }
        }
    }

    private void selectRecyclerView(String s) {
        if (s.equals("#")) {
            rvList.scrollToPosition(0);
        } else {
            if (letters.containsKey(s)) {
                rvList.scrollToPosition(letters.get(s));
            }
        }
    }

    @Override
    protected void initUtil() {
        dataBaseUtil = new DataBase_Util(this);
        musicDataUtil = new MusicData_Util(this.getContentResolver(), this);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.musiclist;
    }


    @Override
    public void onChooseLetter(String letter) {
        if (!tvHint.isShown()) {
            tvHint.setVisibility(View.VISIBLE);
        }
        tvHint.setText(letter);
        selectRecyclerView(letter);
    }

    @Override
    public void onNoChooseLetter() {
        tvHint.setVisibility(View.GONE);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            curPos = (curPos - 1 + data.size()) % data.size();
            initPager(1, curPos);
        } else if (position == 2) {
            curPos = (curPos + 1) % data.size();
            initPager(1, curPos);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 0) {
            bottomVpager.setCurrentItem(1, false);
            int pos1 = (curPos - 1 + data.size()) % data.size();
            int pos2 = (curPos + 1) % data.size();
            initPager(0, pos1);
            initPager(2, pos2);
        }
    }
}
