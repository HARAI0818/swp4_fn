package com.example.main3;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SickdeRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://211.110.104.63/Sickde.php";
    private Map<String, String> map;


    public SickdeRequest(String Sick_user , Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("Sick_user",Sick_user);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}