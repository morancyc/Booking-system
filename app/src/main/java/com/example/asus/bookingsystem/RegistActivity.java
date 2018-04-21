package com.example.asus.bookingsystem;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class RegistActivity extends AppCompatActivity {

    private String username;
    private String password1;
    private String password2;

    //对控件进行定义
    private EditText text_User;
    private EditText text_Password1;
    private EditText text_Password2;

    private Button btn_Regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        Bmob.initialize(this, "8e5668fb800e3b9563248eaef0cba277");

        text_User=(EditText)findViewById(R.id.editText);
        text_Password1=(EditText)findViewById(R.id.editText2);
        text_Password2=(EditText)findViewById(R.id.editText3);
        btn_Regist=(Button)findViewById(R.id.button);

        btn_Regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否为可用的用户名  1.用户名，密码小于20个字符  2.表中不存在用户名
                username=text_User.getText().toString();
                password1=text_Password1.getText().toString();
                password2=text_Password2.getText().toString();
                ConnectivityManager conManager=(ConnectivityManager)getSystemService(RegistActivity.this.CONNECTIVITY_SERVICE );
                NetworkInfo networkInfo = conManager.getActiveNetworkInfo();

                if (networkInfo == null ){ // 注意，这个判断一定要的哦，要不然会出错

                    Toast.makeText(RegistActivity.this,"没网啊！！！",Toast.LENGTH_SHORT).show();

                }
                else if(!password1.equals(password2))
                {
                    Toast.makeText(RegistActivity.this,"两次密码不一样",Toast.LENGTH_SHORT).show();
                }
                else if(username.equals("")||password1.equals("")||password2.equals(""))
                {
                    Toast.makeText(RegistActivity.this,"请输入",Toast.LENGTH_SHORT).show();
                }
                else if(username.length()>20)
                {
                    Toast.makeText(RegistActivity.this,"用户名过长",Toast.LENGTH_SHORT).show();
                }
                else{
                    BmobQuery<User> quary =new BmobQuery<User>();
                    quary.addWhereEqualTo("Username",username);
                    quary.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if(e==null&&list.size()!=0)  //代表用户存在
                            {
                                Toast.makeText(RegistActivity.this,"用户名已经存在",Toast.LENGTH_SHORT).show();

                            }

                            else if(e==null)
                            {
                                User user=new User();
                                user.setUsername(username);
                                user.setPassword(password1);
                                user.setSex("男");
                                user.setAge(0);
                                user.setMobilephone("");
                                user.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if(e==null){
                                            Toast.makeText(RegistActivity.this,"创建数据成功",Toast.LENGTH_SHORT ).show();
                                            Intent intent=new Intent(RegistActivity.this,LoginActivity.class);
                                            startActivity(intent);
                                            RegistActivity.this.finish();


                                        }else{
                                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                        }
                                    }
                                });

                            }
                            else{
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });


                }
            }
        });




    }
}
