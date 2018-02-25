package com.asus.cloudmusic.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.asus.cloudmusic.R;

/**
 * Created by asus on 2018/1/23.
 */

public class MusicListItemView extends RecyclerView.ViewHolder{

    public TextView title,album;
    public MusicListItemView(View itemView) {
        super(itemView);
        title= (TextView) itemView.findViewById(R.id.songName);
        album= (TextView) itemView.findViewById(R.id.albumName);
    }
}
