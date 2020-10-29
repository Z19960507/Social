package com.example.socialsoftware.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.socialsoftware.R;
import com.example.socialsoftware.db.DBOpenHelper;
import com.example.socialsoftware.model.User;

import java.util.ArrayList;

public class ForgetPassword2Activity extends AppCompatActivity implements View.OnClickListener{
    Button btn1;
    private DBOpenHelper mDbOpenHepler;
    private Button next;
    private EditText password1;
    private EditText password2;

    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget2);
        Intent intent = getIntent();//页面跳转，也可以带上数据一起跳转
        account = intent.getStringExtra("account");
        initView();

        mDbOpenHepler = new DBOpenHelper(this);

        btn1 = findViewById(R.id.id_login);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ForgetPassword2Activity.this, ForgetPassword1Activity.class);
                startActivity(intent);
            }
        });
    }

    private void initView(){//大写是类，小写是对象
        next = findViewById(R.id.bt_2);
        password1 = findViewById(R.id.et_username);
        password2 = findViewById(R.id.et_password);


        next.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_2:
                String password = password1.getText().toString().trim();
                String mpassword2 = password2.getText().toString().trim();

                if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(mpassword2)) {
                    // TODO:根据用户名查询数据库中的密码,然后进行比对                 2
                    ArrayList<User> data = mDbOpenHepler.getAllData();
                    boolean match = false;

                    User user = mDbOpenHepler.selectUserByName(account);
                    if (password.length() < 8 && mpassword2.length() < 8){//ayezng
                        Toast.makeText(this, "用户名或密码长度必须大于8或小于20", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (user != null && !TextUtils.equals(password, user.getPassword()) && TextUtils.equals(password, mpassword2)) {
                        Toast.makeText(this, "新密码可用", Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                        mDbOpenHepler.update(password);
                        Intent intent=new Intent(ForgetPassword2Activity.this, LoginActivity.class);
                        startActivity(intent);
                        return;//y73hrn
                    }

                    match = TextUtils.equals(password, user.getPassword()) && TextUtils.equals(mpassword2, user.getPassword());
                    if (match) {
                        Toast.makeText(this, "密码与旧密码相同", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(this, MainActivity2.class);
//                        startActivity(intent);
//                        finish();//销毁此Activity
                        return;
                    }
                    if (!TextUtils.equals(password, mpassword2)){
                        Toast.makeText(this, "两次输入密码不相同", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(this, "新密码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}