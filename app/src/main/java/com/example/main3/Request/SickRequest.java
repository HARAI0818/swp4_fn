package com.example.main3.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SickRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://211.110.104.63/Sick.php";

    private Map<String, String> map;


    public SickRequest(String Sick_A1, String Sick_A2, String Sick_A3 , String Sick_A4, String Sick_A41, String Sick_A42 , String Sick_A7
            , String Sick_A8, String Sick_A9, String Sick_A10, String Sick_A11, String User_id, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("Sick_A1",Sick_A1);
        map.put("Sick_A2",Sick_A2);
        map.put("Sick_A3",Sick_A3);
        map.put("Sick_A4",Sick_A4);
        map.put("Sick_A41",Sick_A41);
        map.put("Sick_A42",Sick_A42);
        map.put("Sick_A7",Sick_A7);
        map.put("Sick_A8",Sick_A8);
        map.put("Sick_A9",Sick_A9);
        map.put("Sick_A10",Sick_A10);
        map.put("Sick_A11",Sick_A11);
        map.put("User_id",User_id);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}