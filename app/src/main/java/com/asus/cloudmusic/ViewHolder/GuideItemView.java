package com.asus.cloudmusic.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asus.cloudmusic.R;


/**
 * Created by asus on 2018/1/21.
 */

public class GuideItemView extends RecyclerView.ViewHolder{
    public int[] imgs = {R.drawable.guide_music, R.drawable.guide_lastplay, R.drawable.guide_collect};
    public ImageView ivGuide;
    public TextView tvNumber;
    public TextView tvTitle;
    public GuideItemView(View itemView) {
        super(itemView);
        tvTitle= (TextView) itemView.findViewById(R.id.tv_title);
        tvNumber= (TextView) itemView.findViewById(R.id.tv_number);
        ivGuide= (ImageView) itemView.findViewById(R.id.iv_guide);
    }
}
