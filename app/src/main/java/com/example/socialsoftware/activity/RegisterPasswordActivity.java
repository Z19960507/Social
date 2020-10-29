package com.example.socialsoftware.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.socialsoftware.R;

public class RegisterPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn1;
    private Button next;

    private EditText password1;
    private EditText password2;
    private String account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce2);

        Intent intent = getIntent();
        account = intent.getStringExtra("account");

        initView();


        btn1 = findViewById(R.id.id_login);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterPasswordActivity.this, RegisterAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView(){
        next = findViewById(R.id.bt_2);
        password1 = findViewById(R.id.et_username);
        password2 = findViewById(R.id.et_password);


        next.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_2:
                String mpassword1 = password1.getText().toString().trim();
                String mpassword2 = password2.getText().toString().trim();
                if (mpassword1.length() < 8 && mpassword2.length() < 8){//ayezng
                    Toast.makeText(this, "用户名或密码长度必须大于8或小于20", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(mpassword1) && !TextUtils.isEmpty(mpassword2)) {
                    if (TextUtils.equals(mpassword1, mpassword2)) {
                        Intent intent = new Intent(RegisterPasswordActivity.this, RegisterPhoneCodeActivity.class);
                        String password = password2.getText().toString().trim();
                        intent.putExtra("account", account);
                        intent.putExtra("password", password);
                        startActivity(intent);
                        Toast.makeText(this, "密码相同", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(this, "密码不相同", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(this, "密码输入框为空", Toast.LENGTH_LONG).show();
                }
                break;


        }
    }
}