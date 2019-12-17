package com.qianxu.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.List;

public class SongAdapter extends ArrayAdapter<Song> {
    private int resourceId;
    public SongAdapter(Context context, int textViewResourceId, List<Song> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        Song song=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.SongImage=(ImageView)view.findViewById(R.id.songImg);
            viewHolder.name=(TextView)view.findViewById(R.id.songname);
            viewHolder.Authorname=(TextView)view.findViewById(R.id.songauthor);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag(); //重新获取Viewholder
        }
        //viewHolder.SongImage.setImageResource(song.getImageId());
        viewHolder.SongImage.setImageBitmap(song.getImageBitmap());
        viewHolder.name.setText(song.getName());
        viewHolder.Authorname.setText(song.getAuthorname());
        viewHolder.SongPath=song.getSongPath();
        return view;
    }

    class ViewHolder{
        ImageView SongImage;
        TextView name;
        TextView Authorname;
        String SongPath;
    }
}
