package com.example.asus.bookingsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

//搜索界面
public class SearchActivity extends AppCompatActivity {

    private PassSeat passSeat;    //接受从Main2Activity传来的passSeat对象，包含用户名username和moviename
    private String Movie_Price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent=getIntent();
        passSeat=(PassSeat)getIntent().getSerializableExtra("pass");

        BmobQuery<Movie> query1 = new BmobQuery<Movie>();
        //query1.addWhereEqualTo("Movie_Name",passSeat.getMovie_Name());
        query1.setLimit(200);
        query1.findObjects(new FindListener<Movie>() {
            @Override
            public void done(List<Movie> list, BmobException e) {
                if (e == null) {   //如果查询成功
                    final ArrayList<Movie> movielist = new ArrayList<>();

                    //用于实现模糊匹配：
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getMovie_Name().contains(passSeat.getMovie_Name()))
                            movielist.add(list.get(i));
                    }
                    Movie_Price=movielist.get(0).getMovie_Price();
                } else {
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    Toast.makeText(SearchActivity.this,"查询失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

        BmobQuery<Cinema> query = new BmobQuery<Cinema>();

        //query.addWhereContains("Movie_Name",moviename);     //模糊匹配查询(需要Money)

        query.setLimit(200);
        query.findObjects(new FindListener<Cinema>() {
            @Override
            public void done(List<Cinema> list, BmobException e) {
                if(e==null){   //如果查询成功
                    final ArrayList<Cinema> cinemalist=new ArrayList<>();

                    //用于实现模糊匹配：
                    for(int i=0;i<list.size();i++)
                    {
                        if(list.get(i).getMovie_Name().contains(passSeat.getMovie_Name()))
                            cinemalist.add(list.get(i));
                    }

                    CinemaAdapter adapter = new CinemaAdapter(SearchActivity.this,R.layout.cinema_item,cinemalist);
                    ListView listview3 = (ListView)findViewById( R.id.listview3);
                    listview3.setAdapter(adapter);
                    listview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Cinema cine = cinemalist.get(position);
                            PassSeat pass=new PassSeat();
                            pass.setUsername(passSeat.getUsername());
                            pass.setMovie_Name(cine.getMovie_Name());
                            pass.setMovie_ID(cine.getMovie_ID());
                            pass.setMovie_Date(cine.getMovie_Date());
                            pass.setMovie_Price(Movie_Price);
                            pass.setMovie_Time(cine.getMovie_Time());
                            Intent intent=new Intent(SearchActivity.this,SeatActivity.class);
                            intent.putExtra("pass",pass);


                        /*
                            Intent intent = new Intent(SearchActivity.this,SeatActivity.class);
                            intent.putExtra("cine",cine);
                         */

                            startActivity(intent);         //转到选座界面
                        }
                    });


                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    Toast.makeText(SearchActivity.this,"查询失败",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
