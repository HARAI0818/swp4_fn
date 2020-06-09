package com.example.main3;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

public class SickFrag2 extends Fragment {
    private AlertDialog dialog;
    private RadioGroup rg;

    public static SickFrag2 newInstance() {
        return new SickFrag2();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {

        final View view =  inflater.inflate(R.layout.activity_sick3, container, false); // Fragment로 불러올 xml파일을 view로 가져옵니다.
        Button btn_next = (Button)view.findViewById(R.id.btn_next2);
        final EditText Q42_A1 = (EditText)view.findViewById(R.id.Q42_A1);
        final EditText Q42_A2 = (EditText)view.findViewById(R.id.Q42_A2);
        final EditText Q43_A1 = (EditText)view.findViewById(R.id.Q43_A1);
        final EditText Q43_A2 = (EditText)view.findViewById(R.id.Q43_A2);
        Button btn_back = (Button)view.findViewById(R.id.btn_back);

        rg = (RadioGroup) view.findViewById(R.id.Q4_rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.Q41_A1){
                    Q42_A1.setEnabled(false);
                    Q42_A1.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    Q42_A2.setEnabled(false);
                    Q42_A2.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    Q43_A1.setEnabled(false);
                    Q43_A1.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    Q43_A2.setEnabled(false);
                    Q43_A2.setBackgroundColor(getResources().getColor(R.color.colorGray));
                }
                else if (checkedId == R.id.Q41_A2){
                    Q42_A1.setEnabled(true);
                    Q42_A1.setBackgroundColor(getResources().getColor(R.color.colorwhite));
                    Q42_A2.setEnabled(true);
                    Q42_A2.setBackgroundColor(getResources().getColor(R.color.colorwhite));
                    Q43_A1.setEnabled(false);
                    Q43_A1.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    Q43_A2.setEnabled(false);
                    Q43_A2.setBackgroundColor(getResources().getColor(R.color.colorGray));
                }
                else if (checkedId == R.id.Q41_A3){
                    Q42_A1.setEnabled(false);
                    Q42_A1.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    Q42_A2.setEnabled(false);
                    Q42_A2.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    Q43_A1.setEnabled(true);
                    Q43_A1.setBackgroundColor(getResources().getColor(R.color.colorwhite));
                    Q43_A2.setEnabled(true);
                    Q43_A2.setBackgroundColor(getResources().getColor(R.color.colorwhite));
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SickActivity)getActivity()).replaceFragment(SickFrag1.newInstance());
            }
        });



        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "";
                String result2 = "";
                String result3 = "";
                int id;

                if (rg.getCheckedRadioButtonId() != -1) {
                    id = rg.getCheckedRadioButtonId();
                    RadioButton rb = (RadioButton) view.findViewById(id);
                    result = rb.getText().toString();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    dialog = builder.setMessage("비어있는 정보가 있습니다. \n전부 입력해주세요.")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog.show();
                    return ;
                }


                if(id == R.id.Q41_A2){
                    result2 = Q42_A1.getText().toString();
                    result3 = Q42_A2.getText().toString();
                    if(result2.equals("")||result3.equals("")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        dialog = builder.setMessage("비어있는 정보가 있습니다. \n전부 입력해주세요.")
                                .setNegativeButton("OK", null)
                                .create();
                        dialog.show();
                        return;
                    }
                }
                else if ( id == R.id.Q41_A3){
                    result2 = Q43_A1.getText().toString();
                    result3 = Q43_A2.getText().toString();
                    if(result2.equals("")||result3.equals("")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        dialog = builder.setMessage("비어있는 정보가 있습니다. \n전부 입력해주세요.")
                                .setNegativeButton("OK", null)
                                .create();
                        dialog.show();
                        return;
                    }
                }

                ((SickActivity) getActivity()).A4 = result;
                ((SickActivity) getActivity()).A41 = result2;
                ((SickActivity) getActivity()).A42 = result3;

                ((SickActivity)getActivity()).replaceFragment(SickFrag3.newInstance());    // 새로 불러올 Fragment의 Instance를 Main으로 전달
            }
        });

        return view;
    }


}