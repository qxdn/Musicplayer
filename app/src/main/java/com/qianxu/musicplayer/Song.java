package com.qianxu.musicplayer;

import android.graphics.Bitmap;

public class Song {
    private String name;  //歌名
    private String Authorname; //作者名
    private Bitmap Image;   //专辑封面
    private String SongPath; //歌曲路径
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
