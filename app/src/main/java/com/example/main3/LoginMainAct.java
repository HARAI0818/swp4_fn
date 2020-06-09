package com.example.main3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class LoginMainAct<MemberDTO> extends AppCompatActivity {

    private Button btn_reviewa , btn_reviewb , btn_mask, btn_logininfor;
    ArrayList<MemberDTO> members ;
    private Context mContext;
    private AlertDialog dialog;
    private long time= 0;

    //뒤로 두번 누를시 다시 로그인 화면으로
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()-time>=2000){
            time= System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르면 로그아웃합니다.", Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-time<2000){
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
      //  btn_reviewa = (Button) findViewById((R.id.btn_reviewa));
       //btn_reviewb = (Button) findViewById(R.id.btn_reviewb);
      //  btn_mask = (Button) findViewById(R.id.btn_mask);
     //   btn_logininfor = (Button) findViewById(R.id.btn_logininfor);



      /*  // 리뷰 버튼
        btn_reviewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginMainAct.this, ReviewActivity.class);
                startActivity(intent);
            }
        });*/

        //회원정보 버튼
        btn_logininfor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginMainAct.this, LoginInfActivity.class);
                startActivity(intent);
            }
        });

        //리뷰 보는 버튼
       /* btn_reviewb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask().execute();
            }
        });*/

        //마스크 버튼
        btn_mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginMainAct.this);
                RbPreference pref = new RbPreference(mContext);
                String User_birth = pref.getValue("User_birth", "");
                int check = Integer.parseInt(User_birth.substring(3,4));
                int week = 0;
                switch(check){
                    case 1 :
                    case 6 :
                        week = 2;
                        break;
                    case 2 :
                    case 7 :
                        week = 3;
                        break;
                    case 3 :
                    case 8 :
                        week = 4;;
                        break;
                    case 4 :
                    case 9 :
                        week = 5;;
                        break;
                    case 5 :
                    case 0 :
                        week = 6;;
                        break;
                    default:
                        break;
                }

                Calendar cal = Calendar.getInstance();
                int tweek = cal.get(Calendar.DAY_OF_WEEK);
                if (week == tweek){
                    dialog = builder.setMessage("오늘은 구매가능한 날입니다.")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog.show();
                }
                else if(tweek == 7 || tweek == 1){
                    dialog = builder.setMessage("주중에 구매하지 않았다면\n구매 가능한 날입니다.")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog.show();
                }
                else{
                    dialog = builder.setMessage("오늘은 구매불가능한 날입니다.")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog.show();
                }

            }
        });


    }



    //리뷰 보여주는 기능 클래스

    /*class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target;
        RbPreference pref = new RbPreference(mContext);
        String Review_user = pref.getValue("User_id", "");
        String sendMsg;
        @Override
        protected void onPreExecute() {
            target="http://211.110.104.63/Reviewsh.php";
        }


        @Override
        protected String doInBackground(Void... params) {

            InputStream is=null;
            InputStreamReader isr =null;
            BufferedReader reader=null;
            StringBuffer stringBuffer =new StringBuffer();

            try{

                URL url =new URL(target);
                HttpURLConnection httpURLConnection =(HttpURLConnection)url.openConnection();


                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpURLConnection.setRequestMethod("POST");//데이터를 POST 방식으로 전송합니다.
                OutputStreamWriter osw = new OutputStreamWriter(httpURLConnection.getOutputStream());
                sendMsg = "Review_user="+Review_user;//보낼 정보인데요. GET방식으로 작성합니다. ex) "id=rain483&pwd=1234";
                //회원가입처럼 보낼 데이터가 여러 개일 경우 &로 구분하여 작성합니다.
                osw.write(sendMsg);//OutputStreamWriter에 담아 전송합니다.
                osw.flush();
                //jsp와 통신이 정상적으로 되었을 때 할 코드들입니다.

                httpURLConnection.setConnectTimeout(10000);

                if(httpURLConnection.getResponseCode()== HttpURLConnection.HTTP_OK){

                    is=httpURLConnection.getInputStream();
                    reader=new BufferedReader(new InputStreamReader(is));

                    while(true){
                        String stringLine =reader.readLine();
                        if(stringLine==null)break;
                        stringBuffer.append(stringLine+"\n");
                    }

                }

                parsing(stringBuffer.toString());
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    if(reader!=null) reader.close();
                    if(isr!=null) isr.close();
                    if(is!=null)is.close();

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected void onPostExecute(String s) {

            Intent intent =new Intent(LoginMainAct.this, ReviewshActivity.class);

            Bundle bundle =new Bundle();
            bundle.putSerializable("members", members);
            intent.putExtra("members",  bundle);
            startActivity(intent);

        }

    } */


    /*public void parsing(String data){

        members=new ArrayList<>();

        try{
            JSONObject jsonObject =new JSONObject(data);
            JSONArray jsonArray=new JSONArray(jsonObject.getString("response"));

            //arrayList 클리어
            members.clear();

            for(int i=0; i<jsonArray.length(); i++){

                MemberDTO member=new MemberDTO();

                JSONObject jsonObject1=(JSONObject)jsonArray.get(i);
                member.setReview_num(jsonObject1.getString("Review_num"));
                member.setReview_score(jsonObject1.getString("Review_score"));
                member.setReview_time(jsonObject1.getString("Review_time"));
                member.setReview_title(jsonObject1.getString("Review_title"));
                member.setReview_contents(jsonObject1.getString("Review_contents"));
                member.setReview_user(jsonObject1.getString("Review_user"));
                member.setReview_hos(jsonObject1.getString("Review_hos"));
                members.add(member);
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }*/



}
