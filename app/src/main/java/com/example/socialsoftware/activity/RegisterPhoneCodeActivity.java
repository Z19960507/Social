package com.example.socialsoftware.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.socialsoftware.MainActivity;
import com.example.socialsoftware.R;
import com.example.socialsoftware.db.DBOpenHelper;
import com.example.socialsoftware.model.User;
import com.example.socialsoftware.utils.Code;

public class RegisterPhoneCodeActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn1;
    ImageView showCode;
    private String realCode;
    private EditText phonecode;
    private Button next;
    private DBOpenHelper mDbOpenHepler;
    private String account;
    private String password;

    private String mphonecode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce3);

        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        password = intent.getStringExtra("password");

        btn1 = findViewById(R.id.id_login);
        initView();
        mDbOpenHepler = new DBOpenHelper(this);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPhoneCodeActivity.this, RegisterPasswordActivity.class);
                startActivity(intent);
            }
        });

        showCode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
    }

    private void initView() {
        showCode = findViewById(R.id.showCode);
        phonecode = findViewById(R.id.et_password);
        next = findViewById(R.id.bt_2);


        showCode.setOnClickListener(this);
        next.setOnClickListener(this);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.showCode:
                showCode.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
                break;
            case R.id.bt_2:
                mphonecode = phonecode.getText().toString().trim();

                if (!TextUtils.isEmpty(mphonecode)) {
                    if (TextUtils.equals(mphonecode, realCode)) {
                        mDbOpenHepler.submit(account, password, mphonecode);//把这三个传递进db的submit里
//                        Intent intent = new Intent(RegisterPhoneCodeActivity.this, LoginActivity.class);
                        User user = new User(account, password, mphonecode);//生成一个对象,new 一个对象
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("user", user);//放user到Intent
                        //步骤1：创建一个SharedPreferences对象
                        SharedPreferences sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
                        //步骤2： 实例化SharedPreferences.Editor对象
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        //步骤3：将获取过来的值放入文件
                        editor.putString("name",account);


                        //步骤4：提交
                        editor.apply();


                        // 传递参数
                        intent.putExtra("username",account);
                        startActivity(intent);
                        finish();//销毁此Activity

                        Toast.makeText(this, "验证码正确", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "验证码不正确", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void showToastOnThread(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterPhoneCodeActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
