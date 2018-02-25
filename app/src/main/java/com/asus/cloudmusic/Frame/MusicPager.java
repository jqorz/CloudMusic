package com.asus.cloudmusic.Frame;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.asus.cloudmusic.Activity.MusicList;
import com.asus.cloudmusic.Adapter.ExpendedListAdapter;
import com.asus.cloudmusic.Adapter.GuideRvAdapter;
import com.asus.cloudmusic.Base.BaseFragment;
import com.asus.cloudmusic.R;
import com.asus.cloudmusic.View.ExpandList;


public class MusicPager extends BaseFragment implements GuideRvAdapter.ItemClickListener {

    private RecyclerView rvGuidelist;
    private ExpandList expandlist;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {
        rvGuidelist = (RecyclerView) getView().findViewById(R.id.guide_list);
        expandlist = (ExpandList) getView().findViewById(R.id.expandlist);
        rvGuidelist.setLayoutManager(new LinearLayoutManager(getFragContext(), LinearLayoutManager.VERTICAL, false));
        GuideRvAdapter guideRvAdapter = new GuideRvAdapter(getFragContext());
        ExpendedListAdapter expandedListAdapter = new ExpendedListAdapter(getFragContext());
        guideRvAdapter.setItemClickListener(this);
        rvGuidelist.setAdapter(guideRvAdapter);
        expandlist.setAdapter(expandedListAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initUtil() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frag_music;
    }

    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent();
        intent.setClass(getFragContext(), MusicList.class);
        startActivity(intent);

    }
}
