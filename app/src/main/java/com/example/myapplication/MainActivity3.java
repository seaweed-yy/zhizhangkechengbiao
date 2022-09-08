package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Bean.UserBean;
import com.example.myapplication.Bean.UserDao;

public class MainActivity3 extends AppCompatActivity {
    private Button button;
    private TextView edittext1;
    private TextView edittext2;
    private UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        button = (Button) findViewById(R.id.btn);
        edittext1 = (TextView) findViewById(R.id.edittext1);
        edittext2 = (TextView) findViewById(R.id.edittext2);
        userDao = new UserDao(MainActivity3.this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edittext1.getText().toString();
                String password = edittext2.getText().toString();
                UserBean userBean = userDao.querryUser(username);
                if (TextUtils.isEmpty(userBean.getUsername())) {
                    boolean flag = userDao.insertUser(username,password);
                    if(flag){
                        Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity3.this, MainActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"用户名已存在",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}