package com.example.main3.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
  final static private String URL = "http://211.110.104.63/Login.php";

    private Map<String, String> map;


    public LoginRequest(String User_id, String User_pass, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("User_id",User_id);
        map.put("User_pass", User_pass);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
