package com.cookandroid.aa;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AlarmActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference("aa");

    private AlarmManager alarmManager;
    private TimePicker tp_set_alarm;

    private PendingIntent pendingIntent;

    private ToggleButton tgbtn_blind_height;
    private CheckBox ck_mon, ck_tues, ck_wed, ck_thur, ck_fri, ck_sat, ck_sun;
    Button btn_check_alarm, btn_cancel_alarm;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        this.alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        this.tp_set_alarm = findViewById(R.id.tp_set_alarm);
        tgbtn_blind_height = findViewById(R.id.tgbtn_blind_height);

        ck_mon = findViewById(R.id.ck_mon);
        ck_tues = findViewById(R.id.ck_tues);
        ck_wed = findViewById(R.id.ck_wed);
        ck_thur = findViewById(R.id.ck_thur);
        ck_fri = findViewById(R.id.ck_fri);
        ck_sat = findViewById(R.id.ck_sat);
        ck_sun = findViewById(R.id.ck_sun);

        findViewById(R.id.btn_check_alarm).setOnClickListener(mClickListener);
        findViewById(R.id.btn_cancel_alarm).setOnClickListener(mClickListener);

        //btn_check_alarm.setOnClickListener(mClickListener);
        //btn_cancel_alarm.setOnClickListener(mClickListener);

//        btn_check_alarm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(AlarmActivity.this, ControlActivity.class);
////                startActivity(intent);
//                finish();
//            }
//        });
//
//        btn_cancel_alarm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(AlarmActivity.this, ControlActivity.class);
////                startActivity(intent);
//                finish();
//            }
//        });
    }

    /* ?????? ?????? */
    private void start(){
        // ?????? ??????
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, this.tp_set_alarm.getHour());
        calendar.set(Calendar.MINUTE, this.tp_set_alarm.getMinute());
        calendar.set(Calendar.SECOND, 0);

        // ?????????????????? ????????????
        if(calendar.before(Calendar.getInstance())){
            // ???????????? ??????
            calendar.add(Calendar.DATE, 1);
        }

        // Receiver ??????
        Intent intent = new Intent(this, AlarmReceiver.class);
        // state ?????? on ?????? ????????????, off ?????? ??????
        intent.putExtra("state", "on");


        this.pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        databaseReference.child("K40TY9PhwkPGtMDRNhoor4Kg4em2").child("blind4").child("Alarm").child("Alarm_state").setValue("on");


        // ?????? ??????
        this.alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        // Toast ???????????? (?????? ?????? ??????)
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Toast.makeText(this, "Alarm : " + format.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
    }

    /* ?????? ?????? */
    private void stop(){
        if (this.pendingIntent == null){
            return;
        }

        // ?????? ??????
        this.alarmManager.cancel(this.pendingIntent);

        // ?????? ?????? Broadcast
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("state", "off");

        sendBroadcast(intent);

        this.pendingIntent = null;
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_check_alarm:
                    // ?????? ??????
                    start();

                    break;
                case R.id.btn_cancel_alarm:
                    // ?????? ??????
                    stop();

                    break;
            }
        }
    };
}
