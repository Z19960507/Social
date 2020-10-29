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

public class ForgetPassword1Activity extends AppCompatActivity implements View.OnClickListener {
    Button btn1;

    private DBOpenHelper mDbOpenHepler;

    private EditText musername;
    private EditText mrealCode;
    private Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wangji1);
        Intent intent = getIntent();//页面跳转，也可以带上数据一起跳转
        User user = (User) intent.getSerializableExtra("user");//序列号，//object强转user
        initView(user);




        mDbOpenHepler = new DBOpenHelper(this);
        btn1 = findViewById(R.id.id_login);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ForgetPassword1Activity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    private void initView(User user){//大写是类，小写是对象
        login = findViewById(R.id.bt_2);
        musername = findViewById(R.id.et_username);
        mrealCode = findViewById(R.id.et_password);


        login.setOnClickListener(this);

    }


    public void onClick(View view){

        switch (view.getId()){
            case R.id.bt_2:
                String name = musername.getText().toString().trim();
                String code = mrealCode.getText().toString().trim();

                if (name.length() < 8 && code.length() < 6){//ayezng
                    Toast.makeText(this, "用户名或密码长度必须大于8或小于20", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(code)) {
                    // TODO:根据用户名查询数据库中的密码,然后进行比对
                    ArrayList<User> data = mDbOpenHepler.getAllData();
                    boolean match = false;

                    User user = mDbOpenHepler.selectUserByName(name);
                    if (user == null) {
                        Toast.makeText(this, "用户未注册", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    match = TextUtils.equals(name, user.getName()) && TextUtils.equals(code, user.getCode());

                    if (match) {
                        Toast.makeText(this, "账号验证码在库", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, ForgetPassword2Activity.class);
                        String account = musername.getText().toString().trim();
                        intent.putExtra("account", account);

                        startActivity(intent);
                        finish();//销毁此Activity
                    } else {
                        Toast.makeText(this, "用户名或验证码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "用户名或验证码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}