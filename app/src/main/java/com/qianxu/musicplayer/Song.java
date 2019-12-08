package com.qianxu.musicplayer;

public class Song {
    private String name;
    private String Authorname;
    private int ImageId;
    private String SongPath;
    public Song(String name,String Authorname,int ImageId,String SongPath){
        this.name=name;
        this.Authorname=Authorname;
        this.ImageId=ImageId;
        this.SongPath=SongPath;
    }

    public String getName(){
        return name;
    }
    public String getAuthorname(){
        return Authorname;
    }
    public int getImageId(){
        return ImageId;
    }
    public String getSongPath(){return SongPath;}
}
