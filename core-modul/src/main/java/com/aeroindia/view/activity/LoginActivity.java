package com.aeroindia.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aeroindia.R;
import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.custom.utility.AppUtilityFunction;
import com.aeroindia.db.DatabaseHelpers;
import com.aeroindia.pojos.request.LoginRequest;
import com.aeroindia.pojos.request.OtpRequest;
import com.aeroindia.pojos.response.AllServicesResponse;
import com.aeroindia.pojos.response.B2BListResponse;
import com.aeroindia.pojos.response.FeedbackResponse;
import com.aeroindia.pojos.response.GenericResponse;
import com.aeroindia.pojos.response.LoginResponse;
import com.aeroindia.pojos.response.RegisterResponse;
import com.aeroindia.pojos.response.UpcomingEventsResponse;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.customComponent.CustomAlert;
import com.customComponent.Networking.CustomVolley;
import com.customComponent.Networking.VolleyTaskListener;
import com.customComponent.utility.BaseAppCompatActivity;
import com.customComponent.utility.ProjectPrefrence;

public class LoginActivity extends BaseAppCompatActivity {
private Context context;
private View view;
private EditText verifyCodeET;
AlertDialog dialog;
Button submit;
TextView incorrectotpLabel;
    ImageView cancelIV;
private String email,ctgry;



    private EditText emailET,nameET,cntryET,cmpnyET,contactET,uniqueIdET,exceptOthersemailET;
    private Button signInButton,loginButton,signUpButton;
    private CustomVolley volley;
    private LoginResponse loginResponse;
private LinearLayout exceptothersLay,othersLay;
    private RadioGroup radioUser;
    Intent intent;
    private RegisterResponse registerResponse;
    private GenericResponse genericResponse;
    private UpcomingEventsResponse upcomingEventsResponse;
private AllServicesResponse allServicesResponse;
private B2BListResponse b2BListResponse;
private DatabaseHelpers dbHelper;
private FeedbackResponse feedbackResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_loginn);
        getSupportActionBar().hide();
        context=this;
        dbHelper = DatabaseHelpers.getInstance(context);

//        lay1=(RelativeLayout)findViewById(R.id.lay1);
//        lay2=(RelativeLayout)findViewById(R.id.lay2);

        //skipLogin.setPaintFlags(skipLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);



        LayoutInflater factory = LayoutInflater.from(context);
        view = factory.inflate(R.layout.custom_alert_activate_account, null);
        verifyCodeET = (EditText) view.findViewById(R.id.verifyCodeET);
        incorrectotpLabel = (TextView) view.findViewById(R.id.incorrectotpLabel);
        verifyCodeET.setText("");
        incorrectotpLabel.setVisibility(View.GONE);
        submit = (Button) view.findViewById(R.id.submit);
        cancelIV = (ImageView) view.findViewById(R.id.cancelIV);
        verifyCodeET.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.toString().equals(""))
                {
                    incorrectotpLabel.setVisibility(View.GONE);
                }
            }
        });        dialog = new AlertDialog.Builder(context).create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation_popup;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        dialog.setView(view);
        cancelIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String verifyCode = verifyCodeET.getText().toString();
                if (!verifyCode.equals("")) {
//Verify service implementation
                    OtpRequest otpRequest=new OtpRequest(verifyCode,email);
                    verifyOtp(otpRequest);

                } else {
                    Toast.makeText(context, getResources().getString(R.string.entercodetext), Toast.LENGTH_LONG).show();

                }
            }
        });
        emailET = (EditText)findViewById(R.id.emailET);
        signInButton = (Button)findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailET.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(context,getResources().getString(R.string.email_empty),Toast.LENGTH_LONG).show();
                    return;
                }

                LoginRequest loginRequest=new LoginRequest();
                loginRequest.setUserName(emailET.getText().toString());
                getLogin(loginRequest);

            }
        });

    }



    private void getLogin(final LoginRequest request){
        VolleyTaskListener taskListener=new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                genericResponse= GenericResponse.create(response);

                if(genericResponse.isStatus()){
                    email=request.getUserName();

                    verifyCodeET.setText("");
                    dialog.show();

                    //ProjectPrefrence.saveSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, loginResponse.serialize(), context);
                   // Toast.makeText(context,genericResponse.getErrorMessage(),Toast.LENGTH_LONG).show();

                }else{

                    Toast.makeText(context,genericResponse.getErrorMessage(),Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onError(VolleyError error) {
                if (error != null) {
                    // String s = new String(error.networkResponse.data);
                    //  Log.d("ERROR MSG", s);
                    if (error instanceof TimeoutError) {
                        CustomAlert.alertWithOk(context, getResources().getString(R.string.timeout_issue));
                    } else if (AppUtilityFunction.isServerProblem(error)) {
                        // Toast.makeText(getApplicationContext(),R.string.LOGIN_FAILED,Toast.LENGTH_LONG).show();

                        CustomAlert.alertWithOk(context, getResources().getString(R.string.server_issue));
                    } else if (AppUtilityFunction.isNetworkProblem(error)) {
                        CustomAlert.alertWithOk(context, getResources().getString(R.string.IO_ERROR));
                    }
                }
            }
        };
        volley=new CustomVolley(taskListener,"Please wait..", AppConstant.LOGIN_API,request.serialize(),null,null,context);
        volley.execute();
    }


    private void verifyOtp(OtpRequest request){
        VolleyTaskListener taskListener=new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                loginResponse= LoginResponse.create(response);

                if(loginResponse.isStatus()){

                    //Toast.makeText(context,loginResponse.getErrorMessage(),Toast.LENGTH_LONG).show();

                    dialog.dismiss();
                    //loginResponse.getUser().setCategory(AppConstant.VOLUNTEER);
                    ProjectPrefrence.saveSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, loginResponse.serialize(), context);

                     if(loginResponse.getUser().getCategory().equalsIgnoreCase(AppConstant.VOLUNTEER)) {
                        intent = new Intent(context, DashboardVolunteerActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else
                    {
                        CustomAlert.alertWithOk(context, "You are not allowed to login in this application due to this type - "+loginResponse.getUser().getCategory());
                        ProjectPrefrence.removeSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, context);

                    }


                }else{
                    incorrectotpLabel.setVisibility(View.VISIBLE);
                    incorrectotpLabel.setText(loginResponse.getErrorMessage());
                  //  Toast.makeText(context,loginResponse.getErrorMessage(),Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onError(VolleyError error) {
                if (error != null) {
                    // String s = new String(error.networkResponse.data);
                    //  Log.d("ERROR MSG", s);
                    if (error instanceof TimeoutError) {
                        CustomAlert.alertWithOk(context, getResources().getString(R.string.timeout_issue));
                    } else if (AppUtilityFunction.isServerProblem(error)) {
                        // Toast.makeText(getApplicationContext(),R.string.LOGIN_FAILED,Toast.LENGTH_LONG).show();

                        CustomAlert.alertWithOk(context, getResources().getString(R.string.server_issue));
                    } else if (AppUtilityFunction.isNetworkProblem(error)) {
                        CustomAlert.alertWithOk(context, getResources().getString(R.string.IO_ERROR));
                    }
                }
            }
        };
        volley=new CustomVolley(taskListener,"Please wait..", AppConstant.OTPVERIFY_API,request.serialize(),null,null,context);
        volley.execute();
    }




}


