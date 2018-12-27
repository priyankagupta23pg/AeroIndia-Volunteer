package com.aeroindia.volunteer;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.custom.utility.AppUtilityFunction;
import com.aeroindia.pojos.response.LoginResponse;
import com.aeroindia.view.activity.DashboardVolunteerActivity;
import com.aeroindia.view.activity.LoginActivity;
import com.aeroindia.view.activity.NewDashboardActivity;
import com.customComponent.utility.BaseAppCompatActivity;
import com.customComponent.utility.ProjectPrefrence;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class SplashActivity extends BaseAppCompatActivity {
    private Context context;
    private int SPLASH_TIME_OUT = 3000;
    private LoginResponse loginResponse;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 125;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        context=this;
       // getSupportActionBar().hide();
        getSupportActionBar().hide();
        showSplash();
    }
    public void performAction(final Context mContext)
    {
        if(AppUtilityFunction.isFirstSplash(mContext)) {
            //clean all folders
            File dir = new File(Environment.getExternalStorageDirectory(), File.separator + context.getPackageName() + File.separator);
            try {
                boolean b = AppUtilityFunction.deleteDirectory(dir);
                Log.d("FILE", "deleted" + b);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else
        {
            //do nothing
            Log.d("LATEST FILE","NO deleteion needed");
        }

        // getVersion();

    }

    @TargetApi(23)
    private void showSplash() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= 23){
            showPermissions();
            // Do something for lollipop and above versions
        } else
        {
            continuePostAppVersionCheck();
            // do something for phones running an SDK before lollipop
        }

    }

    public void continuePostAppVersionCheck()
    {
        performAction(context);

        loginResponse=LoginResponse.create(ProjectPrefrence.getSharedPrefrenceData(AppConstant.PROJECT_PREF,AppConstant.LOGIN_DETAILS,context));
        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                if (loginResponse == null) {
                    Intent mainIntent = new Intent(context, LoginActivity.class);
                    startActivity(mainIntent);
                    finish();

                } else {
//                    if(loginResponse.getUser().getCategory().equalsIgnoreCase(AppConstant.EXHIBITOR))
//                    {
//                        Intent i = new Intent(context, DashboardExhibitorActivity.class);
//                        startActivity(i);
//                        finish();
//                    }
//
//                    else if(loginResponse.getUser().getCategory().equalsIgnoreCase(AppConstant.OTHER))
//                    {
//                        Intent i = new Intent(context, DashboardActivity.class);
//                        startActivity(i);
//                        finish();
//                    }
//                   if(loginResponse.getUser().getCategory().equalsIgnoreCase(AppConstant.PROVIDER))
//                    {
//                        Intent i = new Intent(context, DashboardProviderActivity.class);
//                        startActivity(i);
//                        finish();
//                    }
                    if(loginResponse.getUser().getCategory().equalsIgnoreCase(AppConstant.VOLUNTEER))
                    {
                        Intent i = new Intent(context, DashboardVolunteerActivity.class);
                        startActivity(i);
                        finish();
                    }

                }

            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
               // perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
//                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
               // perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);

                if (( perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ) )  {
                    // All Permissions Granted
                    Log.d("Granted Splash result", "PERMISSIONS");

                    continuePostAppVersionCheck();
                } else {
                    //Permission denied
                    com.customComponent.CustomAlert.alertOkWithFinish(context, getResources().getString(R.string.without));
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
