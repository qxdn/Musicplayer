package com.qianxu.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;


public class MusicService extends Service {

    private MediaPlayer mediaPlayer;

    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBinder();
    }

    class MusicBinder extends Binder{
        public MusicService getService(){
            return MusicService.this;
        }
    }

    //开始方法
    public void Start(String path){
        if(mediaPlayer!=null)
            mediaPlayer.reset();
        try {
            mediaPlayer=MediaPlayer.create(this, Uri.parse(path));
            mediaPlayer.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //暂停
    public void Pause(){
        if(mediaPlayer!=null){
            mediaPlayer.pause();
        }
    }

    //继续
    public void Continue(){
        if(mediaPlayer!=null){
            mediaPlayer.start();
        }
    }

    public boolean isPlaying(){
        if(mediaPlayer!=null)
            return mediaPlayer.isPlaying();
        else
            return false;
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
