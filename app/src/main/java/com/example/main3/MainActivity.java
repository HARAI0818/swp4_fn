package com.example.main3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import org.json.JSONObject;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView text;
    private AppBarConfiguration mAppBarConfiguration;
    Context mContext = this;
    private BackPressHandler backPressHandler = new BackPressHandler(this);
    private AlertDialog dialog;


    //뒤로 두번 누를시 앱 종료
    @Override
    public void onBackPressed() {
        backPressHandler.onBackPressed("뒤로가기 버튼 한번 더 누르면 종료");
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
                                    dialog = builder.setMessage("저장되어 있는 문진표가 없어 문진표를 작성합니다.")
                                            .setNegativeButton("예", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(MainActivity.this, SickActivity.class);
                                                    startActivity(intent);
                                                }
                                            })
                                            .setPositiveButton("아니요", null)
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
                Intent intent = new Intent(getApplicationContext(), LoadingActivity.class);
                startActivity(intent);

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

        /*btn6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), search_hAct.class);
                startActivity(intent);

            }
        });*/


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


}
