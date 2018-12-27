package com.aeroindia.Notif;


import android.content.Context;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Belal on 5/27/2016.
 */


//Class extending FirebaseInstanceIdService
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    private Context context;

    private String sessionId, refreshedToken;

    @Override
    public void onTokenRefresh() {
        context = this;
        //Getting registration token
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Displaying token on logcat 
        Log.d(TAG, "Refreshed token: " + refreshedToken);
//        ProjectPrefrence.saveSharedPrefrenceData(AppConstants.PROJECT_FCM, AppConstants.TRANSPORTER_FCM_TOKEN, refreshedToken, this);
//
//        String loginPref = ProjectPrefrence.getSharedPrefrenceData(AppConstants.PROJECT_PREF_DOMAIN, AppConstants.LOGIN_PREF_CONTENT, context);
//        if (loginPref != null) {
//            registerationResponse = RegisterationResponse.create(loginPref);
//            if (registerationResponse.headers != null) {
//                sessionId = registerationResponse.headers.sessionId;
//                Log.d("Session", sessionId);
//            }
//
//        }
//        if (sessionId != null) {
//            Log.d("Update", "FCM Service");
//            UpdateFCMRequest updateFCMRequest = new UpdateFCMRequest(refreshedToken);
//            updateFCM(updateFCMRequest);
//        }
    }

//    private void updateFCM(UpdateFCMRequest request) {
//        VolleyTaskListener taskListener = new VolleyTaskListener() {
//            @Override
//            public void postExecute(JSONObject response) {
//                Log.d("FCM response", response.toString());
//                genericResponse = GenericResponse.create(response.toString());
//                if (genericResponse != null) {
//                    if (genericResponse.getResponseStatus() == ResponseStatus.OK.getStatus()) {
//                        Log.d("FCM", "Updated DONE");
//                    } else {
//                      //  CustomAlert.alertWithOk(context, genericResponse.getMessage());
//
//                    }
//                }
//            }
//
//
//            @Override
//            public void onError(VolleyError error) {
//                if (error.getMessage() != null) {
//                    if (error.getMessage().contains("java.net.UnknownHostException")) {
//                        //CustomAlert.alertWithOk(context, getResources().getString(R.string.internet_connection_message));
//
//                    } else {
//                        //CustomAlert.alertWithOk(context, getResources().getString(R.string.server_issue));
//
//                    }
//                } else {
//                   // CustomAlert.alertWithOk(context, getResources().getString(R.string.server_issue));
//
//                }
//
//
//            }
//        };
//        Log.d("FCM update request", request.serialize());
//        CustomVolley volley = new CustomVolley(taskListener, AppConstants.UPDATE_FCM_URL, request.serialize(), "sessionID", sessionId, context);
//        volley.execute();
//    }
}