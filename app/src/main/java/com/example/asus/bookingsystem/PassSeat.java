package com.example.asus.bookingsystem;

import java.io.Serializable;

/**
 * Created by asus on 2018/3/29.
 */

//用于传递给SeatActivity的序列化对象

public class PassSeat implements Serializable {
    private String  Username;       /*用户名*/
    private Integer Movie_ID;        //电影编号
    private String  Movie_Name;      //电影名
    private String  Movie_Price;     //价格
    private String  Movie_Date;    //排片日期
    private String  Movie_Time;    //排片时间


    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
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

    public String getMovie_Price() {
        return Movie_Price;
    }

    public void setMovie_Price(String movie_Price) {
        Movie_Price = movie_Price;
    }

    public String getMovie_Date() {
        return Movie_Date;
    }

    public void setMovie_Date(String movie_Date) {
        Movie_Date = movie_Date;
    }

    public String getMovie_Time() {
        return Movie_Time;
    }

    public void setMovie_Time(String movie_Time) {
        Movie_Time = movie_Time;
    }
}
