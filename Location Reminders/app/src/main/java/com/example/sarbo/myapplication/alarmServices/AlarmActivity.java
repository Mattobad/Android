package com.example.sarbo.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.GregorianCalendar;

public class AlarmActivity extends AppCompatActivity {


    int year,month,day,hour,minute;

    AlarmManager alarmManager;
    GregorianCalendar calendar;
    long alarm_time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSetAlarm = ( Button ) findViewById(R.id.btn_set_alarm);
        Button btnQuitAlarm = ( Button ) findViewById(R.id.btn_quit_alarm);

        DatePicker dpDate = (DatePicker) findViewById(R.id.dp_date);

        /** Getting a reference to TimePicker object available in the MainActivity */
        TimePicker tpTime = (TimePicker) findViewById(R.id.tp_time);
       alarmManager = (AlarmManager) getBaseContext().getSystemService(ALARM_SERVICE);

       year = dpDate.getYear();
         month = dpDate.getMonth();
        day = dpDate.getDayOfMonth();
        hour = tpTime.getCurrentHour();
        minute = tpTime.getCurrentMinute();
        calendar = new GregorianCalendar(year,month,day, hour, minute);
         alarm_time = calendar.getTimeInMillis();

        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /** This intent invokes the activity DemoActivity, which in turn opens the AlertDialog window */
                Intent i = new Intent("in.wptrafficanalyzer.servicealarmdemo.demoactivity");

                /** Creating a Pending Intent */
                PendingIntent operation = PendingIntent.getActivity(getBaseContext(), 0, i, PendingIntent.FLAG_ONE_SHOT);

                /** Getting a reference to the System Service ALARM_SERVICE */

                /** Getting a reference to DatePicker object available in the MainActivity */


                /** Creating a calendar object corresponding to the date and time set by the user */

                /** Converting the date and time in to milliseconds elapsed since epoch */


                /** Setting an alarm, which invokes the operation at alart_time */
                alarmManager.set(AlarmManager.RTC_WAKEUP  , alarm_time , operation);

                /** Alert is set successfully */
                Toast.makeText(getBaseContext(), "Alarm is set successfully", Toast.LENGTH_SHORT).show();

            }
        });

        btnQuitAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
