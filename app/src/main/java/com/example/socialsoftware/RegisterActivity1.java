package com.example.socialsoftware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity1 extends AppCompatActivity implements View.OnClickListener {
    Button btn1;
    private Button next;

    private EditText username1;
    private EditText username2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce1);
        initView();


        btn1 = findViewById(R.id.bt_1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity1.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView(){
        next = findViewById(R.id.bt_2);
        username1 = findViewById(R.id.et_1);
        username2 = findViewById(R.id.et_2);


        next.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_2:
                String musername1 = username1.getText().toString().trim();
                String musername2 = username2.getText().toString().trim();
                if (musername1.length() < 8 && musername2.length() < 8){//ayezng
                    Toast.makeText(this, "用户名或密码长度必须大于8或小于20", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(musername1) && !TextUtils.isEmpty(musername2)) {
                    if (TextUtils.equals(musername1, musername2)) {
                        Intent intent = new Intent(RegisterActivity1.this, RegisterActivity2.class);
                        String account = username2.getText().toString().trim();
                        intent.putExtra("account", account);
                        startActivity(intent);
                        Toast.makeText(this, "相同", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(this, "不相同", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(this, "输入框为空", Toast.LENGTH_LONG).show();
                }
                break;


        }
    }
    
}