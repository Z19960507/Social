package com.example.socialsoftware.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.socialsoftware.R;

public class Logout extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        initView();

    }
    private void initView() {

        Button mBtMainLogout = findViewById(R.id.bt_main_logout);
        mBtMainLogout.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.bt_main_logout) {
            //步骤1：创建一个SharedPreferences对象
            SharedPreferences sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
            //步骤2： 实例化SharedPreferences.Editor对象
            SharedPreferences.Editor editor = sharedPreferences.edit();
            //步骤3：将获取过来的值放入文件
            editor.putString("name","");


            //步骤4：提交
            editor.apply();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
//    public void onDestroy() {
//        super.onDestroy();
//        //步骤1：创建一个SharedPreferences对象
//        SharedPreferences sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
//        //步骤2： 实例化SharedPreferences.Editor对象
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        //步骤3：将获取过来的值放入文件
//        editor.putString("name","");
//        editor.putString("password","");
//
//        //步骤4：提交
//        editor.apply();
//
//    }

}