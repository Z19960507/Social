package com.example.socialsoftware.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.socialsoftware.R;
import com.example.socialsoftware.arouter.MyRoute;
import com.example.socialsoftware.db.DBOpenHelper;
import com.example.socialsoftware.model.User;
import com.vondear.rxtool.RxBarTool;
import com.vondear.rxtool.RxTool;

@Route(path = MyRoute.MINE_INF_ACTIVITY)
public class MineInfActivity extends AppCompatActivity {

    private TextView tvShowRealName;
    private TextView tvShowRealNameJP;
    private TextView tvShowGender;
    private TextView tvShowDateOfBirth;
    private TextView tvShowNationality;
    private TextView tvShowPostcode;
    private TextView tvShowAddress;
    private TextView tvShowPhoneNumber;
    private User user;

    private Button btEdit;
    private Button btSave;
    private Button btBack;
    private EditText etRealName;
    private EditText etRealNameJP;
    private EditText etShowGender;
    private EditText etShowDateOfBirth;
    private EditText etShowNationality;
    private EditText etShowPostcode;
    private EditText etShowAddress;
    private EditText etShowPhoneNumber;
    private SharedPreferences sharedPreferences;
    private DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxTool.init(getApplication());
        RxBarTool.setNoTitle(this);
        setContentView(R.layout.activity_mine_inf);

        initView();

        dbOpenHelper = new DBOpenHelper(this);
        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name","");

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this,"查询失败：查询条件为空",Toast.LENGTH_SHORT).show();
        }else {
            User user1 = dbOpenHelper.getUserByName(name);
            if (user1 != null) {
                tvShowRealName.setText(user1.getReal_name());
                tvShowRealNameJP.setText(user1.getReal_name_jp());
                tvShowGender.setText(user1.getGender());
                tvShowDateOfBirth.setText(user1.getDate_of_birth());
                tvShowNationality.setText(user1.getNationality());
                tvShowPostcode.setText(user1.getPostcode());
                tvShowAddress.setText(user1.getAddress());
                tvShowPhoneNumber.setText(user1.getPhone_number());
            } else {
                Toast.makeText(this, "查询失败", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void initView() {


        tvShowRealName = findViewById(R.id.tv_show_real_name);
        tvShowRealNameJP = findViewById(R.id.tv_show_real_name_jp);
        tvShowGender = findViewById(R.id.tv_show_gender);
        tvShowDateOfBirth = findViewById(R.id.tv_show_date_of_birth);
        tvShowNationality = findViewById(R.id.tv_show_nationality);
        tvShowPostcode = findViewById(R.id.tv_show_postcode);
        tvShowAddress = findViewById(R.id.tv_show_address);
        tvShowPhoneNumber = findViewById(R.id.tv_show_phone_number);


        btEdit = findViewById(R.id.bt_edit);
        btSave = findViewById(R.id.bt_save);
        btBack = findViewById(R.id.bt_back);
        etRealName = findViewById(R.id.et_real_name);
        etRealNameJP = findViewById(R.id.et_real_name_jp);
        etShowGender = findViewById(R.id.et_show_gender);
        etShowDateOfBirth = findViewById(R.id.et_show_date_of_birth);
        etShowNationality = findViewById(R.id.et_show_nationality);
        etShowPostcode = findViewById(R.id.et_show_postcode);
        etShowAddress = findViewById(R.id.et_show_address);
        etShowPhoneNumber = findViewById(R.id.et_show_phone_number);

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditHideText();
            }
        });


        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = sharedPreferences.getString("name", "");
                //把输入框里的数据赋值给user的属性
                user = new User();
                user.setReal_name(etRealName.getText().toString());
                user.setReal_name_jp(etRealNameJP.getText().toString());
                user.setGender(etShowGender.getText().toString());
                user.setDate_of_birth(etShowDateOfBirth.getText().toString());
                user.setNationality(etShowNationality.getText().toString());
                user.setPostcode(etShowPostcode.getText().toString());
                user.setAddress(etShowAddress.getText().toString());
                user.setPhone_number(etShowPhoneNumber.getText().toString());


                //更新数据库中的user，把獲取的值賦值給dbOpenHelper的方法update的User
                int res = dbOpenHelper.updateAll(user, name);
                if (res == 0) {
                    Toast.makeText(MineInfActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MineInfActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                }


                /*
                回显数据
                1.sharedPreferences = 之前創建的方法，獲取name中的當前賬號
                2.在User中創建一個user1（因為返回值是User對象，所以在User中創建一個user1來接收返回值）
                3.使用user1獲取dbOpenHelper中正在登錄的賬號
                4.在tv中顯示user1中獲得的輸入框中的數據
                 */
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        user = dbOpenHelper.getUserByName(name);

                        tvShowRealName.setText(user.getReal_name());
                        tvShowRealNameJP.setText(user.getReal_name_jp());
                        tvShowGender.setText(user.getGender());
                        tvShowDateOfBirth.setText(user.getDate_of_birth());
                        tvShowNationality.setText(user.getNationality());
                        tvShowPostcode.setText(user.getPostcode());
                        tvShowAddress.setText(user.getAddress());
                        tvShowPhoneNumber.setText(user.getPhone_number());
                    }
                });

                showTextHideEdit();
            }
        });


