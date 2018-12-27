package com.aeroindia.view.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aeroindia.R;
import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.custom.utility.AppUtilityFunction;
import com.aeroindia.db.CommonDatabaseAero;
import com.aeroindia.db.DatabaseHelpers;
import com.aeroindia.db.DatabaseOpration;
import com.aeroindia.pojos.request.EmptyRequest;
import com.aeroindia.pojos.request.ExhibitorsItem;
import com.aeroindia.pojos.request.GetFeedbackRequest;
import com.aeroindia.pojos.request.OtpRequest;
import com.aeroindia.pojos.request.RegisterUserRequest;
import com.aeroindia.pojos.request.LoginRequest;
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
import com.customComponent.Networking.CustomVolleyGet;
import com.customComponent.Networking.VolleyTaskListener;
import com.customComponent.utility.BaseAppCompatActivity;
import com.customComponent.utility.ProjectPrefrence;

public class NewDashboardActivity extends BaseAppCompatActivity {
private Context context;
private View view;
private EditText verifyCodeET;
AlertDialog dialog;
Button submit;
TextView incorrectotpLabel,skipLogin;
    ImageView cancelIV;
private String email,ctgry;
private RelativeLayout lay3,lay4,lay5;
    private Button titleButton;
    private Dialog dialog2,dialog1,dialog3;
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

        setContentView(R.layout.activity_new_dashboard);
        getSupportActionBar().hide();
        context=this;
        dbHelper = DatabaseHelpers.getInstance(context);

//        lay1=(RelativeLayout)findViewById(R.id.lay1);
//        lay2=(RelativeLayout)findViewById(R.id.lay2);
        lay3=(RelativeLayout)findViewById(R.id.lay3);
        lay4=(RelativeLayout)findViewById(R.id.lay4);
        lay5=(RelativeLayout)findViewById(R.id.lay5);

