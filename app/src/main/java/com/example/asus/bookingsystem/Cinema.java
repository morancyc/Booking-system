package com.example.asus.bookingsystem;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by asus on 2018/3/24.
 */
/*电影院排片信息表*/

public class Cinema extends BmobObject implements Serializable {
    private Integer Movie_ID;      //电影编号
    private String  Movie_Name;    //电影名称
    private String  Movie_Date;    //排片日期
    private String  Movie_Time;    //排片时间
    private Integer image;         //图片ID

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

    public String getMovie_Date() {
        return Movie_Date;
    }

    public void setMovie_Date(String movie_Date) {
        Movie_Date = movie_Date;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}
