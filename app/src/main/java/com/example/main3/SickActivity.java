package com.example.main3;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentManager;
        import androidx.fragment.app.FragmentTransaction;

        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.widget.Toast;

        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.toolbox.Volley;

        import org.json.JSONException;
        import org.json.JSONObject;

public class SickActivity extends AppCompatActivity {
    String A1,A2,A3,A4,A41,A42,A7,A9,A10,A11,A8;
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sick);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, SickFrag1.newInstance()).commit();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment).commit();      // Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
    }


    public void getAll() {
        RbPreference pref = new RbPreference(mContext);
        final String User_id = pref.getValue("User_id", "");
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) { // 문진표 작성 완료시
                        Toast.makeText(getApplicationContext(),"문진표 작성이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SickActivity.this, MainActivity.class); //로그인 엑티비티로
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else { // 문진표 작성 실패시
                        Toast.makeText(getApplicationContext(),"문진표 작성이 실패되었습니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        // 서버로 Volley를 이용해서 요청을 함.
        SickRequest sickRequest = new SickRequest (A1,A2,A3,A4,A41,A42,A7,A8,A9,A10,A11,User_id,responseListener);
        RequestQueue queue = Volley.newRequestQueue(SickActivity.this);
        queue.add(sickRequest);


    }

}
