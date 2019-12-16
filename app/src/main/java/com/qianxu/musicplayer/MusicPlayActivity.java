package com.qianxu.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import co.mobiwise.library.MusicPlayerView;

public class MusicPlayActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musicplaylayout);
        //View
        MusicPlayerView mpv=(MusicPlayerView)findViewById(R.id.mpv);
        TextView Song=(TextView)findViewById(R.id.playsongname);
        TextView Singer=(TextView)findViewById(R.id.playsinger);

        Intent intent=getIntent();
        String songname=intent.getStringExtra("song");  //跳转
        String author=intent.getStringExtra("author");
        long time=intent.getLongExtra("time",0);

        Song.setText(songname);
        Singer.setText(author);
        mpv.setMax((int)(time/1000));
    }
}
