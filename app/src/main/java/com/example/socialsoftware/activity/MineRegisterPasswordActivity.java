package com.example.socialsoftware.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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


public class MineRegisterPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private String username;
    private EditText oldPassword;
    private EditText newPassword;
    private EditText confirmedPassword;
    private Button back;
    private Button confirmed;
    private DBOpenHelper mDbOpenHepler;
    private String account;
    private String name;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_register_password);
        initView();
        Intent intent = getIntent();
        mDbOpenHepler = new DBOpenHelper(this);

        username = intent.getStringExtra("username");




    }
    private void initView() {
        oldPassword = findViewById(R.id.ed_old_password);
        newPassword = findViewById(R.id.ed_new_password);
        confirmedPassword = findViewById(R.id.ed_confirmed_new_password);
        back = findViewById(R.id.id_back_mine);
        confirmed = findViewById(R.id.bt_confirmed);


        back.setOnClickListener(this);
        confirmed.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_back_mine:
                finish();
                return;
            case R.id.bt_confirmed:

                String Password = oldPassword.getText().toString().trim();
                String mConfirmedPassword = newPassword.getText().toString().trim();
                String nPassword = confirmedPassword.getText().toString().trim();
                ArrayList<User> data = mDbOpenHepler.getAllData();


                if (!TextUtils.isEmpty(Password) && !TextUtils.isEmpty(mConfirmedPassword) && !TextUtils.isEmpty(nPassword)) {
                    if (mConfirmedPassword.length() < 8 || nPassword.length() < 8){//ayezng
                        Toast.makeText(this, "密码长度必须大于8或小于20", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    boolean match = false;
                    User user = mDbOpenHepler.selectUserByName(username);

                    if (TextUtils.isEmpty(username)) {
                        Toast.makeText(this,"查询失败：查询条件为空",Toast.LENGTH_SHORT).show();
                    }
                    if (!TextUtils.equals(Password, user.getPassword())) {
                        Toast.makeText(this, "旧密码输入错误", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!TextUtils.equals(nPassword, mConfirmedPassword)) {
                        Toast.makeText(this, "新密码输入不相同", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (user != null && !TextUtils.equals(nPassword, user.getPassword()) && TextUtils.equals(nPassword, mConfirmedPassword)) {
                        Toast.makeText(this, "新密码可用", Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                        mDbOpenHepler.update(username,nPassword);
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                        return;//y73hrn
                    }
                    match = TextUtils.equals(nPassword, user.getPassword()) && TextUtils.equals(mConfirmedPassword, user.getPassword());
                    if (match) {
                        Toast.makeText(this, "新密码与旧密码相同", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(this, MainActivity2.class);
//                        startActivity(intent);
//                        finish();//销毁此Activity
                        return;
                    }
                }
                else {
                    Toast.makeText(this, "密码输入框为空", Toast.LENGTH_LONG).show();

                }
                break;

        }

    }
}