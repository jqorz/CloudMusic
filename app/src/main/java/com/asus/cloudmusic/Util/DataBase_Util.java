package com.asus.cloudmusic.Util;

import android.content.Context;

import com.asus.cloudmusic.Bean.LocalSong;
import com.asus.cloudmusic.Bean.PlayUrl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by asus on 2018/1/21.
 */

public class DataBase_Util {
    private Context context;
    private Realm realm;
    public DataBase_Util(Context context){
        this.context=context;
        Realm.init(context);
        realm=Realm.getDefaultInstance();
    }
    public void AddData(final LocalSong LocalSong){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(LocalSong);
            }
        });
    }

    public List<LocalSong> getAllData(){
        RealmResults<LocalSong> datas=realm.where(LocalSong.class).findAll();
        List<LocalSong> data=realm.copyFromRealm(datas);
        return data;
    }
    public List<LocalSong> getAllSortedData(){
        RealmResults<LocalSong> datas=realm.where(LocalSong.class).findAllSorted("sChar", Sort.ASCENDING);
        List<LocalSong> data=realm.copyFromRealm(datas);
        return data;
    }

    public List<PlayUrl> getLocalMusic(){
        RealmResults<LocalSong> datas=realm.where(LocalSong.class).findAllSorted("sChar", Sort.ASCENDING);
        List<LocalSong> data=realm.copyFromRealm(datas);
        List<PlayUrl> urls = new ArrayList<>();
        for(int i=0;i<data.size();i++){
            PlayUrl playUrl = new PlayUrl();
            playUrl.setUrl(data.get(i).getFileUrl());
            urls.add(playUrl);
        }
        return urls;
    }

    public void Destroy(){
        realm.close();
    }
}
