package com.example.asus.bookingsystem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by asus on 2018/4/1.
 */

//用于显示我的订单
public class MyOrderAdapter extends ArrayAdapter<Order> {
    private int resourceId;

    public MyOrderAdapter(Context context, int textViewResourceId, List<Order> objects)
    {
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Order order=getItem(position);
        View view;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        }else{
            view =convertView;
        }
        TextView order_id=(TextView) view.findViewById(R.id.textView16);
        TextView movie_name=(TextView)view.findViewById(R.id.textView17);
        TextView movie_time=(TextView) view.findViewById(R.id.textView18);
        TextView seat=(TextView)view.findViewById(R.id.textView19);

        order_id.setText(order.getOrder_ID().toString());
        movie_name.setText(order.getMovie_Name());
        movie_time.setText(order.getMovie_Date()+order.getMovie_Time());
        seat.setText(order.getSeat_V()+"排"+order.getSeat_H()+"座");

        return view;
    }
}
