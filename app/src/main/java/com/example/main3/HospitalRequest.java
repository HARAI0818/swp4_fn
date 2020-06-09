package com.example.main3;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class HospitalRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://211.110.104.63/hospitalloading.php";
    private Map<String, String> map;


    public HospitalRequest( Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
