package com.qianxu.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public class MusicService extends Service {

    private LocalBroadcastManager localBroadcastManager;

    private MediaPlayer mediaPlayer;

    public MusicService() {
        localBroadcastManager=LocalBroadcastManager.getInstance(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBinder();
    }

    class MusicBinder extends Binder{ //绑定Service
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
            //播放完成监听
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //发送完成广播
                    Intent intent=new Intent("com.qianxu.musicplayer.LOCAL_BROADCAST_MCOMPLETE");
                    intent.putExtra("finishcode",1);
                    localBroadcastManager.sendBroadcast(intent);
                }
            });
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
        if(mediaPlayer!=null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
