package com.example.socialsoftware.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.socialsoftware.MainActivity;
import com.example.socialsoftware.R;
import com.luck.picture.lib.permissions.RxPermissions;

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

public class LauncherActivity extends AppCompatActivity {

    private static final long DELAY = 1500;
    private TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.init(getApplication());
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestPermisson();
            }
        }, 100);

        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        final String name = sharedPreferences.getString("name", "");

        if (!TextUtils.isEmpty(name)) {
            final Intent localIntent = new Intent(LauncherActivity.this, MainActivity.class);//你要转向的Activity
            Timer timer = new Timer();
            TimerTask tast = new TimerTask() {
                @Override
                public void run() {
                    localIntent.putExtra("username",name);
                    startActivity(localIntent);//执行
                    finish();
//                    ARouter.getInstance().build(MyRoute.BINDING_ACTIVITY).navigation();
                }
            };
            timer.schedule(tast, DELAY);//3秒后
//                    return;//4w8fam
        } else {
            final Intent localIntent = new Intent(this, LoginActivity.class);//你要转向的Activity
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

    private void requestPermisson(){
        RxPermissions rxPermission = new RxPermissions(this);
//        rxPermission
//                .request(
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,//存储权限
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.CAMERA,
//                        Manifest.permission.RECORD_AUDIO
//                )
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(Boolean aBoolean) throws Exception {
//                        if (aBoolean) {
//                            startActivity(new Intent(LauncherActivity.this,MainActivity.class));
//                            finish();
//                        } else {
//
//                            SetPermissionDialog mSetPermissionDialog = new SetPermissionDialog(LauncherActivity.this);
//                            mSetPermissionDialog.show();
//                            mSetPermissionDialog.setConfirmCancelListener(new SetPermissionDialog.OnConfirmCancelClickListener() {
//                                @Override
//                                public void onLeftClick() {
//
//                                    finish();
//                                }
//
//                                @Override
//                                public void onRightClick() {
//
//                                    finish();
//                                }
//                            });
//                        }
//                    }
//                });
    }
}

//}