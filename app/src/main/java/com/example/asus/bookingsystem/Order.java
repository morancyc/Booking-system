package com.example.asus.bookingsystem;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by asus on 2018/3/24.
 */
/*订单信息表*/

public class Order extends BmobObject implements Serializable {
    private Integer Order_ID;        //订单编号
    private String  User_Name;       //用户名
    private String  Order_Price;     //订单价格
    private Integer  Movie_ID;       //电影编号
    private String  Movie_Name;      //电影名
    private String  Movie_Date;      //电影日期
    private String  Movie_Time;      //电影时间
    private Integer Seat_V;          //座位  排
    private Integer Seat_H;          //座位  列


    public Integer getOrder_ID() {
        return Order_ID;
    }

    public void setOrder_ID(Integer order_ID) {
        Order_ID = order_ID;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getOrder_Price() {
        return Order_Price;
    }

    public void setOrder_Price(String order_Price) {
        Order_Price = order_Price;
    }

    public Integer getMovie_ID() {
        return Movie_ID;
    }

    public void setMovie_ID(Integer movie_ID) {
        Movie_ID = movie_ID;
    }

    public String getMovie_Name() {
        return Movie_Name;
    }

    public void setMovie_Name(String movie_Name) {
        Movie_Name = movie_Name;
    }

    public String getMovie_Time() {
        return Movie_Time;
    }

    public void setMovie_Time(String movie_Time) {
        Movie_Time = movie_Time;
    }

    public Integer getSeat_V() {
        return Seat_V;
    }

    public void setSeat_V(Integer seat_V) {
        Seat_V = seat_V;
    }

    public Integer getSeat_H() {
        return Seat_H;
    }

    public void setSeat_H(Integer seat_H) {
        Seat_H = seat_H;
    }

    public String getMovie_Date() {
        return Movie_Date;
    }

    public void setMovie_Date(String movie_Date) {
        Movie_Date = movie_Date;
    }
}
