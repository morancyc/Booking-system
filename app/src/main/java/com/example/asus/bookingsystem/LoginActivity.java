package com.example.asus.bookingsystem;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import okhttp3.internal.Util;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private String username;
    private String password;

    //对控件进行定义
    private EditText text_User;
    private EditText text_Password;
    private Button btn_Login;
    private Button btn_Regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "8e5668fb800e3b9563248eaef0cba277");
        setContentView(R.layout.activity_login);

        text_User=(EditText)findViewById(R.id.editText_user);
        text_Password=(EditText)findViewById(R.id.editText_password);
        btn_Login=(Button)findViewById(R.id.button_login);
        btn_Regist=(Button)findViewById(R.id.button_Regist);

/*
        Cinema cin=new Cinema();
        cin.setMovie_ID(1);
        cin.setMovie_Name("古墓丽影：源起之战");
        cin.setMovie_Date("3月29日");
        cin.setMovie_Time("19:30");
        cin.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Toast.makeText(LoginActivity.this,"cinema",Toast.LENGTH_SHORT).show();
                }
            }
        });
*/

        /*
            下面是两个按钮的监听事件，其中登录按钮按下，判断是否用户名存在以及密码是否正确来登录MainActivity,
            注册按钮按下转到注册界面
        */
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=text_User.getText().toString();
                password=text_Password.getText().toString();
                ConnectivityManager conManager=(ConnectivityManager)getSystemService(LoginActivity.this.CONNECTIVITY_SERVICE );
                NetworkInfo networkInfo = conManager.getActiveNetworkInfo();

                if (networkInfo == null ){ // 注意，这个判断一定要的哦，要不然会出错

                    Toast.makeText(LoginActivity.this,"没网啊！！！",Toast.LENGTH_SHORT).show();

                }

                else
                if(username.equals("")||password.equals("")) {
                    Toast.makeText(LoginActivity.this,"请输入用户名密码",Toast.LENGTH_SHORT).show();
                }
                else{
                    BmobQuery<User> quary =new BmobQuery<User>();
                    quary.addWhereEqualTo("Username",username);
                    quary.addWhereEqualTo("Password",password);
                    quary.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if(e==null&&list.size()!=0)  //代表用户存在
                            {
                                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                /*

                                     跳转到MainActivity,注意传递参数（用户）

                                 */
                                User log=new User();   //传递登录者
                                log.setUsername(username);
                                log.setPassword(password);
                                log.setObjectId(list.get(0).getObjectId());
                                Intent intent=new Intent(LoginActivity.this,Main2Activity.class);
                                intent.putExtra("log",log);
                                startActivity(intent);

                            }
                            else{
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                }

        });

        btn_Regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //使用Intent转到登陆界面
                Intent toReg=new Intent(LoginActivity.this,RegistActivity.class);
                startActivity(toReg);
            }
        });

    }
}
