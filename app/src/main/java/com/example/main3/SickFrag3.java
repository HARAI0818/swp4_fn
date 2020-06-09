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

public class SickFrag3 extends Fragment {
    private AlertDialog dialog;

    public static SickFrag3 newInstance() {
        return new SickFrag3();
    }
    private RadioGroup rg1;
    private RadioGroup rg2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {

        final View view =  inflater.inflate(R.layout.activity_sick4, container, false); // Fragment로 불러올 xml파일을 view로 가져옵니다.
        Button btn_next = (Button)view.findViewById(R.id.btn_next3);
        rg1 = (RadioGroup)view.findViewById(R.id.Q5_rg1);
        rg1.clearCheck();
        rg1.setOnCheckedChangeListener(listener1);
        rg2 = (RadioGroup)view.findViewById(R.id.Q5_rg2);
        rg2.setOnCheckedChangeListener(listener2);
        rg1.clearCheck();
        final EditText Q52_A = (EditText)view.findViewById(R.id.Q52_A);
        Button btn_back = (Button)view.findViewById(R.id.btn_back2);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SickActivity)getActivity()).replaceFragment(SickFrag2.newInstance());
            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "";
                String result2 = "";
                int id1 , id2;

                id1 = rg1.getCheckedRadioButtonId();
                id2 = rg2.getCheckedRadioButtonId();
                if(id1 == -1 &&  id2 == -1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    dialog = builder.setMessage("비어있는 정보가 있습니다. \n전부 입력해주세요.")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog.show();
                    return;
                }
                else if ( id1 == -1 ){
                    RadioButton rb = (RadioButton) view.findViewById(id2);
                    result = rb.getText().toString();
                }
                else if( id2 == -1){
                    RadioButton rb = (RadioButton) view.findViewById(id1);
                    result = rb.getText().toString();
                }


                result2 = Q52_A.getText().toString();

                if(result2.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    dialog = builder.setMessage("비어있는 정보가 있습니다. \n전부 입력해주세요.")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog.show();
                    return;
                }


                ((SickActivity)getActivity()).A7 = result;
                ((SickActivity)getActivity()).A8 = result2;


                ((SickActivity)getActivity()).replaceFragment(SickFrag4.newInstance());    // 새로 불러올 Fragment의 Instance를 Main으로 전달


            }
        });

        return view;
    }

    private RadioGroup.OnCheckedChangeListener listener1 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                rg2.setOnCheckedChangeListener(null);
                rg2.clearCheck();
                rg2.setOnCheckedChangeListener(listener2);
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener listener2 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                rg1.setOnCheckedChangeListener(null);
                rg1.clearCheck();
                rg1.setOnCheckedChangeListener(listener1);
            }
        }
    };



}

