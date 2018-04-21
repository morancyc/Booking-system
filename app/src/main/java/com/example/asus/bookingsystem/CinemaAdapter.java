package com.example.asus.bookingsystem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by asus on 2018/3/26.
 */

public class CinemaAdapter extends ArrayAdapter<Cinema>{
    private int resourceId;

    public CinemaAdapter(Context context, int textViewResourceId, List<Cinema> objects)
    {
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Cinema cinema=getItem(position);
        View view;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        }else{
            view =convertView;
        }

        final ImageView cinemaimage=(ImageView)view.findViewById(R.id.imageView2);  //图片
        TextView moviename=(TextView)view.findViewById(R.id.textView8) ;            //影片名
        TextView date=(TextView)view.findViewById(R.id.textView9) ;                 //日期
        TextView time=(TextView)view.findViewById(R.id.textView10) ;                //时间

        cinemaimage.setImageResource(cinema.getImage().intValue());
        moviename.setText(cinema.getMovie_Name());
        date.setText(cinema.getMovie_Date());
        time.setText(cinema.getMovie_Time());

        return view;
    }
}
