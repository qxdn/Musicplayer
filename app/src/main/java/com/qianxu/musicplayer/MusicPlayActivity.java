package com.qianxu.musicplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import co.mobiwise.library.MusicPlayerView;

public class MusicPlayActivity extends AppCompatActivity {

    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;
    private MusicPlayerView mpv;
    private TextView Song;
    private TextView Singer;
    static final int premusiccode=0;
    static final int nextmusiccode=1;
    static final int pausemusiccode=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musicplaylayout);
        //View
        mpv=(MusicPlayerView)findViewById(R.id.mpv);
        Song=(TextView)findViewById(R.id.playsongname);
        Singer=(TextView)findViewById(R.id.playsinger);
        ImageView nextmusic=(ImageView)findViewById(R.id.musicnext);
        final ImageView premusic=(ImageView)findViewById(R.id.musicprevious);

        localBroadcastManager=LocalBroadcastManager.getInstance(this);//获取实例

        final Intent intent=getIntent();
        String songname=intent.getStringExtra("song");  //跳转
        String author=intent.getStringExtra("author");
        long time=intent.getLongExtra("time",0);

        Song.setText(songname); //设置歌曲名
        Singer.setText(author); //设置作者
        mpv.setMax((int)(time/1000));   //设置时长

        //组件问题
        mpv.stop();
        mpv.start();
        //按键绑定
        mpv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mpv.isRotating()){
                    mpv.stop();
                }else {
                    mpv.start();
                }
                localboardsend(pausemusiccode);
            }
        });
        nextmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localboardsend(nextmusiccode);
            }
        });
        premusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localboardsend(premusiccode);
            }
        });

        //注册本地广播
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.qianxu.musicplayer.LOCAL_BROADCAST_MLIST");
        localReceiver=new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);
    }

    public void localboardsend(int code){
        Intent intent=new Intent("com.qianxu.musicplayer.LOCAL_BROADCAST_MVIEW");
        intent.putExtra("musicplayercode",code);
        localBroadcastManager.sendBroadcast(intent);
    }

    //接受歌曲信息
    class LocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("com.qianxu.musicplayer.LOCAL_BROADCAST_MLIST")) {
                String name = intent.getStringExtra("SongName");
                String author = intent.getStringExtra("SongAuthor");
                long time = intent.getLongExtra("SongTime", 0);
                Song.setText(name); //设置歌曲名
                Singer.setText(author); //设置作者
                mpv.setMax((int) (time / 1000));   //设置时长
                mpv.setProgress(0);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }
}
