package com.qianxu.musicplayer;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import java.util.ArrayList;
import java.util.List;

public class MusicListActivity extends AppCompatActivity {

    private List<Song> songList=new ArrayList<Song>(); //歌曲列表
    private int songPosition; //当前歌曲位置

    private IntentFilter intentFilter;
    private HeadsetPlugReceiver headsetPlugReceiver;//耳机

    private MusicService musicService;
    private ServiceConnection connection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService=((MusicService.MusicBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musiclistlayout);

        //绑定服务
        Intent bindIntent=new Intent(this,MusicService.class);
        bindService(bindIntent,connection,BIND_AUTO_CREATE);

        //本地广播
        localBroadcastManager=localBroadcastManager.getInstance(this);

        //耳机权限
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.HEADSET_PLUG");
        headsetPlugReceiver = new HeadsetPlugReceiver();
        registerReceiver(headsetPlugReceiver, intentFilter);


        TextView hellotext=(TextView)findViewById(R.id.hellouser);
        Intent intent=getIntent();
        String username=intent.getStringExtra("username");  //跳转
        hellotext.setText("欢迎"+username+"!");
        Button quit =(Button)findViewById(R.id.musicquit);

        //动态权限   读取外部存储器权限
        if(ContextCompat.checkSelfPermission(MusicListActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MusicListActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else {
            InitSongs();
        }

        ListView listView=(ListView)findViewById(R.id.song);
        SongAdapter adapter=new SongAdapter(MusicListActivity.this,R.layout.song_item,songList);
        listView.setAdapter(adapter);

        //点击时
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song=songList.get(position);
                songPosition=position;
                Intent intent = new Intent(MusicListActivity.this,MusicPlayActivity.class);
                //传递选到的歌曲名
                intent.putExtra("song",song.getName());
                intent.putExtra("author",song.getAuthorname());
                intent.putExtra("time",song.getDuration());
                startActivity(intent);

                musicService.Start(song.getSongPath());  //启动播放

            }
        });

        //退出
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //注册本地广播
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.qianxu.musicplayer.LOCAL_BROADCAST_MVIEW");
        localReceiver=new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);
    }

    //初始化歌曲
    private  void InitSongs(){
        Cursor cursor=null;
        try{
            //查询本地歌曲
            cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null);
            if(cursor!=null){
                while (cursor.moveToNext()){
                    // 获取歌手信息
                    String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                    //获取歌曲名称
                    String disName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                    //获取文件路径
                    String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                    //获取歌曲时长
                    long time=cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));


                    //Song song = new Song(disName, artist, BitmapFactory.decodeResource(getResources(),R.drawable.music), url,time);
                    Song song = new Song(disName, artist, R.drawable.music, url,time);
                    songList.add(song);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null)
                cursor.close();
        }
    }

    public void NextSong(){
        songPosition++;
        if(songPosition>=songList.size())
            songPosition=0;
        Song song=songList.get(songPosition);
        musicService.Start(song.getSongPath());
        localboardsend(song.getName(),song.getAuthorname(),song.getDuration());
    }

    public void PreSong(){
        songPosition--;
        if(songPosition<0)
            songPosition=(songList.size()-1);
        Song song=songList.get(songPosition);

        musicService.Start(song.getSongPath());
        localboardsend(song.getName(),song.getAuthorname(),song.getDuration());
    }

    //发送歌曲信息
    public void localboardsend(String songname,String author,long time){
        Intent intent=new Intent("com.qianxu.musicplayer.LOCAL_BROADCAST_MLIST");
        intent.putExtra("SongName",songname);
        intent.putExtra("SongAuthor",author);
        intent.putExtra("SongTime",time);
        localBroadcastManager.sendBroadcast(intent);
    }

    //耳机广播接收器
    class HeadsetPlugReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equalsIgnoreCase("android.intent.action.HEADSET_PLUG")){
                if(intent.hasExtra("state")){
                    if (intent.getIntExtra("state", 0) == 1){
                        Toast.makeText(context, "耳机连接", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "耳机断开", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    //本地广播
    class LocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equalsIgnoreCase("com.qianxu.musicplayer.LOCAL_BROADCAST_MVIEW")){
            int code=intent.getIntExtra("musicplayercode",-1);
            switch (code){
                case 0:PreSong();break;
                case 1:NextSong();break;
                case 2:
                    if(musicService.isPlaying())
                        musicService.Pause();
                    else
                        musicService.Continue();
                    break;
                default:break;
                }
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    InitSongs();
                }else{
                    Toast.makeText(this,"没有权限无法实现",Toast.LENGTH_LONG).show();
                }
                break;
            default:break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(headsetPlugReceiver); //广播释放
        localBroadcastManager.unregisterReceiver(localReceiver);
        unbindService(connection);  //解绑服务
    }

}
