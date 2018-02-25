package com.asus.cloudmusic.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.asus.cloudmusic.Activity.MusicList;
import com.asus.cloudmusic.Bean.PlayUrl;
import com.asus.cloudmusic.Util.DataBase_Util;
import com.asus.cloudmusic.Util.MusicData_Util;
import com.asus.cloudmusic.Util.Text_Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by asus on 2018/1/28.
 */

public class PlayerService extends Service{
    private List<PlayUrl> data;
    private Context context;
    private MediaPlayer mediaPlayer;
    private MusicData_Util musicUtil;
    private DataBase_Util dataUtil;
    private int pos,mode;
    private Random rand;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initUtil();
        initData();
        initMedia();
    }

    private void initData() {
        pos=0;
        mode=0;
        rand=new Random();
        data=new ArrayList<>();
        data=dataUtil.getLocalMusic();
    }
    private void initUtil(){
        musicUtil=new MusicData_Util(this.getContentResolver(),this);
        dataUtil=new DataBase_Util(this);
    }

    private void initMedia() {
        mediaPlayer=new MediaPlayer();
        try {
            mediaPlayer.setDataSource(data.get(pos).getUrl());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                if (mode==0){
                    if (pos==data.size()-1){
                        pos=0;
                    }else {
                        pos++;
                    }
                }else if (mode==2){
                    int next=rand.nextInt(data.size());
                    while (next==pos){
                        next=rand.nextInt(data.size());
                    }
                    pos=next;
                }
                try {
                    mp.setDataSource(data.get(pos).getUrl());
                    mp.prepare();
                    mp.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        mediaPlayer=null;
    }

    public class MusicController extends Binder{
        public void play(){
            mediaPlayer.start();
        }
        public void pause(){
            mediaPlayer.pause();
        }
        public void setMode(int m){
            mode=m;
        }

        public void changeSong(int n){
            pos=n;
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(data.get(pos).getUrl());
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public String getMusicTime(){
            return Text_Util.getTime(mediaPlayer.getDuration());
        }
        public long getProcess(){
            return mediaPlayer.getCurrentPosition();
        }
        public void setProcess(int pos){
            mediaPlayer.seekTo(pos);
        }
    }
}
