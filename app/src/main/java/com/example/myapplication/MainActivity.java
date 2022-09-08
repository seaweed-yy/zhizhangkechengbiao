package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Bean.CourseDao;
import com.example.myapplication.Bean.UserBean;
import com.example.myapplication.Bean.UserDao;
import com.example.myapplication.adapter.SpinnerImgAdapter;

import java.util.concurrent.TimeUnit;

//Alt+Enter     自动提示
//Ctrl+/        自动注释
//Ctrl+Alt+l    自动缩进


public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button1;
    private EditText edittext1;
    private EditText edittext2;
    private String editstr1;
    private String editstr2;
    private Spinner spinner;
    private int spinnerIndex;
    private String[] spinnerImg = new String[3];
    private int i;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userDao = new UserDao(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        button1 = findViewById(R.id.btn1);
        edittext1 = (EditText) findViewById(R.id.edittext1);
        edittext2 = (EditText) findViewById(R.id.edittext2);
        spinner = (Spinner) findViewById(R.id.imgSpinner);
        spinnerImg[0] = String.valueOf(R.drawable.infoimg1);
        spinnerImg[1] = String.valueOf(R.drawable.infoimg2);
        spinnerImg[2] = String.valueOf(R.drawable.logo);
        spinner.setAdapter(new SpinnerImgAdapter(MainActivity.this, spinnerImg));
        Toast.makeText(MainActivity.this, "啦啦啦", Toast.LENGTH_SHORT).show();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, spinnerImg[i], Toast.LENGTH_SHORT).show();
                spinnerIndex = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressBar progressH = findViewById(R.id.ProgressH);
                editstr1 = edittext1.getText().toString();
                editstr2 = edittext2.getText().toString();
                UserBean userBean = userDao.querryUser(editstr1, editstr2);
                if (TextUtils.isEmpty(userBean.getUsername())) {
                    Toast.makeText(getApplicationContext(), "用户名不存在或密码不正确", Toast.LENGTH_SHORT).show();
                } else {
                    for (i = 1; i < 100; i++) {
                        progressH.incrementProgressBy(1);
                    }
                    //跳转
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    //传值
                    intent.putExtra("userId", editstr1);
                    intent.putExtra("password", editstr2);
                    intent.putExtra("imgsrc", spinnerImg[spinnerIndex]);
                    //消息提示 登录成功
                    Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    //执行跳转
                    startActivity(intent);
                }


            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(intent);
            }
        });
    }
}