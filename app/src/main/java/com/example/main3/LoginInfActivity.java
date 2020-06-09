package com.example.main3;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//로그인 정보 보여주는 액티비티
public class LoginInfActivity extends AppCompatActivity {

    private TextView tv_id, tv_pass,tv_birth, tv_location, tv_sex, tv_sick ;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_inf);
        mContext = this;



        tv_id = (TextView) findViewById(R.id.tv_id);
        tv_pass = (TextView) findViewById(R.id.tv_pass);
        tv_birth = (TextView) findViewById(R.id.tv_birth);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_sex = (TextView) findViewById(R.id.tv_sex);

        RbPreference pref = new RbPreference(mContext);
        String User_id = pref.getValue("User_id", "");
        String User_pass = pref.getValue("User_pass", "");
        String User_birth = pref.getValue("User_birth", "");
        String User_sex = pref.getValue("User_sex", "");
        String User_location = pref.getValue("User_location", "");


        tv_id.setText(User_id);
        tv_pass.setText(User_pass);
        tv_birth.setText(User_birth);
        tv_location.setText(User_location);
        tv_sex.setText(User_sex);
    }
}
