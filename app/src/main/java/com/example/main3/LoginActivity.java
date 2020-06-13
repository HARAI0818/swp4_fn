package com.example.main3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.main3.Request.LoginRequest;


import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    private EditText et_id, et_pass;
    private Button btn_login, btn_register;
    private Context mContext;
    private BackPressHandler backPressHandler = new BackPressHandler(this);


    //뒤로 두번 누를시 앱 종료
    @Override
    public void onBackPressed() {
        backPressHandler.onBackPressed("한번 더 누르면 종료됩니다.");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;

        //버튼 , 아이디와 비밀번호 id값 할당
        et_id = (EditText) findViewById(R.id.et_id);
        et_pass = (EditText) findViewById(R.id.et_pass);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);

        //회원가입 클릭시 회원가입 페이지로
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // 로그인 버튼을 클릭 시 수행
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String User_id = et_id.getText().toString();
                String User_pass = et_pass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                            System.out.println("hongchul" + response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 로그인에 성공한 경우
                                String User_name = jsonObject.getString("User_name");
                                String User_num = jsonObject.getString("User_num");
                                String User_id = jsonObject.getString("User_id");
                                String User_pass = jsonObject.getString("User_pass");
                                String User_birth = jsonObject.getString("User_birth");
                                String User_sex = jsonObject.getString("User_sex");
                                String User_location= jsonObject.getString("User_location"); // 로그인한 유저 정보 받기

                                RbPreference pref = new RbPreference(mContext);
                                pref.put("User_name",User_name);
                                pref.put("User_num", User_num);
                                pref.put("User_id", User_id);
                                pref.put("User_pass", User_pass);
                                pref.put("User_birth", User_birth);
                                pref.put("User_sex", User_sex);
                                pref.put("User_location", User_location);
                                pref.put("User_check", true);// 회원정보를 RbPreference.class에 넘김 로그인 유지 기능
                                Toast.makeText(getApplicationContext(),User_id+"님 환영합니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else { // 로그인에 실패한 경우
                                Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                //서버 통신을 위해 값을 보냄
                LoginRequest loginRequest = new LoginRequest(User_id, User_pass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });


    }

}
