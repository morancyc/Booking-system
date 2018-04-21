package com.example.asus.bookingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fragment.Fragment1;

public class MainActivity extends AppCompatActivity{
    private  User user;      //user保存登录者名称
 //   private  ArrayList<Movie> movielist=new ArrayList<>();

    private Fragment1 fragment1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user=(User)getIntent().getSerializableExtra("log");  //user保存由loginactivity传递过来的User名称
/*
        BmobQuery<Movie> query = new BmobQuery<Movie>();
        query.setLimit(50);
        query.findObjects(new FindListener<Movie>() {
            @Override
            public void done(List<Movie> list, BmobException e) {
                if(e==null){
                    Toast.makeText(MainActivity.this,"查询成功：共" + list.size() + "条数据。",Toast.LENGTH_SHORT).show();
                    for (int i=0;i<list.size();i++) {
                        movielist.add(list.get(i));
                    }
                    Toast.makeText(MainActivity.this,"movielist共" + movielist.size() + "条数据。",Toast.LENGTH_SHORT).show();
                    MovieAdapter adapter = new MovieAdapter(MainActivity.this,R.layout.movie_item,movielist);
                    ListView listView=(ListView)findViewById(R.id.listview1);
                    listView.setAdapter(adapter);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    Toast.makeText(MainActivity.this,"查询失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
*/










    }
}
