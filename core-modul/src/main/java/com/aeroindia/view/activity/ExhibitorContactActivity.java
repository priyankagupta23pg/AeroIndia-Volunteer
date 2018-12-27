package com.aeroindia.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aeroindia.R;
import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.custom.utility.AppUtilityFunction;
import com.aeroindia.db.CommonDatabaseAero;
import com.aeroindia.pojos.request.ContactEmailRequestModel;
import com.aeroindia.pojos.request.ExhibitorsItem;
import com.aeroindia.pojos.response.ContactEmailResponseModel;
import com.aeroindia.pojos.response.LoginResponse;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.customComponent.CustomAlert;
import com.customComponent.Networking.CustomVolley;
import com.customComponent.Networking.VolleyTaskListener;
import com.customComponent.utility.ProjectPrefrence;

/**
 * Created by SUNAINA on 29-11-2018.
 */

public class ExhibitorContactActivity extends AppCompatActivity {
    private ExhibitorContactActivity activity;
    private Context context;
    private LoginResponse loginResponse;
    private RelativeLayout backLayout;
    private TextView headerTV;
    private EditText emailET, nameET, cmpnyET, purposeET, messageET;
    private ExhibitorsItem exhibitorsItem;
    private ContactEmailResponseModel contactEmailResponseModel;
    private CustomVolley volley;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibitor_contact);
        activity = this;
        context = this;
        setupScreen();
    }

    private void setupScreen() {
        getSupportActionBar().hide();
        loginResponse = LoginResponse.create(ProjectPrefrence.getSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, context));
        backLayout = (RelativeLayout) findViewById(R.id.backLayout);
        headerTV = (TextView) findViewById(R.id.headerTV);
        headerTV.setText(getResources().getString(R.string.contact));
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        exhibitorsItem = (ExhibitorsItem) getIntent().getSerializableExtra("ContactItem");
         emailET = (EditText) findViewById(R.id.emailET);
       // nameET = (EditText) findViewById(R.id.nameET);
        cmpnyET = (EditText) findViewById(R.id.cmpnyET);
        purposeET = (EditText) findViewById(R.id.purposeET);
        messageET = (EditText) findViewById(R.id.messageET);
        if (loginResponse.getUser() != null) {
            if (loginResponse.getUser().getUserName() != null) {
                emailET.setText(loginResponse.getUser().getUserName());
                emailET.setEnabled(false);
        /*    emailET.setFocusable(false);
            emailET.setClickable(false);*/
                emailET.setBackgroundColor(context.getResources().getColor(R.color.lighter_gray));
            }
        }
        if (loginResponse.getCompany() != null) {
            if (loginResponse.getCompany().getName() != null) {
                cmpnyET.setText(loginResponse.getCompany().getName());
                cmpnyET.setEnabled(false);
      /*      cmpnyET.setFocusable(false);
            cmpnyET.setClickable(false);*/
                cmpnyET.setBackgroundColor(context.getResources().getColor(R.color.lighter_gray));

            }
        }
        Button sendButton = (Button) findViewById(R.id.sendButton);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* if (nameET.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(context, context.getResources().getString(R.string.empty_name), Toast.LENGTH_LONG).show();
                    return;
                }*/
                if (emailET.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(context, context.getResources().getString(R.string.email_empty), Toast.LENGTH_LONG).show();
                    return;
                }
                if (cmpnyET.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(context, context.getResources().getString(R.string.empty_company), Toast.LENGTH_LONG).show();
                    return;
                }
                if (purposeET.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(context, context.getResources().getString(R.string.empty_purpose), Toast.LENGTH_LONG).show();
                    return;
                }
                if (messageET.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(context, context.getResources().getString(R.string.empty_message), Toast.LENGTH_LONG).show();
                    return;
                }

                if (loginResponse.getCompany() != null) {


                    ContactEmailRequestModel requestModel = new ContactEmailRequestModel();
                    //requestModel.setSenderName(nameET.getText().toString());
                    requestModel.setSenderName(loginResponse.getCompany().getCountry());
                    requestModel.setSenderEmail(emailET.getText().toString());
                    requestModel.setSenderCompanyName(cmpnyET.getText().toString());
                    requestModel.setCommType("Send_Email");
                    requestModel.setPurpose(purposeET.getText().toString());

                    requestModel.setSenderCountry(loginResponse.getCompany().getCountry());
                    if (exhibitorsItem != null) {
                        requestModel.setReceiverType(exhibitorsItem.getCompanyId());
                        requestModel.setReceiverId(exhibitorsItem.getUniqueId());

                    }

                    requestModel.setSenderType(AppConstant.EMAIL);
                    requestModel.setSenderId(loginResponse.getUser().getUserId());

                    requestModel.setMessage(messageET.getText().toString());

                    Log.d("Contact Email Request:", requestModel.serialize());
                    if (AppUtilityFunction.isNetworkAvailable(context)) {
                        sendContactRequest(requestModel);
                    }else {
                        long value= CommonDatabaseAero.saveContactRequest(context,requestModel);
                        if(value!=0){
                            CustomAlert.alertOkWithFinish(context, "Your contact data has been saved. It will be synced to server when you come online.");
                        }else {
                            CustomAlert.alertWithOk(context,"Failed");
                        }
                    }
                } else {
                    Toast.makeText(context, "There is no company id exist for this user for contact request", Toast.LENGTH_LONG).show();

                }


            }
        });
    }

    private void sendContactRequest(ContactEmailRequestModel request) {
        VolleyTaskListener taskListener = new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                contactEmailResponseModel = ContactEmailResponseModel.create(response);
                if (contactEmailResponseModel.isStatus()) {
                    /*ProjectPrefrence.saveSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, loginResponse.serialize(), activity);

                    Intent intent=new Intent(activity,DashboardActivity.class);
                    startActivity(intent);*/

                    CustomAlert.alertOkWithFinish(context, "Contact request send successfully.");
                  //  finish();
                } else {

                    Toast.makeText(context, contactEmailResponseModel.getErrorMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onError(VolleyError error) {
                if (error != null) {
                    // String s = new String(error.networkResponse.data);
                    //  Log.d("ERROR MSG", s);
                    if (error instanceof TimeoutError) {
                        CustomAlert.alertWithOk(context, context.getResources().getString(R.string.timeout_issue));
                    } else if (AppUtilityFunction.isServerProblem(error)) {
                        // Toast.makeText(getApplicationContext(),R.string.LOGIN_FAILED,Toast.LENGTH_LONG).show();

                        CustomAlert.alertWithOk(context, context.getResources().getString(R.string.server_issue));
                    } else if (AppUtilityFunction.isNetworkProblem(error)) {
                        CustomAlert.alertWithOk(context, context.getResources().getString(R.string.IO_ERROR));
                    }
                }
            }
        };
        volley = new CustomVolley(taskListener, "Please wait..", AppConstant.CONTACT_EMAIL, request.serialize(), null, null, context);
        volley.execute();
    }

}
