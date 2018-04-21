package com.example.asus.bookingsystem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;



/**
 * Created by asus on 2018/3/24.
 */
 /*用于显示电影的适配器*/
public class MovieAdapter extends ArrayAdapter<Movie> {
        private int resourceId;

        public MovieAdapter(Context context, int textViewResourceId, List<Movie> objects)
        {
            super(context,textViewResourceId,objects);
            resourceId=textViewResourceId;
        }

    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        final Movie movie=getItem(position);
        View view;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        }else{
            view =convertView;
        }
        final ImageView movieimage=(ImageView)view.findViewById(R.id.imageView);  //图片
        TextView moviename=(TextView)view.findViewById(R.id.textView4) ;    //影片名
        TextView movierate=(TextView)view.findViewById(R.id.textView5) ;    //评分
        TextView moviedirector=(TextView)view.findViewById(R.id.textView6) ;//导演
        TextView price=(TextView)view.findViewById(R.id.textView20) ;//导演

        movieimage.setImageResource(movie.getImage().intValue());
        moviename.setText(movie.getMovie_Name());
        movierate.setText(movie.getMovie_Rate().toString());
        moviedirector.setText("导演："+movie.getDirector());
        price.setText("价格"+movie.getMovie_Price()+"元");

        return view;
    }



}
