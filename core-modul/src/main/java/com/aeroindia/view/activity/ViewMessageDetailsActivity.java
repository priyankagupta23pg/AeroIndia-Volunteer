package com.aeroindia.view.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aeroindia.R;
import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.custom.utility.AppUtilityFunction;
import com.aeroindia.pojos.request.MessageItem;

public class ViewMessageDetailsActivity extends AppCompatActivity {
private Context context;
RelativeLayout backLayout;
private TextView headerTV,fromLabel,fromTv,dateTv,purposeTv,msgTv;
private int fromm;
MessageItem messageItem;
String messageString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        getSupportActionBar().hide();
        context=this;

        backLayout=(RelativeLayout)findViewById(R.id.backLayout);
        headerTV=(TextView)findViewById(R.id.headerTV);
        fromLabel=(TextView)findViewById(R.id.fromLabel);
        purposeTv=(TextView)findViewById(R.id.purposeTv);
        fromTv=(TextView)findViewById(R.id.fromTv);
        dateTv=(TextView)findViewById(R.id.dateTv);
        msgTv=(TextView)findViewById(R.id.msgTv);
        headerTV.setText(getResources().getString(R.string.msgDetail));
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if(getIntent()!=null)
        {
            fromm=getIntent().getIntExtra("fromm",0);
            messageString=getIntent().getStringExtra("MessageItem");
            messageItem = MessageItem.create(messageString);


        }
        if(fromm==0){
            fromLabel.setText("To : ");
        }
        else
        {
            fromLabel.setText("From : ");

        }
        if(messageItem!=null)
        {
            fromTv.setText(messageItem.getName());
            purposeTv.setText(messageItem.getPurpose());
            dateTv.setText(AppUtilityFunction.getDate(messageItem.getDateTime(), AppConstant.MSG_DATE_FORMAT));
            if(fromm==0){
            String[] separated = messageItem.getMessage().split(".<br>");
           if(separated!=null) {
               if (separated[0] != null) {
                   msgTv.setText(separated[0]);
               }
           }
           }
           else
            {
                msgTv.setText( Html.fromHtml(messageItem.getMessage()));
            }
        }
    }
}
