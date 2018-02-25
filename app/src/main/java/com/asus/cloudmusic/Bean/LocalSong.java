package com.asus.cloudmusic.Bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by asus on 2018/1/21.
 */

public class LocalSong extends RealmObject {
    private String fileName;//文件名
    private String duration;//时长
    private String singer;//歌手
    private String album;//专辑
    private String type;//形式
    private String size;//大小
    private String fileUrl;//路径
    private int id;//专辑ID
    private String headChar;//首字母
    private String sChar;//缩写字母
    private String parkUrl;//封面路径
    @PrimaryKey
    private String title;//标题歌名

    public void setHeadChar(String headChar) {
        this.headChar = headChar;
    }

    public String getHeadChar() {
        return headChar;
    }

    public int getId() {
        return id;
    }

    public String getsChar() {
        return sChar;
    }

    public void setsChar(String sChar) {
        this.sChar = sChar;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParkUrl() {
        return parkUrl;
    }

    public void setParkUrl(String parkUrl) {
        this.parkUrl = parkUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public LocalSong() {
        super();
    }
}
