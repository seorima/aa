package com.cookandroid.aa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ControlActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;

    private ImageButton ibtn_blind_up, ibtn_blind_stop, ibtn_blind_down;
    private ToggleButton btn_light_control, btn_alarm, btn_mode;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        ibtn_blind_up = findViewById(R.id.ibtn_blind_up);
        ibtn_blind_stop = findViewById(R.id.ibtn_blind_stop);
        ibtn_blind_down = findViewById(R.id.ibtn_blind_down);
        btn_light_control = findViewById(R.id.btn_light_control);
        btn_alarm = findViewById(R.id.btn_alarm);
//        btn_mode = findViewById(R.id.btn_mode);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("aa");


        ibtn_blind_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child("K40TY9PhwkPGtMDRNhoor4Kg4em2").child("blind4").child("Control").child("blind_control").setValue("up");
            }
        });

        ibtn_blind_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child("K40TY9PhwkPGtMDRNhoor4Kg4em2").child("blind4").child("Control").child("blind_control").setValue("stop");
            }
        });

        ibtn_blind_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child("K40TY9PhwkPGtMDRNhoor4Kg4em2").child("blind4").child("Control").child("blind_control").setValue("down");
            }
        });

        btn_light_control.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    // 첫번째 인자는 ToggleButton, 두번째 인자는 on/off에 대한 boolean값
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        // toggle 버튼이 on된 경우
                        if (isChecked){
                            //설정 화면 이동
                            Intent intent = new Intent(ControlActivity.this, LightControlActivity.class);
                            startActivity(intent);
                        }
                        // toggle 버튼이 off된 경우
                        else{

                        }
                    }
                }
        );

        btn_alarm.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    // 첫번째 인자는 ToggleButton, 두번째 인자는 on/off에 대한 boolean값
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        // toggle 버튼이 on된 경우
                        if (isChecked){
                            //설정 화면 이동
                            Intent intent = new Intent(ControlActivity.this, AlarmActivity.class);
                            startActivity(intent);
                        }
                        // toggle 버튼이 off된 경우
                        else{

                        }
                    }
                }
        );

//        btn_light_control.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ControlActivity.this, LightControlActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        btn_alarm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ControlActivity.this, AlarmActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        btn_mode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ControlActivity.this, ModeActivity.class);
//                startActivity(intent);
//            }
//        });

    }
}
