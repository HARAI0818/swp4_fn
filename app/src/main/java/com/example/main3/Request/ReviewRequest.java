package com.example.main3.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReviewRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://211.110.104.63/Review.php";

    private Map<String, String> map;


    public ReviewRequest(String Review_contents, String Review_score , String Review_time, String Review_user, String Review_hos  , Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("Review_contents", Review_contents);
        map.put("Review_score",Review_score);
        map.put("Review_time", Review_time);
        map.put("Review_user", Review_user);
        map.put("Review_hos", Review_hos);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
