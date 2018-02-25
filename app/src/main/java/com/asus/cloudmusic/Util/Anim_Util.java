package com.asus.cloudmusic.Util;

import android.view.animation.AlphaAnimation;

/**
 * Created by asus on 2018/1/23.
 */

public class Anim_Util {
    public static AlphaAnimation alphaAnimation;
    public static AlphaAnimation appear(){
        alphaAnimation=new AlphaAnimation(0,1);
        alphaAnimation.setDuration(500);
        return alphaAnimation;
    }

    public static AlphaAnimation disappear(){
        alphaAnimation=new AlphaAnimation(1,0);
        alphaAnimation.setDuration(500);
        return alphaAnimation;
    }
}
