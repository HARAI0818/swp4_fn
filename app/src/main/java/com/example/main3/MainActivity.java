package com.example.main3;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView text;
    private AppBarConfiguration mAppBarConfiguration;
    Context mContext = this;
    private BackPressHandler backPressHandler = new BackPressHandler(this);
    private AlertDialog dialog;
    ArrayList<Clinic> clinics = new ArrayList<Clinic>();
    ArrayList<Medi> Medi = new ArrayList<Medi>();
    ArrayList<Location> clinic_address = new ArrayList<Location>();
    ArrayList<Location> clinic_address1 = new ArrayList<Location>();
    private ProgressDialog progressDialog;


    //뒤로 두번 누를시 앱 종료
    @Override
    public void onBackPressed() {
        backPressHandler.onBackPressed("한번 더 누르면 종료됩니다.");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.btn_sick,R.id.btn_mask)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawer.closeDrawers();
                int id = menuItem.getItemId();
                if(id == R.id.nav_gallery){
                    Intent intent = new Intent(MainActivity.this, LoginInfActivity.class);
                    startActivity(intent);
                }
                else if(id == R.id.btn_sick){
                    RbPreference pref = new RbPreference(mContext);
                    String Sick_user = pref.getValue("User_id", "");
                    Response.Listener<String> responseListener = new Response.Listener<String>(){

                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if(success){
                                    Intent intent = new Intent(MainActivity.this, SickshActivity.class);
                                    startActivity(intent);

                                }else{//사용할 수 없는 아이디라면
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    dialog = builder.setMessage("저장되어 있는 문진표가 없어 \n문진표를 작성해야합니다.")
                                            .setNegativeButton("작성", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(MainActivity.this, SickActivity.class);
                                                    startActivity(intent);
                                                }
                                            })
                                            .setPositiveButton("취소", null)
                                            .create();
                                    dialog.show();
                                }

                            }
                            catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    };//Response.Listener 완료

                    //Volley 라이브러리를 이용해서 실제 서버와 통신을 구현하는 부분
                    SickshRequest sickshRequest = new SickshRequest(Sick_user, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(sickshRequest);
                }
                else if(id == R.id.btn_mask){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                        dialog = builder.setMessage("오늘은 구매가능한 날입니다.\n"+
                                "\n※정보※\n출생연도 끝자리 1,6 : 월요일 구매 가능\n" +
                                "출생연도 끝자리 2,7 : 화요일 구매 가능\n" +
                                "출생연도 끝자리 3,8 : 수요일 구매 가능\n" +
                                "출생연도 끝자리 4,9 : 목요일 구매 가능\n" +
                                "출생연도 끝자리 5,0 : 금요일 구매 가능\n")
                                .setNegativeButton("OK", null)
                                .create();
                        dialog.show();
                    }
                    else if(tweek == 7 || tweek == 1){
                        dialog = builder.setMessage("주중에 구매하지 않았다면\n구매 가능한 날입니다.\n"+
                                "\n※정보※\n출생연도 끝자리 1,6 : 월요일 구매 가능\n" +
                                "출생연도 끝자리 2,7 : 화요일 구매 가능\n" +
                                "출생연도 끝자리 3,8 : 수요일 구매 가능\n" +
                                "출생연도 끝자리 4,9 : 목요일 구매 가능\n" +
                                "출생연도 끝자리 5,0 : 금요일 구매 가능\n")
                                .setNegativeButton("OK", null)
                                .create();
                        dialog.show();
                    }
                    else{
                        dialog = builder.setMessage("오늘은 구매불가능한 날입니다.\n"+
                                "\n※정보※\n출생연도 끝자리 1,6 : 월요일 구매 가능\n" +
                                "출생연도 끝자리 2,7 : 화요일 구매 가능\n" +
                                "출생연도 끝자리 3,8 : 수요일 구매 가능\n" +
                                "출생연도 끝자리 4,9 : 목요일 구매 가능\n" +
                                "출생연도 끝자리 5,0 : 금요일 구매 가능\n")
                                .setNegativeButton("OK", null)
                                .create();
                        dialog.show();
                    }                }
                return true;
            }
        });


        View navView =  navigationView.inflateHeaderView(R.layout.nav_header_main);

        ImageButton btn1 = (ImageButton)findViewById(R.id.bottom1);
        ImageButton btn2 = (ImageButton)findViewById(R.id.bottom2);
        ImageButton btn3 = (ImageButton)findViewById(R.id.bottom3);
        ImageButton btn4 = (ImageButton)findViewById(R.id.bottom4);
        ImageButton btn5 = (ImageButton)findViewById(R.id.bottom5);
        ImageButton btn6 = (ImageButton)findViewById(R.id.bottom6);
        Button btn_login = (Button)navView.findViewById(R.id.button_login);
        TextView Userid = (TextView)navView.findViewById(R.id.tv_Userid);


        RbPreference pref = new RbPreference(mContext);
        String User_id = pref.getValue("User_id", "");
        Userid.setText(User_id);


        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("잠시 기다려 주세요.");
                progressDialog.show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        String result = null;
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            result = jsonResponse.getString("response");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            JSONArray jArray = new JSONArray(result);
                            clinics.clear();

                            for (int i = 0; i <50 ; i++) {
                                Clinic clinic = new Clinic();
                                JSONObject jsonObject1 = (JSONObject) jArray.get(i);
                                clinic.setNumber(jsonObject1.getString("Hospital_num"));
                                clinic.setSample(jsonObject1.getString("Hospital_category"));
                                clinic.setName(jsonObject1.getString("Hospital_name"));
                                clinic.setAddress(jsonObject1.getString("Hospital_location"));
                                clinic.setPhoneNumber(jsonObject1.getString("Hospital_tel"));




                                clinic.setMonday(jsonObject1.getString("Hospital_M"));
                                clinic.setTuesday(jsonObject1.getString("Hospital_Tu"));
                                clinic.setWednesday(jsonObject1.getString("Hospital_W"));
                                clinic.setThursday(jsonObject1.getString("Hospital_Th"));
                                clinic.setFriday(jsonObject1.getString("Hospital_F"));
                                clinic.setSaturday(jsonObject1.getString("Hospital_Sa"));
                                clinic.setSunday(jsonObject1.getString("Hospital_Su"));
                                clinic.setHoliday(jsonObject1.getString("Hospital_H"));
                                clinics.add(clinic);
                            }

                            clinic_address.clear();
                            for(int i =0; i<clinics.size(); i++){
                                clinic_address.add(addrToPoint(mContext, clinics.get(i).getAddress()));
                            }

                            Intent intent = new Intent(MainActivity.this, search_hAct.class);
                            intent.putExtra("clinic", clinics);
                            intent.putExtra("clinic_addr", clinic_address);
                            progressDialog.dismiss();
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };//Response.Listener 완료
                HospitalRequest hospitalrequest = new HospitalRequest(responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(hospitalrequest);

            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), coronaAct.class);
                startActivity(intent);

            }
        });

        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), newsAct.class);
                startActivity(intent);

            }
        });

        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), search_medAct.class);
                startActivity(intent);

            }
        });

        btn5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), YoutubeAct.class);
                startActivity(intent);

            }
        });

        btn6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("잠시 기다려 주세요.");
                progressDialog.show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        String result = null;
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            result = jsonResponse.getString("response");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            JSONArray jArray = new JSONArray(result);
                            Medi.clear();

                            for (int i = 0; i <50; i++) {
                                Medi medi = new Medi();
                                JSONObject jsonObject1 = (JSONObject) jArray.get(i);
                                medi.setNumber(jsonObject1.getString("Medi_num"));
                                medi.setName(jsonObject1.getString("Medi_name"));
                                medi.setAddress(jsonObject1.getString("Medi_location"));
                                medi.setPhoneNumber(jsonObject1.getString("Medi_tel"));

                                medi.setMonday(jsonObject1.getString("Medi_M"));
                                medi.setTuesday(jsonObject1.getString("Medi_Tu"));
                                medi.setWednesday(jsonObject1.getString("Medi_W"));
                                medi.setThursday(jsonObject1.getString("Medi_Th"));
                                medi.setFriday(jsonObject1.getString("Medi_F"));
                                medi.setSaturday(jsonObject1.getString("Medi_Sa"));
                                medi.setSunday(jsonObject1.getString("Medi_Su"));
                                medi.setHoliday(jsonObject1.getString("Medi_H"));
                                Medi.add(medi);
                            }

                            clinic_address1.clear();
                            for(int i =0; i<Medi.size(); i++){
                                clinic_address1.add(addrToPoint(mContext, Medi.get(i).getAddress()));
                            }

                            Intent intent = new Intent(MainActivity.this, search_mediAct.class);
                            intent.putExtra("clinic", Medi);
                            intent.putExtra("clinic_addr", clinic_address1);
                            progressDialog.dismiss();
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };//Response.Listener 완료
                MediRequest medirequest = new MediRequest(responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(medirequest);

            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RbPreference pref = new RbPreference(mContext);
                pref.delete();
                Toast.makeText(getApplicationContext(),"정상적으로 로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });





        //LinearLayout newActivity = (LinearLayout) findViewById(R.id.newActivity);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public static Location addrToPoint(Context context, String addr) {
        Location location = new Location("");
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocationName(addr,3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(addresses != null) {
            for(int i = 0 ; i < addresses.size() ; i++) {
                Address lating = addresses.get(i);
                location.setLatitude(lating.getLatitude());
                location.setLongitude(lating.getLongitude());
            }
        }
        return location;
    } // 주소명으로 위도 경도를 구하는 메소드
}
