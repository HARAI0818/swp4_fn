package com.example.main3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReviewActivity extends AppCompatActivity {

    private EditText et_title , et_contents ;
    private Button btn_review;
    private Context mContext ;
    private String Review_hos;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 시작시 처음으로 실행되는 생명주기!
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        mContext = this;



        // 타이틀값
        et_title = (EditText) findViewById(R.id.et_title);
        et_contents = (EditText) findViewById(R.id.et_contents);
        final RatingBar rb = (RatingBar)findViewById(R.id.et_score);

        // 작성 버튼 클릭 시 수행
        btn_review = (Button) findViewById(R.id.btn_review);
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String Review_title = et_title.getText().toString();
                String Review_contents = et_contents.getText().toString();
                float rs = rb.getRating();
                String Review_score = String.valueOf(rs);
                Intent intent = getIntent(); /*데이터 수신*/
                final String Review_hos = intent.getExtras().getString("Review_hos");

                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");

                RbPreference pref = new RbPreference(mContext);
                String Review_user = pref.getValue("User_id", "");
                String Review_time  = simpleDate.format(mDate);

                if(Review_title.equals("")||Review_contents.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReviewActivity.this);
                    dialog = builder.setMessage("비어있는 정보가 있습니다. \n전부 입력해주세요.")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog.show();
                    return;
                }



                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 리뷰등록 성공시
                                Toast.makeText(getApplicationContext(),"리뷰 작성에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ReviewActivity.this, ReviewshActivity.class); //리뷰 쓰기를 어디로 옮길지 정하고 바꿀것
                                intent.putExtra("Review_hos", Review_hos);
                                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else { // 리뷰등록 실패시
                                Toast.makeText(getApplicationContext(),"리뷰 작성에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.
                ReviewRequest reviewRequest = new ReviewRequest(Review_title , Review_contents ,Review_score,Review_time,Review_user,Review_hos,responseListener);
                RequestQueue queue = Volley.newRequestQueue(ReviewActivity.this);
                queue.add(reviewRequest);

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}