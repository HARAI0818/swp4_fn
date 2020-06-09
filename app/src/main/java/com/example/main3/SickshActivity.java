package com.example.main3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class SickshActivity extends AppCompatActivity {
    private Context mContext=this;
    private TextView tv_A1, tv_A2 ,tv_A3, tv_A4, tv_A5 , tv_A6 , tv_A7 , tv_A8 , tv_A9 , tv_A10, tv_A11;
    private Button btn_delete ;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sicksh);

        RbPreference pref = new RbPreference(mContext);
        final String Sick_user = pref.getValue("User_id", "");

        tv_A1 = (TextView) findViewById(R.id.tv_A1);
        tv_A2 = (TextView) findViewById(R.id.tv_A2);
        tv_A3 = (TextView) findViewById(R.id.tv_A3);
        tv_A4 = (TextView) findViewById(R.id.tv_A4);
        tv_A5 = (TextView) findViewById(R.id.tv_A5);
        tv_A6 = (TextView) findViewById(R.id.tv_A6);
        tv_A7 = (TextView) findViewById(R.id.tv_A7);
        tv_A8 = (TextView) findViewById(R.id.tv_A8);
        tv_A9 = (TextView) findViewById(R.id.tv_A9);
        tv_A10 = (TextView) findViewById(R.id.tv_A10);
        tv_A11 = (TextView) findViewById(R.id.tv_A11);
        btn_delete = (Button) findViewById(R.id.btn_delete);




        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SickshActivity.this);
                dialog = builder.setMessage("문진표를 삭제 하시겠습니까?")
                        .setNegativeButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Response.Listener<String> responseListener = new Response.Listener<String>(){

                                    @Override
                                    public void onResponse(String response) {
                                        try{
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");
                                            if(success){//사용할 수 있는 아이디라면
                                                Toast.makeText(getApplicationContext(),"문진표가 정상적으로 삭제되었습니다.",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(SickshActivity.this, MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                            }else{//사용할 수 없는 아이디라면
                                                Toast.makeText(getApplicationContext(),"문진표 삭제가 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                        catch(Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                };//Response.Listener 완료

                                //Volley 라이브러리를 이용해서 실제 서버와 통신을 구현하는 부분
                                SickdeRequest sickdeRequest = new SickdeRequest(Sick_user, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(SickshActivity.this);
                                queue.add(sickdeRequest);
                            }
                        })
                        .setPositiveButton("취소", null)
                        .create();
                dialog.show();



            }

        });






        Response.Listener<String> responseListener = new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){//사용할 수 있는 아이디라면
                        String Sick_A1 = jsonResponse.getString("Sick_A1");
                        String Sick_A2 = jsonResponse.getString("Sick_A2");
                        String Sick_A3 = jsonResponse.getString("Sick_A3");
                        String Sick_A4 = jsonResponse.getString("Sick_A4");
                        String Sick_A41 = jsonResponse.getString("Sick_A41") + "년";
                        String Sick_A42= jsonResponse.getString("Sick_A42") + "개비" ;
                        String Sick_A7 = jsonResponse.getString("Sick_A7");
                        String Sick_A8 = jsonResponse.getString("Sick_A8")+ "잔";
                        String Sick_A9 = jsonResponse.getString("Sick_A9");
                        String Sick_A10 = jsonResponse.getString("Sick_A10");
                        String Sick_A11 = jsonResponse.getString("Sick_A11");
                        tv_A1.setText(Sick_A1);
                        tv_A2.setText(Sick_A2);
                        tv_A3.setText(Sick_A3);
                        tv_A4.setText(Sick_A4);
                        tv_A5.setText(Sick_A41);
                        tv_A6.setText(Sick_A42);
                        tv_A7.setText(Sick_A7);
                        tv_A8.setText(Sick_A8);
                        tv_A9.setText(Sick_A9);
                        tv_A10.setText(Sick_A10);
                        tv_A11.setText(Sick_A11);

                    }else{//사용할 수 없는 아이디라면
                    }

                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        };//Response.Listener 완료

        //Volley 라이브러리를 이용해서 실제 서버와 통신을 구현하는 부분
        SickshRequest sickshRequest = new SickshRequest(Sick_user, responseListener);
        RequestQueue queue = Volley.newRequestQueue(SickshActivity.this);
        queue.add(sickshRequest);

    }




}
