package com.qianxu.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;


public class MusicService extends Service {

    private MediaPlayer mediaPlayer=new MediaPlayer();

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
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //暂停
    public void Pause(){
        if(null!=mediaPlayer){
           mediaPlayer.pause();
        }
    }

    //继续
    public void Continue(){
        if(null!=mediaPlayer){
            mediaPlayer.start();
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
