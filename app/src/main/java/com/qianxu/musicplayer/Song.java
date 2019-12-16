package com.qianxu.musicplayer;

import android.graphics.Bitmap;

public class Song {
    private String name;
    private String Authorname;
    private int Imageid;
    private String SongPath;
    private long Duration; //歌曲时长
    public Song(String name,String Authorname,int Imageid,String SongPath,long Duration){
        this.name=name;
        this.Authorname=Authorname;
        this.Imageid=Imageid;
        this.SongPath=SongPath;
        this.Duration=Duration;
    }

    public String getName(){
        return name;
    }
    public String getAuthorname(){
        return Authorname;
    }
    public int getImageId(){
        return Imageid;
    }
    public String getSongPath(){return SongPath;}

    public  long getDuration(){return  Duration;}
}
