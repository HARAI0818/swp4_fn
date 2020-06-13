package com.example.main3;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

public class SickFrag1 extends Fragment {
    private AlertDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {


        final View view =  inflater.inflate(R.layout.activity_sick2, container, false); // Fragment로 불러올 xml파일을 view로 가져옵니다.
        final CheckBox Q1_A1 = (CheckBox)view.findViewById(R.id.Q1_A1);
        final CheckBox Q1_A2 = (CheckBox)view.findViewById(R.id.Q1_A2);
        final CheckBox Q1_A3 = (CheckBox)view.findViewById(R.id.Q1_A3);
        final CheckBox Q1_A4 = (CheckBox)view.findViewById(R.id.Q1_A4);
        final CheckBox Q1_A5 = (CheckBox)view.findViewById(R.id.Q1_A5);
        final CheckBox Q1_A6 = (CheckBox)view.findViewById(R.id.Q1_A6);
        final CheckBox Q1_A7 = (CheckBox)view.findViewById(R.id.Q1_A7);
        final CheckBox Q2_A1 = (CheckBox)view.findViewById(R.id.Q2_A1);
        final CheckBox Q2_A2 = (CheckBox)view.findViewById(R.id.Q2_A2);
        final CheckBox Q2_A3 = (CheckBox)view.findViewById(R.id.Q2_A3);
        final CheckBox Q2_A4 = (CheckBox)view.findViewById(R.id.Q2_A4);
        final CheckBox Q2_A5 = (CheckBox)view.findViewById(R.id.Q2_A5);
        final RadioGroup rg = (RadioGroup) view.findViewById(R.id.Q3_rg);
        Button btn_next = (Button)view.findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "";
                String result2 ="";
                String result3 = "";
                if(Q1_A1.isChecked() == true) result += Q1_A1.getText().toString()+" ";
                if(Q1_A2.isChecked() == true) result += Q1_A2.getText().toString()+" ";
                if(Q1_A3.isChecked() == true) result += Q1_A3.getText().toString()+" ";
                if(Q1_A4.isChecked() == true) result += Q1_A4.getText().toString()+" ";
                if(Q1_A5.isChecked() == true) result += Q1_A5.getText().toString()+" ";
                if(Q1_A6.isChecked() == true) result += Q1_A6.getText().toString()+" ";
                if(Q1_A7.isChecked() == true) result += Q1_A7.getText().toString()+" ";

                if(Q2_A1.isChecked() == true) result2 += Q2_A1.getText().toString()+" ";
                if(Q2_A2.isChecked() == true) result2 += Q2_A2.getText().toString()+" ";
                if(Q2_A3.isChecked() == true) result2 += Q2_A3.getText().toString()+" ";
                if(Q2_A4.isChecked() == true) result2 += Q2_A4.getText().toString()+" ";
                if(Q2_A5.isChecked() == true) result2 += Q2_A5.getText().toString()+" ";


                if (rg.getCheckedRadioButtonId()!=-1) {
                    int id = rg.getCheckedRadioButtonId();
                    RadioButton rb = (RadioButton) view.findViewById(id);
                    result3 = rb.getText().toString();
                }

                if(result.equals("")||result2.equals("")||result3.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    dialog = builder.setMessage("비어있는 정보가 있습니다. \n전부 입력해주세요.")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog.show();
                    return;
                }

                ((SickActivity)getActivity()).A1 = result;
                ((SickActivity)getActivity()).A2 = result2;
                ((SickActivity)getActivity()).A3 = result3;


                ((SickActivity)getActivity()).replaceFragment(SickFrag2.newInstance());
            }
        });

        return view;
    }

    public static SickFrag1 newInstance() {
        return new SickFrag1();
    }


}








