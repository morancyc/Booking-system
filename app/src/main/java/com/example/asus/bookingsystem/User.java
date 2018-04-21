package com.example.asus.bookingsystem;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by asus on 2018/3/23.
 */
/*用于定义用户表*/

public class User extends BmobObject implements Serializable{
    private String Username;       /*用户名*/
    private String Password;       /*密码*/
    private String Mobile;         /*手机号*/
    private String Sex;            /*性别*/
    private Integer Age;           /*年龄*/


    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getMobilephone() {
        return Mobile;
    }

    public void setMobilephone(String mobile) {
        Mobile = mobile;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer age) {
        Age = age;
    }

}
