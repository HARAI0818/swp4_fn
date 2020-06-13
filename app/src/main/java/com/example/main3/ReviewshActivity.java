package com.example.main3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ReviewshActivity extends AppCompatActivity {

    RecyclerView mRecyclerView = null;
    ArrayList<MemberDTO> members = null;
    public String Review_hos = "";
    private LinearLayoutManager mLinearLayoutManager = null;
    Context mContext = this;
    String User_id ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewsh);

        Intent intent = getIntent(); /*데이터 수신*/
        Review_hos = intent.getExtras().getString("Review_hos");

        RbPreference pref = new RbPreference(mContext);
        User_id = pref.getValue("User_id", "");



        Response.Listener<String> responseListener = new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                String result = null;
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    result = jsonResponse.getString("response");
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                parsingJSONData(result);
            }
        };//Response.Listener 완료

        //Volley 라이브러리를 이용해서 실제 서버와 통신을 구현하는 부분
        ReviewshRequest reviewshrequest = new ReviewshRequest(Review_hos, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ReviewshActivity.this);
        queue.add(reviewshrequest);



        FloatingActionButton fab = findViewById(R.id.btn_write);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                String Review_hos = ((search_hAct)search_hAct.context_main).Review_hos;
                intent.putExtra("Review_hos",Review_hos);
                startActivity(intent);
            }
        });

    }

    private void parsingJSONData(String data) {

        members = new ArrayList<>();
        try {
            JSONArray jArray = new JSONArray(data);
            for(int i = 0; i < jArray.length(); i++) {
                MemberDTO member = new MemberDTO();
                JSONObject jsonObject1 = (JSONObject) jArray.get(i);
                member.setReview_score(jsonObject1.getString("Review_score"));
                member.setReview_time(jsonObject1.getString("Review_time"));
                member.setReview_title(jsonObject1.getString("Review_title"));
                member.setReview_contents(jsonObject1.getString("Review_contents"));
                member.setReview_user(jsonObject1.getString("Review_user"));
                members.add(member);
            }
            RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main_list);
            mLinearLayoutManager = new LinearLayoutManager(mContext);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            ReviewAdapter adapter = new ReviewAdapter(members , User_id , this);
            mRecyclerView.setAdapter(adapter);

        } catch(JSONException e) {
            e.printStackTrace();
        }

    }


}