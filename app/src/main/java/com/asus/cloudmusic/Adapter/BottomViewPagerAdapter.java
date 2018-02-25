package com.asus.cloudmusic.Adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asus.cloudmusic.Bean.LocalSong;
import com.asus.cloudmusic.R;
import com.asus.cloudmusic.Util.DataBase_Util;
import com.asus.cloudmusic.Util.MusicData_Util;

import java.util.List;

/**
 * Created by asus on 2018/1/26.
 */

public class BottomViewPagerAdapter extends PagerAdapter {
    private List<View> list;
    private List<LocalSong> data;
    private DataBase_Util dataUtil;
    private Context context;
    private ImageView[] ivBottomPark;
    private TextView[] tvBottomTitle;
    private TextView[] tvBottomSong;
    private MusicData_Util musicUtil;
    public BottomViewPagerAdapter(List<View> list, Context context, ContentResolver c){
        this.list=list;
        this.context=context;
        ivBottomPark=new ImageView[3];
        tvBottomSong=new TextView[3];
        tvBottomTitle=new TextView[3];
        musicUtil=new MusicData_Util(c,context);
        initData();
    }

    private void initData() {
        dataUtil=new DataBase_Util(context);
        data=dataUtil.getAllSortedData();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

    private void initView(int position,int n){
        String path=musicUtil.getAlbumArt(data.get(n).getId());
        if (path!=null){
            Bitmap map= BitmapFactory.decodeFile(path);
            ivBottomPark[position].setImageBitmap(map);
        }
        tvBottomSong[position].setText(data.get(n).getSinger());
        tvBottomTitle[position].setText(data.get(n).getTitle());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        object=null;
    }
}
