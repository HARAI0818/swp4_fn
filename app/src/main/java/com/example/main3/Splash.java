package com.example.main3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.Toast;


public class Splash extends Activity {
    private Context mContext = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        Handler hd = new Handler();
        hd.postDelayed(new splashier(), 3000); // 1초 후에 hd handler 실행  3000ms = 3초

    }

    private class splashier implements Runnable{
        public void run(){
            RbPreference pref = new RbPreference(mContext);
            final Boolean User_check = pref.getValue("User_check", false);
            final String User_id = pref.getValue("User_id","");
            if(User_check == true) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                Toast.makeText(getApplicationContext(),User_id+"님 환영합니다.",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                Splash.this.finish(); // 로딩페이지 Activity  제거
            }
            else {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
                Splash.this.finish(); // 로딩페이지 Activity  제거
            }

        }
    }

    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }

}
