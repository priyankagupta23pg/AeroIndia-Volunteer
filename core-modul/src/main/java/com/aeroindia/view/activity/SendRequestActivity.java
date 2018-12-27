package com.aeroindia.view.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.aeroindia.R;
import com.aeroindia.custom.utility.AppConstant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SendRequestActivity extends AppCompatActivity {
RelativeLayout backLayout;
private TextView headerTV,selectedDateTv,companyTv,addTv;
TextView dateTimePicker,companyVal,addVal;
String fromFlag="0";
private Context context;
    Calendar date;
    private String add,company,visitorNm,visitorAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request);
        getSupportActionBar().hide();
        context=this;
        backLayout = (RelativeLayout) findViewById(R.id.backLayout);
        headerTV = (TextView) findViewById(R.id.headerTV);
        companyVal = (TextView) findViewById(R.id.companyVal);
        companyTv = (TextView) findViewById(R.id.companyTv);
        addTv = (TextView) findViewById(R.id.addTv);
        addVal = (TextView) findViewById(R.id.addVal);
        selectedDateTv = (TextView) findViewById(R.id.selectedDateTv);
        dateTimePicker=(TextView)findViewById(R.id.dateTimePicker);
        headerTV.setText(getResources().getString(R.string.sendreq));
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (getIntent() != null)
        {
            fromFlag=getIntent().getStringExtra("from");
            if(fromFlag.equalsIgnoreCase("1"))
            {
                companyTv.setText("Company :");
                addTv.setText("Address :");
                        company=getIntent().getStringExtra("company");
                if(company!=null)
                companyVal.setText(company);
                add=getIntent().getStringExtra("add");
                if(add!=null)
                {
                    addVal.setText(add);
                }

            }
            else
            {
                companyTv.setText("Visitor :");
                addTv.setText("Address :");
                visitorNm=getIntent().getStringExtra("visitorNm");
                visitorAdd=getIntent().getStringExtra("visitorAdd");
                if(visitorNm!=null)
                {  companyVal.setText(visitorNm);

                }
                if(visitorAdd!=null)
                {
                    addVal.setText(visitorAdd);
                }
            }
        }
        dateTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker();
            }
        });
    }
    public void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        Log.v("TIMING", "The choosen one " + date.getTime());
                        final SimpleDateFormat dateFormatter = new SimpleDateFormat(AppConstant.DATE_FORMAT, Locale.ENGLISH);
                        String datee = dateFormatter.format(date.getTime());
                        selectedDateTv.setText(datee);
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }
}
