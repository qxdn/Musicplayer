package com.qianxu.musicplayer;

import android.graphics.Bitmap;

public class Song {
    private String name;
    private String Authorname;
    private Bitmap Image;
    private String SongPath;
    private long Duration; //歌曲时长
    public Song(String name,String Authorname,Bitmap Image,String SongPath,long Duration){
        this.name=name;
        this.Authorname=Authorname;
        this.Image=Image;
        this.SongPath=SongPath;
        this.Duration=Duration;
    }

    public String getName(){
        return name;
    }
    public String getAuthorname(){
        return Authorname;
    }
    public Bitmap getImageBitmap(){
        return Image;
    }
    public String getSongPath(){return SongPath;}

    public  long getDuration(){return  Duration;}
}
