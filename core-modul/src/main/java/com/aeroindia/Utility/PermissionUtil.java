package com.aeroindia.Utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


/**
 * Created by Manjunath on 27-04-2017.
 */

public class PermissionUtil {

    public final static int REQ_PERMISSION = 1001;
    public final static int REQ_CAMERA_PERMISSION = 1002;
    public final static int REQ_Storage_PERMISSION = 1000;
    public final static int REQ_LOCATION_PERMISSION = 1004;

    // Check for permission to access Location
    public static boolean checkLocationPermission(Context context) {
        //  Log.d(MainActivity.TAG, "checkLocationPermission()");
        // Ask for permission if it wasn't granted yet
        if (context != null) {
            return (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        } else {
            return false;
        }
    }

  /*  public static void askLocationPermision(BaseFragment baseFragment) {
        if (baseFragment != null) {
            try {
                baseFragment.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_LOCATION_PERMISSION);
            } catch (IllegalStateException e) {
                Crashlytics.logException(e);
            }
        }
    }*/

    public static boolean checkExternalStoragePermission(Context context) {
        //   Log.d(MainActivity.TAG, "checkLocationPermission()");
        // Ask for permission if it wasn't granted yet
        if (context != null) {
            return (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        } else {
            return false;
        }
    }


    public static boolean checkCameraPermission(Context context) {
        // Ask for permission if it wasn't granted yet
        if (context != null) {
            return (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
        } else {
            return false;
        }
    }

    public static boolean checkStoragePermission(Context context) {
        // Ask for permission if it wasn't granted yet
        if (context != null) {
            return (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        } else {
            return false;
        }
    }


    public static void askAllPermissionCamera(Activity activity) {
        if (activity != null) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_PERMISSION);


        }
    }

}
