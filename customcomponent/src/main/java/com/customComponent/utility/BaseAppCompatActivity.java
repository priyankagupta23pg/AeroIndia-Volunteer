package com.customComponent.utility;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.customComponent.R;

import java.util.ArrayList;
import java.util.List;


/**
 * BaseActionBarActivity. Created on 15-12-2015.
 */
public class BaseAppCompatActivity extends AppCompatActivity {
   // private ProgressDialog _progressDialog;
   TransDialog pd;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 125;

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        /*_progressDialog = new ProgressDialog(this);
        _progressDialog.setCancelable(false);
        _progressDialog.setCanceledOnTouchOutside(false);
        _progressDialog.setMessage(getString(R.string.sharedWaitMsg));*/
        pd = new TransDialog(this, R.drawable.loading);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
    }





    public void showHideProgressDialog(boolean isShow) {
        showHideProgressDialog(isShow, getString(R.string.please_wait));
    }

    public void showHideProgressDialog(boolean isShow, String message) {
        if (isShow) {
          //  _progressDialog.setMessage(message);
          //  _progressDialog.show();
            pd.show();
        } else {
           // _progressDialog.dismiss();
            pd.dismiss();
        }
    }
    @TargetApi(23)
    public void showPermissions() {
        List<String> permissionsNeeded = new ArrayList<String>();
        List<String> permissionsAll= new ArrayList<String>();
        permissionsAll.add(getResources().getString(R.string.accessdeviceloc));

        final List<String> permissionsList = new ArrayList<String>();

        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add(getResources().getString(R.string.accessloc));
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add(getResources().getString(R.string.writedb));

        Log.d("Perm Size",permissionsList.size()+"");
        Log.d("Perm Needed Size",permissionsNeeded.size()+"");
        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {

                // Need Rationale
                String message = getResources().getString(R.string.appneed) +" "+ permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(getResources().getString(R.string.alert));
                builder.setMessage(message)
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
                            @TargetApi(23)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                return;
            }
            ActivityCompat.requestPermissions(BaseAppCompatActivity.this,permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }
        else
        {
            Log.d("After removing","permission");
            ActivityCompat.requestPermissions(BaseAppCompatActivity.this,permissionsAll.toArray(new String[permissionsAll.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }
    }
    @TargetApi(23)
    public boolean addPermission(List<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
       /* ProjectPrefrence.saveSharedPrefrenceData("PrefFlagDB", "flag","1", mContext);*/

        return true;
    }
}
