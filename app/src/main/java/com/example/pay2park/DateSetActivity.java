package com.example.pay2park;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateSetActivity extends AppCompatActivity {
    //initialize variable
    TextView tvtimer1, tvtimer2;
    int t1Hour, t1Minute, t2Hour, t2Minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_set);

        tvtimer1=findViewById(R.id.tv_timer1);
        tvtimer2=findViewById(R.id.tv_timer2);

        tvtimer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog= new TimePickerDialog(DateSetActivity.this,android.R.style.Theme_Holo_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        //inititalize hour and minute

                        t1Hour= i; //hourofday
                        t1Minute= i1; //minute
                        String time= t1Hour+ ":" +t1Minute;
                        //initialize 24 hours format
                        SimpleDateFormat f24ours= new SimpleDateFormat( "HH:mm");
                        try{
                            Date date= f24ours.parse(time);
                            SimpleDateFormat f12Hours = new SimpleDateFormat(
                                    "hh:mm aa"
                            );
                            //set selected time on text view
                            tvtimer1.setText(f12Hours.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, false

                ) ;
                //set transparent background
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //display previous selected time
                timePickerDialog.updateTime(t1Hour,t1Minute);
                //show dialog
                timePickerDialog.show();
            }
        });

        tvtimer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initialize

TimePickerDialog timePickerDialog= new TimePickerDialog(DateSetActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        //initialize hour and minute
        t2Hour= i;
        t2Minute=i1;
        //store hour and minute in string
        String time= t2Hour+ ":" +t2Minute;
        //initialize 24 hours format
        SimpleDateFormat f24ours= new SimpleDateFormat( "HH:mm");
        try{
            Date date= f24ours.parse(time);
            SimpleDateFormat f12Hours = new SimpleDateFormat(
                    "hh:mm aa"
            );
            //set selected time on text view
            tvtimer2.setText(f12Hours.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}, 12, 0, false

) ;
          //set transparent background
          timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
          //display previious selected time
                timePickerDialog.updateTime(t2Hour,t2Minute);
                //show dialog
                timePickerDialog.show();
            }
        });
    }
}