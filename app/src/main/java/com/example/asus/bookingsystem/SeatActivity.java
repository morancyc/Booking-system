package com.example.asus.bookingsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

//选座界面
public class SeatActivity extends AppCompatActivity {

    private PassSeat passSeat ;        //接收SearchActivity传递过来的passSeat对象
    public SeatView seatView;
    private Button button2;           //主界面提交按钮
 // public ArrayList<Integer> soldrow=new ArrayList<Integer>();    //存储Order表中已经卖出的座位
 // public ArrayList<Integer> soldcol=new ArrayList<Integer>();

    int a1=0,a2=0,a3=0,b1=0,b2=0,b3=0;       //用于接收传递过来的选座排号列号（最多3个）


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        passSeat= (PassSeat) getIntent().getSerializableExtra("pass");
/*
        BmobQuery<Order> query = new BmobQuery<Order>();      //查询已经选座情况，初始化ArrayList<Integer> row,col
        query.addWhereEqualTo("Movie_Time",passSeat.getMovie_Time());
        query.addWhereEqualTo("Movie_Date",passSeat.getMovie_Date());
        query.addWhereEqualTo("Movie_ID",passSeat.getMovie_ID());
        query.addWhereEqualTo("Movie_Name",passSeat.getMovie_Name());

        query.setLimit(100);
        query.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> list, BmobException e) {
                if (e == null) {   //如果查询成功
                    for (int i = 0; i < list.size(); i++) {
                        soldrow.add(list.get(i).getSeat_V());
                        soldcol.add(list.get(i).getSeat_H());
                    }
                    int a=soldrow.size();
                    String p = String.valueOf(a);
                    Toast.makeText(SeatActivity.this,"初始化"+p,Toast.LENGTH_SHORT).show();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    Toast.makeText(SeatActivity.this,"查询失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

*/
        seatView=(SeatView)findViewById(R.id.seatView) ;
        button2=(Button)findViewById(R.id.button2);

        seatView.setSeatChecker(new SeatView.SeatChecker() {


            @Override
            public void checked(int row, int column) {
                if(a1==0&&b1==0)
                {
                    a1=row+1;
                    b1=column+1;
                }
                else if(a2==0&&b2==0)
                {
                    a2=row+1;
                    b2=column+1;
                }
                else
                {
                    a3=row+1;
                    b3=column+1;
                }
            }

            @Override
            public void unCheck(int row, int column) {
                if(a1==row+1&&b1==column+1)
                {
                    a1=0;
                    b1=0;
                }
                else if(a2==row+1&&b2==column+1)
                {
                    a2=0;
                    b2=0;
                }
                else if(a3==row+1&&b3==column+1)
                {
                    a3=0;
                    b3=0;
                }
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatView.setData(6,10,passSeat.getMovie_Time(),passSeat.getMovie_Date(),passSeat.getMovie_Name(),passSeat.getMovie_ID());

        //按下提交按钮向数据库加入值
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a1!=0&&b1!=0)
                {
                    Order order1=new Order();

                    int num0=(int)(Math.random()*60000);
                    order1.setSeat_V((Integer)(a1));
                    order1.setSeat_H((Integer)b1);
                    order1.setMovie_Time(passSeat.getMovie_Time());
                    order1.setMovie_Date(passSeat.getMovie_Date());
                    order1.setUser_Name(passSeat.getUsername());
                    order1.setMovie_Name(passSeat.getMovie_Name());
                    order1.setMovie_ID(passSeat.getMovie_ID());
                    order1.setOrder_Price(passSeat.getMovie_Price());
                    order1.setOrder_ID((Integer)num0);
                    order1.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                Toast.makeText(SeatActivity.this,"选座成功",Toast.LENGTH_SHORT ).show();

                            }else{
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });
                }
                if(a2!=0&&b2!=0)
                {
                    Order order2=new Order();

                    int num0=(int)(Math.random()*60000)+1;
                    order2.setSeat_V((Integer)(a2));
                    order2.setSeat_H((Integer)b2);
                    order2.setMovie_Time(passSeat.getMovie_Time());
                    order2.setMovie_Date(passSeat.getMovie_Date());
                    order2.setUser_Name(passSeat.getUsername());
                    order2.setMovie_Name(passSeat.getMovie_Name());
                    order2.setMovie_ID(passSeat.getMovie_ID());
                    order2.setOrder_Price(passSeat.getMovie_Price());
                    order2.setOrder_ID((Integer)num0);
                    order2.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                Toast.makeText(SeatActivity.this,"选座成功",Toast.LENGTH_SHORT ).show();

                            }else{
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });

                }
                if(a3!=0&&b3!=0)
                {
                    Order order3=new Order();

                    int num0=(int)(Math.random()*60000)+2;
                    order3.setSeat_V((Integer)(a3));
                    order3.setSeat_H((Integer)b3);
                    order3.setMovie_Time(passSeat.getMovie_Time());
                    order3.setMovie_Date(passSeat.getMovie_Date());
                    order3.setUser_Name(passSeat.getUsername());
                    order3.setMovie_Name(passSeat.getMovie_Name());
                    order3.setMovie_ID(passSeat.getMovie_ID());
                    order3.setOrder_Price(passSeat.getMovie_Price());
                    order3.setOrder_ID((Integer)num0);
                    order3.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                Toast.makeText(SeatActivity.this,"选座成功",Toast.LENGTH_SHORT ).show();

                            }else{
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });

                }

                SeatActivity.this.finish();
            }
        });

    }


}
