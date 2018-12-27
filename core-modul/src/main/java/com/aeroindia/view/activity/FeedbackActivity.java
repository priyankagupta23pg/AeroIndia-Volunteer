package com.aeroindia.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aeroindia.R;
import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.custom.utility.AppUtilityFunction;
import com.aeroindia.db.CommonDatabaseAero;
import com.aeroindia.pojos.request.SubmitFeedbackRequest;
import com.aeroindia.pojos.response.GenericResponse;
import com.aeroindia.pojos.response.LoginResponse;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.customComponent.CustomAlert;
import com.customComponent.Networking.CustomVolley;
import com.customComponent.Networking.VolleyTaskListener;
import com.customComponent.utility.BaseAppCompatActivity;
import com.customComponent.utility.ProjectPrefrence;

public class FeedbackActivity extends BaseAppCompatActivity {
    private Context context;
    private LoginResponse loginResponse;
    private RelativeLayout backLayout;
    private TextView headerTV;
    private Button submitBTN;
    private RatingBar ratingBar;
    private EditText commentET;
    private GenericResponse genericResponse;
    private long userid;
    private String uname;
    private int companyId = 0, seminarId = 0, airshowId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setupScreen();


    }

    private void setupScreen() {
        getSupportActionBar().hide();
        context = this;
        if (getIntent() != null) {
            companyId = getIntent().getIntExtra("companyId", 0);
            seminarId = getIntent().getIntExtra("seminarId", 0);
            airshowId = getIntent().getIntExtra("airshowId", 0);
        }
        // loginResponse = LoginResponse.create(ProjectPrefrence.getSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, context));
        backLayout = (RelativeLayout) findViewById(R.id.backLayout);
        submitBTN = (Button) findViewById(R.id.submitBTN);
        commentET = (EditText) findViewById(R.id.commentET);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        headerTV = (TextView) findViewById(R.id.headerTV);
        //  selectedExhibitorTv = (TextView) findViewById(R.id.selectedExhibitorTv);
        headerTV.setText("Feedback");

        ratingBar.setRating((float) 5.0);
        // String rating=String.valueOf(ratingBar.getRating());
        loginResponse = LoginResponse.create(ProjectPrefrence.getSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, context));
        if (loginResponse != null) {
            if (loginResponse.getUser() != null) {
                userid = loginResponse.getUser().getUserId();
                uname = loginResponse.getUser().getUserName();

            }
        }
        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginResponse != null) {
                    int rating = (int) ratingBar.getRating();
                    String commentTxt = commentET.getText().toString();
                    if (commentTxt.equalsIgnoreCase("")) {
                        CustomAlert.alertWithOk(context, getResources().getString(R.string.commnetTxtempty));

                    } else {
                        if (companyId != 0) {
                            if (AppUtilityFunction.isNetworkAvailable(context)) {
                                SubmitFeedbackRequest submitFeedbackRequest = new SubmitFeedbackRequest(userid, companyId, 2, rating, uname, commentTxt);
                                submitFeedback(submitFeedbackRequest);
                            } else {
                                SubmitFeedbackRequest submitFeedbackRequest = new SubmitFeedbackRequest();
                                submitFeedbackRequest.setComment(commentTxt);
                                submitFeedbackRequest.setServiceType(2);
                                submitFeedbackRequest.setStatus(0); // 0 status for save request in db
                                submitFeedbackRequest.setTotalStar(rating);
                                submitFeedbackRequest.setWhatItemCode(companyId);
                                submitFeedbackRequest.setWhoGiven(userid);
                                submitFeedbackRequest.setWhoGivenName(uname);
                                Log.d("Feedback request db:",submitFeedbackRequest.serialize());
                                long value=CommonDatabaseAero.saveFeedbackRequest(context,submitFeedbackRequest);
                                if(value!=0){
                                    CustomAlert.alertOkWithFinish(context, "Your feedback data has been saved. It will be synced to server when you come online.");
                                }else {
                                    CustomAlert.alertWithOk(context,"Failed");
                                }
                            }
                        } else if (seminarId != 0) {
                            if (AppUtilityFunction.isNetworkAvailable(context)) {
                                SubmitFeedbackRequest submitFeedbackRequest = new SubmitFeedbackRequest(userid, seminarId, 1, rating, uname, commentTxt);
                                submitFeedback(submitFeedbackRequest);

                            } else {
                                SubmitFeedbackRequest submitFeedbackRequest = new SubmitFeedbackRequest();
                                submitFeedbackRequest.setComment(commentTxt);
                                submitFeedbackRequest.setServiceType(1);
                                submitFeedbackRequest.setStatus(0); // 0 status for save request in db
                                submitFeedbackRequest.setTotalStar(rating);
                                submitFeedbackRequest.setWhatItemCode(seminarId);
                                submitFeedbackRequest.setWhoGiven(userid);
                                submitFeedbackRequest.setWhoGivenName(uname);
                                Log.d("Feedback request db:",submitFeedbackRequest.serialize());
                                long value=CommonDatabaseAero.saveFeedbackRequest(context,submitFeedbackRequest);
                                if(value!=0){
                                    CustomAlert.alertOkWithFinish(context, "Your feedback data has been saved. It will be synced to server when you come online.");
                                }else {
                                    CustomAlert.alertWithOk(context,"Failed");
                                }
                            }
                        } else if (airshowId != 0) {
                            if (AppUtilityFunction.isNetworkAvailable(context)) {
                                SubmitFeedbackRequest submitFeedbackRequest = new SubmitFeedbackRequest(userid, airshowId, 3, rating, uname, commentTxt);
                                submitFeedback(submitFeedbackRequest);
                            } else {
                                SubmitFeedbackRequest submitFeedbackRequest = new SubmitFeedbackRequest();
                                submitFeedbackRequest.setComment(commentTxt);
                                submitFeedbackRequest.setServiceType(3);
                                submitFeedbackRequest.setStatus(0); // 0 status for save request in db
                                submitFeedbackRequest.setTotalStar(rating);
                                submitFeedbackRequest.setWhatItemCode(airshowId);
                                submitFeedbackRequest.setWhoGiven(userid);
                                submitFeedbackRequest.setWhoGivenName(uname);
                                Log.d("Feedback request db:",submitFeedbackRequest.serialize());
                                long value=CommonDatabaseAero.saveFeedbackRequest(context,submitFeedbackRequest);
                                if(value!=0){
                                    CustomAlert.alertOkWithFinish(context, "Your feedback data has been saved. It will be synced to server when you come online.");
                                }else {
                                    CustomAlert.alertWithOk(context,"Failed");
                                }
                            }
                        }
                    }

                } else {
                    Intent intent = new Intent(context, NewDashboardActivity.class);
                    CustomAlert.alertWithOk(context, getResources().getString(R.string.commnetTxtempty), intent);

                }
            }
        });

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void submitFeedback(SubmitFeedbackRequest request) {
        showHideProgressDialog(true);
        VolleyTaskListener taskListener = new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                genericResponse = GenericResponse.create(response);

                showHideProgressDialog(false);

                if (genericResponse.isStatus()) {
                    CustomAlert.alertOkWithFinish(context, "Your feedback submitted successfully.");


                } else {
                    if (genericResponse.getErrorMessage() != null) {
                        Toast.makeText(context, genericResponse.getErrorMessage(), Toast.LENGTH_LONG).show();
                    }
                    return;
                }
            }

            @Override
            public void onError(VolleyError error) {
                showHideProgressDialog(false);

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

        Log.d("Feedback Request:", request.serialize());
        CustomVolley volley = new CustomVolley(taskListener, null, AppConstant.SUBMITFEEDBACK_API, request.serialize(), null, null, context);
        volley.execute();
    }

}
