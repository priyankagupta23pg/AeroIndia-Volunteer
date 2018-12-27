package com.aeroindia.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aeroindia.R;
import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.custom.utility.AppUtilityFunction;
import com.aeroindia.pojos.request.CreateProfileRequestModel;
import com.aeroindia.pojos.response.CreateProfileResponseModel;
import com.aeroindia.pojos.response.LoginResponse;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.customComponent.CustomAlert;
import com.customComponent.Networking.CustomVolley;
import com.customComponent.Networking.VolleyTaskListener;
import com.customComponent.utility.BaseAppCompatActivity;
import com.customComponent.utility.ProjectPrefrence;

public class ViewProfileActivity extends BaseAppCompatActivity {
private LoginResponse loginResponse;
private RelativeLayout backLayout;
private TextView headerTV,web,descVal;
private Context context;
private CreateProfileResponseModel getProfileResponseModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        getSupportActionBar().hide();
        context=this;
        loginResponse = LoginResponse.create(ProjectPrefrence.getSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, context));
        backLayout = (RelativeLayout) findViewById(R.id.backLayout);
        headerTV = (TextView) findViewById(R.id.headerTV);
        headerTV.setText(getResources().getString(R.string.company_profile));

        web = (TextView) findViewById(R.id.web);
        descVal = (TextView) findViewById(R.id.descVal);
        web.setPaintFlags(web.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
       // web.setText("www.psquickit.com");
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!url.startsWith("http://") && !url.startsWith("https://"))
//                    url = "http://" + url;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.psquickit.com/"));
                startActivity(browserIntent);
            }
        });
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        createProfileRequest();
    }

    private void createProfileRequest() {

        CreateProfileRequestModel getProfileRequest = new CreateProfileRequestModel();
        if (loginResponse.getCompany() != null) {
            if (loginResponse.getCompany().getCompanyId() != 0) {
                getProfileRequest.setExhibitorId(loginResponse.getCompany().getCompanyId());
            }
        }
        getProfile(getProfileRequest);
    }

    private void getProfile(CreateProfileRequestModel request) {
        showHideProgressDialog(true);
        VolleyTaskListener taskListener = new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                showHideProgressDialog(false);

                getProfileResponseModel = CreateProfileResponseModel.create(response);
                if (getProfileResponseModel.isStatus()) {
                    if (getProfileResponseModel != null && getProfileResponseModel.getProfile() != null
                            && getProfileResponseModel.getProfile().getAboutMe() != null) {
                        setProfileData(getProfileResponseModel.getProfile().getAboutMe());
                    }


                    return;
                } else {

                    Toast.makeText(context, getProfileResponseModel.getErrorMessage(), Toast.LENGTH_LONG).show();
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
        CustomVolley  volley = new CustomVolley(taskListener, null, AppConstant.GET_PROFILE, request.serialize(), null, null, context);
        volley.execute();
    }
    private void setProfileData(String aboutCompany) {
        // Static data
        // weblinkET.setText("www.psquickit.com");
        descVal.setText(aboutCompany);
    }
}
