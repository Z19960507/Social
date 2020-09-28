package com.example.socialsoftware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

//public class MainActivity extends AppCompatActivity {


//    Button btn1, btn2;
//    TextView text1;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        text1 = findViewById(R.id.TextView_1);
//        text1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
//                String userId = sharedPreferences.getString("name", "");
//
//                if (userId != null && !userId.equals("")) {
//                    Intent intent = new Intent(MainActivity.this, Logout.class);
//                    startActivity(intent);
////                    return;//4w8fam
//                } else {
//                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
//                    startActivity(intent);
//                }
//            }
//        });
//
//    }

public class MainActivity extends AppCompatActivity {

    private static final long DELAY = 3000;
    private TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("name", "");

        if (userId != null && !userId.equals("")) {
            final Intent localIntent = new Intent(this, Logout.class);//你要转向的Activity
            Timer timer = new Timer();
            TimerTask tast = new TimerTask() {
                @Override
                public void run() {
                    startActivity(localIntent);//执行
                }
            };
            timer.schedule(tast, DELAY);//3秒后
//                    return;//4w8fam
        } else {
            final Intent localIntent = new Intent(this, MainActivity2.class);//你要转向的Activity
            Timer timer = new Timer();
            TimerTask tast = new TimerTask() {
                @Override
                public void run() {
                    startActivity(localIntent);//执行
                }
            };
            timer.schedule(tast, DELAY);//10秒后
        }


    }
}

//}