        skipLogin=(TextView) findViewById(R.id.skipLogin);
        //skipLogin.setPaintFlags(skipLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        lay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateRegisteredUSerPopup();
            }
        });
        lay4.setVisibility(View.GONE);
        lay5.setVisibility(View.GONE);
        lay4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateNewUSerPopup();
            }
        });
        skipLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DashboardActivity.class);
            startActivity(intent);
            finish();
            }
        });
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

    }

    private void initiateNewUSerPopup() {
        dialog2 = new Dialog(context);
        // dialog = new Dialog(this, R.style.NewDialog);
        dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //  dialog.setCancelable(true);
        dialog2.setCanceledOnTouchOutside(true);
        //dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
      /* WindowManager.LayoutParams lp = dialog2.getWindow().getAttributes();
       lp.dimAmount = 0.6f;
       dialog2.getWindow().setAttributes(lp);*/
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog2.getWindow().getAttributes().windowAnimations = R.style.animation_popup;


        //    dialog2.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog2.setContentView(R.layout.newuser_login_layout);
        ctgry=context.getResources().getString(R.string.exhibitor);

        emailET = (EditText) dialog2.findViewById(R.id.emailET);
        exceptOthersemailET = (EditText) dialog2.findViewById(R.id.exceptOthersemailET);
        exceptothersLay = (LinearLayout) dialog2.findViewById(R.id.exceptothersLay);
        othersLay = (LinearLayout) dialog2.findViewById(R.id.othersLay);
        nameET = (EditText) dialog2.findViewById(R.id.nameET);
        cmpnyET = (EditText) dialog2.findViewById(R.id.cmpnyET);
        contactET = (EditText) dialog2.findViewById(R.id.contactET);
        cntryET = (EditText) dialog2.findViewById(R.id.cntryET);
        uniqueIdET = (EditText) dialog2.findViewById(R.id.uniqueIdET);
        radioUser=(RadioGroup)dialog2.findViewById(R.id.radioGroup);
        signUpButton = (Button) dialog2.findViewById(R.id.signUpButton);
        ImageView cancelIV=(ImageView)dialog2.findViewById(R.id.cancelIV) ;
        cancelIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        radioUser.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                     ctgry=checkedRadioButton.getText().toString();
                    if(ctgry.equalsIgnoreCase("Visitor"))
                    {
                        othersLay.setVisibility(View.VISIBLE);
                        exceptothersLay.setVisibility(View.GONE);
                    }
                    else
                    {
                        othersLay.setVisibility(View.GONE);
                        exceptothersLay.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ctgry.equalsIgnoreCase("Visitor"))
                {
                    String name=nameET.getText().toString();

                    String cntry=cntryET.getText().toString();
                    String mob=contactET.getText().toString();
                    email=emailET.getText().toString();
                    if(name.equalsIgnoreCase("")){
                        Toast.makeText(context,getResources().getString(R.string.name_empty),Toast.LENGTH_LONG).show();
                        return;
                    }

                    if(cntry.equalsIgnoreCase("")) {
                        Toast.makeText(context, getResources().getString(R.string.cntry_empty), Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(email.equalsIgnoreCase("")){
                        Toast.makeText(context,getResources().getString(R.string.email_empty),Toast.LENGTH_LONG).show();
                        return;
                    }

                    RegisterUserRequest registerUserRequest=new RegisterUserRequest(email,name,cntry,mob,"XXXXX",AppConstant.OTHER,"XXXXX");
        doRegister(registerUserRequest);
                }
                else
                {
                    email=exceptOthersemailET.getText().toString();
                    if(email.equalsIgnoreCase("")){
                        Toast.makeText(context,getResources().getString(R.string.email_empty),Toast.LENGTH_LONG).show();
                        return;
                    }

                    RegisterUserRequest registerUserRequest=new RegisterUserRequest(email,"XXXXX","XXXXX","XXXXX","XXXXX",ctgry,"XXXXX");
                doRegister(registerUserRequest);
                }
//RegisterUserRequest registerUserRequest=new RegisterUserRequest(email,name,cntry,mob,cmpny,ctgry,uniqId);
//                doRegister(registerUserRequest);

            }
        });


        dialog2.show();
    }

    private void initiateRegisteredUSerPopup() {

        dialog3 = new Dialog(context);
        // dialog = new Dialog(this, R.style.NewDialog);
        dialog3.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //  dialog.setCancelable(true);
        dialog3.setCanceledOnTouchOutside(true);
        //dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lp = dialog3.getWindow().getAttributes();
        lp.dimAmount=0.6f;
        dialog3.getWindow().setAttributes(lp);
        dialog3.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog3.setContentView(R.layout.registereduser_login_layout);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        float scaleFactor = metrics.density;
        float widthDp = widthPixels / scaleFactor;
        float heightDp = heightPixels / scaleFactor;
        float smallestWidth = Math.min(widthDp, heightDp);
        Window window = dialog3.getWindow();
        window.setLayout(widthPixels, heightPixels);
        dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //   dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //dialog.getWindow().setBackgroundDrawableResource(R.color.black);
        //dialog.setCancelable(false);

        emailET = (EditText) dialog3.findViewById(R.id.emailET);
        signInButton = (Button) dialog3.findViewById(R.id.signInButton);
        ImageView cancelIV=(ImageView)dialog3.findViewById(R.id.cancelIV) ;
        cancelIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog3.dismiss();
            }
        });
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

        dialog3.show();
    }
    private void getLogin(final LoginRequest request){
        VolleyTaskListener taskListener=new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                genericResponse= GenericResponse.create(response);

                if(genericResponse.isStatus()){
                    email=request.getUserName();
                    dialog3.dismiss();
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
        volley=new CustomVolley(taskListener,"Please wait..",AppConstant.LOGIN_API,request.serialize(),null,null,context);
        volley.execute();
    }
    private void doRegister(RegisterUserRequest request){
        VolleyTaskListener taskListener=new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                registerResponse= RegisterResponse.create(response);


                if(registerResponse.isStatus()){
                    dialog.show();
                    dialog2.dismiss();
                    //Toast.makeText(context,registerResponse.getErrorMessage(),Toast.LENGTH_LONG).show();

                }else{

                    Toast.makeText(context,registerResponse.getErrorMessage(),Toast.LENGTH_LONG).show();
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
        volley=new CustomVolley(taskListener,"Please wait..",AppConstant.REGISTER_API,request.serialize(),null,null,context);
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
//                    if(loginResponse.getUser().getCategory().equalsIgnoreCase(AppConstant.EXHIBITOR))
//                    {
//                        getAllEvents();
//
//                    }
//
//                    else if(loginResponse.getUser().getCategory().equalsIgnoreCase(AppConstant.OTHER)) {
//
//                        getAllEvents();
//
//
//
//                    }
//                     if(loginResponse.getUser().getCategory().equalsIgnoreCase(AppConstant.PROVIDER)) {
//                        intent = new Intent(context, DashboardProviderActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
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
        volley=new CustomVolley(taskListener,"Please wait..",AppConstant.OTPVERIFY_API,request.serialize(),null,null,context);
        volley.execute();
    }

    private void getAllEvents() {
        showHideProgressDialog(true);
        VolleyTaskListener taskListener=new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                upcomingEventsResponse = UpcomingEventsResponse.create(response);
                if (upcomingEventsResponse != null) {


                    if (upcomingEventsResponse.isStatus()) {
                        if (upcomingEventsResponse.getEventList() != null) {
                            if(upcomingEventsResponse.getEventList().size()>0) {
                                for (int i = 0; i < upcomingEventsResponse.getEventList().size(); i++) {
                                   // data.add(new EventModel(upcomingEventsResponse.getEventList().get(i).getId(),upcomingEventsResponse.getEventList().get(i).getDateTime(), upcomingEventsResponse.getEventList().get(i).getEventName(), upcomingEventsResponse.getEventList().get(i).getEventType(), upcomingEventsResponse.getEventList().get(i).getVenue(),upcomingEventsResponse.getEventList().get(i).getLatitude(),upcomingEventsResponse.getEventList().get(i).getLongitude()));
                                    //CommonDatabaseAero.saveUpcomingEvents(context,new EventModel(upcomingEventsResponse.getEventList().get(i).getId(),upcomingEventsResponse.getEventList().get(i).getDateTime(), upcomingEventsResponse.getEventList().get(i).getEventName(), upcomingEventsResponse.getEventList().get(i).getEventType(), upcomingEventsResponse.getEventList().get(i).getVenue(),upcomingEventsResponse.getEventList().get(i).getLatitude(),upcomingEventsResponse.getEventList().get(i).getLongitude()));
                                    CommonDatabaseAero.saveUpcomingEvents(context,upcomingEventsResponse.getEventList().get(i));
                                }
                            }
                            else
                            {
                                CustomAlert.alertOkWithFinish(context, "There is no upcoming event available for now");

                            }
                        } else {
                            CustomAlert.alertWithOk(context, "There is no upcoming event available for now");

                        }

                    }
                    showHideProgressDialog(false);
                    getAllServicesList();


                }
            }


            @Override
            public void onError(VolleyError error) {

                showHideProgressDialog(false);
                if (error.getMessage() != null) {
                    if (error.getMessage().contains("java.net.UnknownHostException")) {
                        CustomAlert.alertWithOk(context, getResources().getString(R.string.internet_connection_message));

                    } else {
                        CustomAlert.alertWithOk(context, getResources().getString(R.string.server_issue));

                    }
                } else {
                    CustomAlert.alertWithOk(context, getResources().getString(R.string.server_issue));

                }



            }
        };
        CustomVolleyGet volley=new CustomVolleyGet(taskListener, null, AppConstant.UPCOMING_EVENTS_API,context);
        volley.execute();
    }

    private void getAllServicesList() {
        showHideProgressDialog(true);
        VolleyTaskListener taskListener=new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                allServicesResponse = AllServicesResponse.create(response);
                if (allServicesResponse != null) {
                    if (allServicesResponse.isStatus()) {
                        if (allServicesResponse.services != null) {
                            if(allServicesResponse.services.size()>0) {
                                for (int i = 0; i < allServicesResponse.services.size(); i++) {
                                    CommonDatabaseAero.saveService(context,allServicesResponse.services.get(i));
                                }

                            }
                            else
                            {
                                CustomAlert.alertOkWithFinish(context, "There is no services available for now");

                            }
                        } else {
                            CustomAlert.alertWithOk(context, "There is no services available for now");

                        }
                    }
                    showHideProgressDialog(false);
                    if(loginResponse.getUser().getCategory().equalsIgnoreCase(AppConstant.EXHIBITOR)) {
                      if(loginResponse.getCompany()!=null) {
                          if (loginResponse.getCompany().getCompanyId() != 0) {
                              GetFeedbackRequest getFeedbackRequest = new GetFeedbackRequest(loginResponse.getCompany().getCompanyId());
                              getFeedbacksList(getFeedbackRequest);
                          }
                      }
                      else
                      {
                          intent = new Intent(context, DashboardExhibitorActivity.class);
                          startActivity(intent);
                          finish();
                      }

                    }
                    else if(loginResponse.getUser().getCategory().equalsIgnoreCase(AppConstant.OTHER)) {
                        getExhibitorData(new EmptyRequest());

                    }
                }
            }


            @Override
            public void onError(VolleyError error) {

                showHideProgressDialog(false);
                if (error.getMessage() != null) {
                    if (error.getMessage().contains("java.net.UnknownHostException")) {
                        CustomAlert.alertWithOk(context, getResources().getString(R.string.internet_connection_message));

                    } else {
                        CustomAlert.alertWithOk(context, getResources().getString(R.string.server_issue));

                    }
                } else {
                    CustomAlert.alertWithOk(context, getResources().getString(R.string.server_issue));

                }



            }
        };
        CustomVolleyGet volley=new CustomVolleyGet(taskListener, null,AppConstant.ALLSERVICES_API,context);
        volley.execute();
    }

    private void getExhibitorData(EmptyRequest emptyRequest) {
     showHideProgressDialog(true);
        VolleyTaskListener taskListener = new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                b2BListResponse = B2BListResponse.create(response);
                if (b2BListResponse != null) {

                        if (b2BListResponse.isStatus()) {
                            if (b2BListResponse.companyHallList != null) {
                                ExhibitorsItem exhibitorsItem;
                                // nocake.setText("There are "+getTrendingListResponse.result.size()+" cakes under this category");
                                for (int i = 0; i < b2BListResponse.companyHallList.size(); i++) {
                                    if (b2BListResponse.companyHallList.get(i).hall != null) {
                                        exhibitorsItem = new ExhibitorsItem(b2BListResponse.companyHallList.get(i).hall.getName(), b2BListResponse.companyHallList.get(i).hall.getStall(), b2BListResponse.companyHallList.get(i).company.getCountry(), b2BListResponse.companyHallList.get(i).company.getName());
                                    }
                                    else
                                    {
                                        exhibitorsItem = new ExhibitorsItem("N.A.", "N.A.", b2BListResponse.companyHallList.get(i).company.getCountry(), b2BListResponse.companyHallList.get(i).company.getName());

                                    }
                                    exhibitorsItem.setUniqueId(b2BListResponse.companyHallList.get(i).company.getUniqueId());
                                    exhibitorsItem.setCompanyId(b2BListResponse.companyHallList.get(i).company.getCompanyId());
                                    exhibitorsItem.setEmail(b2BListResponse.companyHallList.get(i).company.getContactEmailId());
                                    exhibitorsItem.setPhone(b2BListResponse.companyHallList.get(i).company.getMobileNumber());

                                    ExhibitorsItem existExhibitorItem = DatabaseOpration.getExhibitor(b2BListResponse.companyHallList.get(i).company.getUniqueId(), dbHelper);
                                    if (existExhibitorItem == null) {
                                        DatabaseOpration.saveExhibitor(exhibitorsItem, dbHelper);
                                    } else {
                                        DatabaseOpration.updateExhibitor(exhibitorsItem, existExhibitorItem.getUniqueId(), dbHelper);

                                    }



                                }
                            } else {
                               // CustomAlert.alertWithOk(getActivity(), "There is no exhibitor data");

                            }
                        }
                       showHideProgressDialog(false);
                    intent = new Intent(context, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }


            @Override
            public void onError(VolleyError error) {

                 showHideProgressDialog(false);
                    if (error.getMessage() != null) {
                        if (error.getMessage().contains("java.net.UnknownHostException")) {
                            CustomAlert.alertWithOk(context, getResources().getString(R.string.internet_connection_message));

                        } else {
                            CustomAlert.alertWithOk(context, getResources().getString(R.string.server_issue));

                        }
                    } else {
                        CustomAlert.alertWithOk(context, getResources().getString(R.string.server_issue));

                    }



            }
        };
        CustomVolley volley = new CustomVolley(taskListener, AppConstant.EXHIBITOR_API, emptyRequest.serialize(), null, null,context);
        volley.execute();
    }
    private void getFeedbacksList(GetFeedbackRequest getFeedbackRequest) {
        showHideProgressDialog(true);
        VolleyTaskListener taskListener=new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                feedbackResponse = FeedbackResponse.create(response);
                if (feedbackResponse != null) {


                    if (feedbackResponse.isStatus()) {
                        if (feedbackResponse.feedbackList != null) {
                            if(feedbackResponse.feedbackList.size()>0) {

                                for (int i = 0; i < feedbackResponse.feedbackList.size(); i++) {

                                    //data.add(new WallModel(feedbackResponse.feedbackList.get(i).getComment(), feedbackResponse.feedbackList.get(i).getWhoGivenName(), feedbackResponse.feedbackList.get(i).getTotalStar(), AppUtilityFunction.getDate(feedbackResponse.feedbackList.get(i).getCreatedOn(), AppConstant.MSG_DATE_FORMAT)));
                                    CommonDatabaseAero.saveMyWall(context,feedbackResponse.feedbackList.get(i));

                                }

                            }
                            else
                            {
                                CustomAlert.alertOkWithFinish(context, "There is no feedback available for now");

                            }
                        } else {
                            CustomAlert.alertWithOk(context, "There is no feedback available for now");

                        }
                    }
                    showHideProgressDialog(false);
                    intent = new Intent(context, DashboardExhibitorActivity.class);
                    startActivity(intent);
                    finish();
                }
            }


            @Override
            public void onError(VolleyError error) {

                showHideProgressDialog(false);
                if (error.getMessage() != null) {
                    if (error.getMessage().contains("java.net.UnknownHostException")) {
                        CustomAlert.alertWithOk(context, getResources().getString(R.string.internet_connection_message));

                    } else {
                        CustomAlert.alertWithOk(context, getResources().getString(R.string.server_issue));

                    }
                } else {
                    CustomAlert.alertWithOk(context, getResources().getString(R.string.server_issue));

                }



            }
        };
        CustomVolley volley=new CustomVolley(taskListener, AppConstant.GETFEEDBACK_API,getFeedbackRequest.serialize(),null,null,context);
        volley.execute();
    }

}