//    }
    }


    private void showEditHideText() {
        btEdit.setVisibility(View.GONE);
        btSave.setVisibility(View.VISIBLE);

        tvShowRealName.setVisibility(View.GONE);
        etRealName.setVisibility(View.VISIBLE);
        etRealName.setText(tvShowRealName.getText());

        tvShowRealNameJP.setVisibility(View.GONE);
        etRealNameJP.setVisibility(View.VISIBLE);
        etRealNameJP.setText(tvShowRealNameJP.getText());

        tvShowGender.setVisibility(View.GONE);
        etShowGender.setVisibility(View.VISIBLE);
        etShowGender.setText(tvShowGender.getText());

        tvShowDateOfBirth.setVisibility(View.GONE);
        etShowDateOfBirth.setVisibility(View.VISIBLE);
        etShowDateOfBirth.setText(tvShowDateOfBirth.getText());

        tvShowNationality.setVisibility(View.GONE);
        etShowNationality.setVisibility(View.VISIBLE);
        etShowNationality.setText(tvShowNationality.getText());

        tvShowPostcode.setVisibility(View.GONE);
        etShowPostcode.setVisibility(View.VISIBLE);
        etShowPostcode.setText(tvShowPostcode.getText());

        tvShowAddress.setVisibility(View.GONE);
        etShowAddress.setVisibility(View.VISIBLE);
        etShowAddress.setText(tvShowAddress.getText());

        tvShowPhoneNumber.setVisibility(View.GONE);
        etShowPhoneNumber.setVisibility(View.VISIBLE);
        etShowPhoneNumber.setText(tvShowPhoneNumber.getText());
    }

    private void showTextHideEdit() {
        btEdit.setVisibility(View.VISIBLE);
        btSave.setVisibility(View.GONE);

        tvShowRealName.setVisibility(View.VISIBLE);
        etRealName.setVisibility(View.GONE);

        tvShowRealNameJP.setVisibility(View.VISIBLE);
        etRealNameJP.setVisibility(View.GONE);

        tvShowGender.setVisibility(View.VISIBLE);
        etShowGender.setVisibility(View.GONE);

        tvShowDateOfBirth.setVisibility(View.VISIBLE);
        etShowDateOfBirth.setVisibility(View.GONE);

        tvShowNationality.setVisibility(View.VISIBLE);
        etShowNationality.setVisibility(View.GONE);

        tvShowPostcode.setVisibility(View.VISIBLE);
        etShowPostcode.setVisibility(View.GONE);

        tvShowAddress.setVisibility(View.VISIBLE);
        etShowAddress.setVisibility(View.GONE);

        tvShowPhoneNumber.setVisibility(View.VISIBLE);
        etShowPhoneNumber.setVisibility(View.GONE);
    }

}