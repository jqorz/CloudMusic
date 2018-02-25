package com.asus.cloudmusic.Util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.asus.cloudmusic.Bean.LocalSong;

import static com.asus.cloudmusic.Util.Text_Util.getPinYin;

/**
 * Created by asus on 2018/1/21.
 */

public class MusicData_Util {
    private ContentResolver contentResolver;
    private DataBase_Util dataBase_util;
    public MusicData_Util(ContentResolver contentResolver, Context context){
        this.contentResolver=contentResolver;
        dataBase_util=new DataBase_Util(context);
    }
    public void initSongs(){
        String[] keys=new String[]{MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,//1
                MediaStore.Audio.Media.TITLE,//2
                MediaStore.Audio.Media.DURATION,//3
                MediaStore.Audio.Media.ARTIST,//4
                MediaStore.Audio.Media.ALBUM,//5
                MediaStore.Audio.Media.SIZE,//6
                MediaStore.Audio.Media.DATA,//7
                MediaStore.Audio.Media.ALBUM_ID};//8
        Cursor cursor=contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,keys,null,null,null);
        if(cursor!=null&&cursor.moveToFirst()){
            LocalSong localSong;
            do {
                localSong =new LocalSong();
                localSong.setFileName(cursor.getString(1));//获取文件名
                localSong.setTitle(cursor.getString(2));//获取歌曲名
                localSong.setSinger(cursor.getString(4));//获取歌手
                localSong.setAlbum(cursor.getString(5));//获取专辑名
                if (cursor.getString(7) != null) {// 获取文件大小
                    float size = cursor.getInt(6) / 1024f / 1024f;
                    localSong.setSize((size + "").substring(0, (size + "").lastIndexOf('.') + 2) + "M");
                } else {
                    localSong.setSize("未知");
                }
                if (cursor.getString(7) != null) {//获取文件路径
                    localSong.setFileUrl(cursor.getString(8));
                }
                if (cursor.getInt(8) != 0) {//获取专辑id
                    localSong.setId(cursor.getInt(8));
                }
                String s=localSong.getTitle();
                String s1,s2;
                s2=Text_Util.getPinYinFirstLetter(s);
                localSong.setHeadChar(s2);
                if (s2.equals("#") ) {
                    localSong.setsChar("#");
                }else {
                    s1= getPinYin(s).toLowerCase();
                    localSong.setsChar(s1);

                }
                dataBase_util.AddData(localSong);
            }while (cursor.moveToNext());
            cursor.close();
        }
    }

    public String getAlbumArt(int album_id)
    {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[] { "album_art" };
        Cursor cur = contentResolver.query(  Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)),  projection, null, null, null);
        String album_art = null;
        if (cur != null && cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
        }
        if (cur != null) {
            cur.close();
        }
        return album_art;
    }


    public void destroyUtil(){
        dataBase_util.Destroy();
    }
}
