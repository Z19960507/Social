package com.example.socialsoftware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity3 extends AppCompatActivity implements View.OnClickListener {
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

        btn1 = findViewById(R.id.bt_1);
        initView();
        mDbOpenHepler = new DBOpenHelper(this);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity3.this, RegisterActivity2.class);
                startActivity(intent);
            }
        });

        showCode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
    }

    private void initView() {
        showCode = findViewById(R.id.showCode);
        phonecode = findViewById(R.id.et_2);
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
                        Intent intent = new Intent(RegisterActivity3.this, LoginActivity.class);
                        User user = new User(account, password, mphonecode);//生成一个对象,new 一个对象
                        intent.putExtra("user", user);//放user到Intent
                        startActivity(intent);

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
                Toast.makeText(RegisterActivity3.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
