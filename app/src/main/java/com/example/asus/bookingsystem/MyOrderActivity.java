package com.example.asus.bookingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MyOrderActivity extends AppCompatActivity {

    private User user;     //接收Main2Activity传递过来的user对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        user=(User)getIntent().getSerializableExtra("User2");    //user接收由Main2activity传递过来的User名称
        Toast.makeText(MyOrderActivity.this,"我的订单",Toast.LENGTH_SHORT).show();

        BmobQuery<Order> query=new BmobQuery<Order>();
        query.addWhereEqualTo("User_Name",user.getUsername());
        query.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> list, BmobException e) {
                if(e==null) {
                    MyOrderAdapter adapter = new MyOrderAdapter(MyOrderActivity.this, R.layout.order_item, list);
                    ListView orderlistview = (ListView) findViewById(R.id.orderlistview);
                    orderlistview.setAdapter(adapter);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    Toast.makeText(MyOrderActivity.this,"查询失败",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
