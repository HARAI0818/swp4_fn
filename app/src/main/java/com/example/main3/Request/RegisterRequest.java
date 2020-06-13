package com.example.main3.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://211.110.104.63/Register.php";

    private Map<String, String> map;


    public RegisterRequest(String User_name,String User_id, String User_pass, String User_birth, String User_sex, String User_location , Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("User_name",User_name);
        map.put("User_id",User_id);
        map.put("User_pass", User_pass);
        map.put("User_birth", User_birth);
        map.put("User_sex", User_sex);
        map.put("User_location", User_location);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
