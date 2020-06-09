package com.example.main3;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

public class SickFrag4 extends Fragment {
    private AlertDialog dialog;

    public static SickFrag4 newInstance() {
        return new SickFrag4();
    }
    private RadioGroup rg1;
    private RadioGroup rg2;
    private RadioGroup rg3;
    private RadioGroup rg4;
    private RadioGroup rg5;
    private RadioGroup rg6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {

        final View view =  inflater.inflate(R.layout.activity_sick5, container, false); // Fragment로 불러올 xml파일을 view로 가져옵니다.
        Button btn_next = (Button)view.findViewById(R.id.btn_end);
        rg1 = (RadioGroup) view.findViewById(R.id.Q61_rg1);
        rg1.clearCheck();
        rg1.setOnCheckedChangeListener(listener1);
        rg2 = (RadioGroup) view.findViewById(R.id.Q61_rg2);
        rg2.clearCheck();
        rg2.setOnCheckedChangeListener(listener2);
        rg3 = (RadioGroup) view.findViewById(R.id.Q62_rg1);
        rg3.clearCheck();
        rg3.setOnCheckedChangeListener(listener3);
        rg4 = (RadioGroup) view.findViewById(R.id.Q62_rg2);
        rg4.clearCheck();
        rg4.setOnCheckedChangeListener(listener4);
        rg5 = (RadioGroup) view.findViewById(R.id.Q63_rg1);
        rg5.clearCheck();
        rg5.setOnCheckedChangeListener(listener5);
        rg6 = (RadioGroup) view.findViewById(R.id.Q63_rg2);
        rg6.clearCheck();
        rg6.setOnCheckedChangeListener(listener6);
        Button btn_back = (Button)view.findViewById(R.id.btn_back3);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SickActivity)getActivity()).replaceFragment(SickFrag3.newInstance());
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "";
                String result2 = "";
                String result3 = "";
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


                id1 = rg3.getCheckedRadioButtonId();
                id2 = rg4.getCheckedRadioButtonId();
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
                    result2 = rb.getText().toString();
                }
                else if( id2 == -1){
                    RadioButton rb = (RadioButton) view.findViewById(id1);
                    result2 = rb.getText().toString();
                }

                id1 = rg5.getCheckedRadioButtonId();
                id2 = rg6.getCheckedRadioButtonId();
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
                    result3 = rb.getText().toString();
                }
                else if( id2 == -1){
                    RadioButton rb = (RadioButton) view.findViewById(id1);
                    result3 = rb.getText().toString();
                }


                ((SickActivity)getActivity()).A9 = result;
                ((SickActivity)getActivity()).A10 = result2;
                ((SickActivity)getActivity()).A11 = result3;

                ((SickActivity)getActivity()).getAll();

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

    private RadioGroup.OnCheckedChangeListener listener3 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                rg4.setOnCheckedChangeListener(null);
                rg4.clearCheck();
                rg4.setOnCheckedChangeListener(listener4);
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener listener4 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                rg3.setOnCheckedChangeListener(null);
                rg3.clearCheck();
                rg3.setOnCheckedChangeListener(listener3);
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener listener5 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                rg6.setOnCheckedChangeListener(null);
                rg6.clearCheck();
                rg6.setOnCheckedChangeListener(listener6);
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener listener6 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                rg5.setOnCheckedChangeListener(null);
                rg5.clearCheck();
                rg5.setOnCheckedChangeListener(listener5);
            }
        }
    };
}