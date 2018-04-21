package com.example.asus.bookingsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

//用于显示我的信息界面
public class MyinfoActivity extends AppCompatActivity {

    private User user;     //接收Main2Activity传递过来的user对象
    private String selected="男";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        user=(User)getIntent().getSerializableExtra("User1");    //user接收由Main2activity传递过来的User名称
        Toast.makeText(MyinfoActivity.this,"我的信息",Toast.LENGTH_SHORT).show();

        BmobQuery<User>query =new BmobQuery<User>();
        query.getObject(user.getObjectId(), new QueryListener<User>() {
            @Override
            public void done(User list, BmobException e) {
                if(e==null)
                {
                    final User me ;
                    me=list;
                    //final String id=me.getObjectId();
                    final ArrayList<String> data=new ArrayList<>();
                    data.add("用户名 :   "+me.getUsername());
                    data.add("密码   :    "+me.getPassword());
                    data.add("性别   :    "+me.getSex());
                    data.add("年龄   :    "+me.getAge().toString());
                    data.add("电话   :    "+me.getMobilephone());

                    ListView myinfolistview= (ListView)findViewById(R.id.myinfolistview);
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(MyinfoActivity.this,android.R.layout.simple_list_item_1,data);
                    myinfolistview.setAdapter(adapter);
                    myinfolistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String s = data.get(position);
                            switch (position)
                            {
                                case 0:    //修改用户名
                                    final String id1=me.getObjectId();
                                    AlertDialog.Builder builder1 =new AlertDialog.Builder(MyinfoActivity.this);
                                    builder1.setIcon(R.drawable.my);
                                    builder1.setTitle("请输入用户名");

                                    final View view1 = LayoutInflater.from(MyinfoActivity.this).inflate(R.layout.dialog,null);
                                    builder1.setView(view1);
                                    //final EditText username=(EditText)findViewById(R.id.editText4);
                                    builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            EditText username=(EditText)view1.findViewById(R.id.editText4);
                                            String uname=username.getText().toString();
                                            User u=new User();
                                            u.setUsername(uname);
                                            u.update(id1, new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if(e==null){
                                                        Log.i("bmob","更新成功");
                                                    }else{
                                                        Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                                                    }
                                                }
                                            });

                                        }
                                    });
                                    builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                                    builder1.show();
                                    break;

                            /*******************************************************************************************/
                                case 1:    //修改密码
                                    final String id2=me.getObjectId();
                                    AlertDialog.Builder builder2 =new AlertDialog.Builder(MyinfoActivity.this);
                                    builder2.setIcon(R.drawable.my);
                                    builder2.setTitle("请输入密码");

                                    final View view2 = LayoutInflater.from(MyinfoActivity.this).inflate(R.layout.dialog,null);
                                    builder2.setView(view2);
                                    //final EditText username=(EditText)findViewById(R.id.editText4);
                                    builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            EditText userpas=(EditText)view2.findViewById(R.id.editText4);
                                            String upas=userpas.getText().toString();
                                            User u=new User();
                                            u.setPassword(upas);
                                            u.update(id2, new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if(e==null){
                                                        Log.i("bmob","更新成功");
                                                    }else{
                                                        Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                                                    }
                                                }
                                            });

                                        }
                                    });
                                    builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                                    builder2.show();

                                    break;

                                /*******************************************************************************************/
                                case 2:    //修改性别
                                    AlertDialog.Builder builder3 =new AlertDialog.Builder(MyinfoActivity.this);
                                    builder3.setIcon(R.drawable.my);
                                    builder3.setTitle("请选择性别");
                                    final  String[] sex ={"男","女","不确定"};
                                    final String id3=me.getObjectId();
                                    //ArrayList<String> selected=new ArrayList<String>();
                                    builder3.setSingleChoiceItems(sex, 0, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            selected=sex[which] ;

                                        }
                                    });
                                    builder3.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            User u =new User();
                                            u.setSex(selected);
                                            u.update(id3, new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if(e==null){
                                                        Log.i("bmob","更新成功");
                                                    }else{
                                                        Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                                                    }
                                                }
                                            });

                                        }
                                    });
                                    builder3.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            selected=sex[0] ;;

                                        }
                                    });
                                    builder3.show();

                                    break;
                             /*******************************************************************************************/
                                case 3:    //修改年龄
                                    final String id4=me.getObjectId();
                                    AlertDialog.Builder builder4 =new AlertDialog.Builder(MyinfoActivity.this);
                                    builder4.setIcon(R.drawable.my);
                                    builder4.setTitle("请输入年龄");

                                    final View view4 = LayoutInflater.from(MyinfoActivity.this).inflate(R.layout.dialog,null);
                                    builder4.setView(view4);
                                    //final EditText username=(EditText)findViewById(R.id.editText4);
                                    builder4.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            EditText userage=(EditText)view4.findViewById(R.id.editText4);
                                            String mage=userage.getText().toString();
                                            Integer uage=Integer.valueOf(mage);
                                            User u=new User();
                                            u.setAge(uage);
                                            u.update(id4, new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if(e==null){
                                                        Log.i("bmob","更新成功");
                                                    }else{
                                                        Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                                                    }
                                                }
                                            });

                                        }
                                    });
                                    builder4.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                                    builder4.show();

                                    break;
                                /*******************************************************************************************/
                                case 4:    //修改电话
                                    final String id5=me.getObjectId();
                                    AlertDialog.Builder builder5 =new AlertDialog.Builder(MyinfoActivity.this);
                                    builder5.setIcon(R.drawable.my);
                                    builder5.setTitle("请输入电话");

                                    final View view5 = LayoutInflater.from(MyinfoActivity.this).inflate(R.layout.dialog,null);
                                    builder5.setView(view5);
                                    //final EditText username=(EditText)findViewById(R.id.editText4);
                                    builder5.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            EditText usertel=(EditText)view5.findViewById(R.id.editText4);
                                            String utel=usertel.getText().toString();
                                            User u=new User();
                                            u.setMobilephone(utel);
                                            u.update(id5, new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if(e==null){
                                                        Log.i("bmob","更新成功");
                                                    }else{
                                                        Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                                                    }
                                                }
                                            });

                                        }
                                    });
                                    builder5.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    builder5.show();
                                    break;

                                default:
                                    break;
                            }
                        }
                    });
                }
            }
        });


    }



}
