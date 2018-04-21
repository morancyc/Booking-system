package com.example.asus.bookingsystem;

import java.io.File;
import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by asus on 2018/3/24.
 */
/*用于定义电影信息表*/

public class Movie extends BmobObject implements Serializable {
    private Integer Movie_ID;        //电影编号
    private String  Movie_Name;      //电影名
    private Double  Movie_Rate;      //评分
    private String  Director;        //导演
    private String  Movie_Price;     //价格
    private Integer Image;           //图片（文件ID）


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

    public Double  getMovie_Rate() {
        return Movie_Rate;
    }

    public void setMovie_Rate(Double movie_Rate) {
        Movie_Rate = movie_Rate;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getMovie_Price() {
        return Movie_Price;
    }

    public void setMovie_Price(String movie_Price) {
        Movie_Price = movie_Price;
    }

    public Integer getImage() {
        return Image;
    }

    public void setImage(Integer image) {
        Image = image;
    }
}
