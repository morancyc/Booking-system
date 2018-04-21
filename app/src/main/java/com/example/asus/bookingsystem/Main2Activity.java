package com.example.asus.bookingsystem;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fragment.Fragment1;
import fragment.Fragment2;
import fragment.Fragment3;

//注意大坑！使用Fragment时候必须继承接口：OnFragmentInteractionListener

public class Main2Activity extends AppCompatActivity implements Fragment1.OnFragmentInteractionListener , Fragment2.OnFragmentInteractionListener , Fragment3.OnFragmentInteractionListener{

    private  User user;      //user保存登录者名称以及Objectid

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;

    private void setDefaultFragment(){
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        fragment1=new Fragment1();
        transaction.replace(R.id.content,fragment1);
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentManager =  getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    fragment1=new Fragment1();
                    transaction.replace(R.id.content,fragment1);
                    transaction.commit();
                    return true;
                case R.id.navigation_dashboard:

                    fragment2=new Fragment2();
                    transaction.replace(R.id.content,fragment2);
                    transaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    fragment3=new Fragment3();
                    transaction.replace(R.id.content,fragment3);
                    transaction.commit();
                    return true;
            }
            return false;
        }

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        user=(User)getIntent().getSerializableExtra("log");  //user接收由loginactivity传递过来的User名称

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        setDefaultFragment();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }



    //实现接口函数obj为movie实例的movie_name,需要传递给SearchActivity用户名和MovieName
    @Override
    public void onFragmentInteraction(String obj) {
        PassSeat passSeat=new PassSeat();
        Intent intent =new Intent(Main2Activity.this,SearchActivity.class);
        passSeat.setMovie_Name(obj);
        passSeat.setUsername(user.getUsername());

        intent.putExtra("pass",passSeat);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction1(String uri) {
        if(uri.equals("我的信息"))
        {

            Intent intent=new Intent(Main2Activity.this,MyinfoActivity.class);
            intent.putExtra("User1",user);
            startActivity(intent);
        }
        else if(uri.equals("我的订单"))
        {
            Intent intent=new Intent(Main2Activity.this,MyOrderActivity.class);
            intent.putExtra("User2",user);
            startActivity(intent);
        }
    }

}
