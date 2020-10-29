package com.example.socialsoftware.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.socialsoftware.MainActivity;
import com.example.socialsoftware.R;
import com.example.socialsoftware.db.DBOpenHelper;
import com.example.socialsoftware.model.User;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn2;
    Button btn3;
    private DBOpenHelper mDbOpenHepler;
    private Button login;
    private EditText musername;
    private EditText mpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Intent intent = getIntent();//页面跳转，也可以带上数据一起跳转
        User user = (User) intent.getSerializableExtra("user");//序列号，//object强转user
        initView(user);

        mDbOpenHepler = new DBOpenHelper(this);

        btn2 = findViewById(R.id.bt_2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, ForgetPassword1Activity.class);
                startActivity(intent);
            }
        });
        btn3 = findViewById(R.id.bt_3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, RegisterAccountActivity.class);
                startActivity(intent);
            }
        });

    }


    /*
        大写是类，小写是对象
     */
    private void initView(User user){
        login = findViewById(R.id.id_login);
        musername = findViewById(R.id.et_username);
        mpassword = findViewById(R.id.et_password);


        login.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_login:
                String name = musername.getText().toString().trim();
                String password = mpassword.getText().toString().trim();

                if (password.length() < 8 && name.length() < 8){//ayezng
                    Toast.makeText(this, "用户名或密码长度必须大于8或小于20", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    // TODO:根据用户名查询数据库中的密码,然后进行比对
                    ArrayList<User> data = mDbOpenHepler.getAllData();
                    boolean match = false;

                    User user = mDbOpenHepler.selectUserByName(name);
                    if (user == null) {
                        Toast.makeText(this, "用户未注册", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    match = TextUtils.equals(name, user.getName()) && TextUtils.equals(password, user.getPassword());

                    if (match) {
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();

                        //步骤1：创建一个SharedPreferences对象
                        SharedPreferences sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
                        //步骤2： 实例化SharedPreferences.Editor对象
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        //步骤3：将获取过来的值放入文件
                        editor.putString("name",name);


                        //步骤4：提交
                        editor.apply();

                        Intent intent = new Intent(this, MainActivity.class);
                        // 传递参数
                        intent.putExtra("username",name);
                        startActivity(intent);
                        finish();//销毁此Activity
                    } else {
                        Toast.makeText(this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}