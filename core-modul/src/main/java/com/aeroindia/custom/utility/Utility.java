package com.penpencil.core.custom.utility;

import android.content.Context;
import android.provider.Settings;

import java.util.ArrayList;

/**
 * Created by PSQ on 11/4/2017.
 */

public class Utility {


    public  static String getDeviceId(Context context){
        String deviceId="3ac0297e7e95f18f";
        if(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)!=null){
            deviceId=Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }

        return deviceId;
    }



}
