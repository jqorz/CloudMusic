package com.asus.cloudmusic.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.asus.cloudmusic.Frame.LifePager;
import com.asus.cloudmusic.Frame.MusicPager;

/**
 * Created by asus on 2018/1/20.
 */

public class FragPagerAdapter extends FragmentPagerAdapter {

    public FragPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new LifePager();
        } else {
            return new MusicPager();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
