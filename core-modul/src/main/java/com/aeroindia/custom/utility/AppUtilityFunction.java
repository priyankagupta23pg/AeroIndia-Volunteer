package com.aeroindia.custom.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;


import org.apache.http.client.HttpClient;
import org.apache.http.params.HttpParams;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by psqit on 11/16/2016.
 */
public class AppUtilityFunction {
    public static final String MY_PREFERENCES = "first_preferences";
    public static final String MY_PREFERENCES_SPLASH = "first_preferences_splash";
    public static int  INTEGER_WIDTH;
    public static int STRING_WIDTH;
    public static int LIST_WIDTH;
    public static int TITLE_WIDTH;
    public static int LIST_HEIGHT = 45;
    public static final int TEXT_SIZE = 15;
    public static final int TEXT_SIZE1 = 14;
    public static final int LEFT_MARGIN = 5;
    public static final String defaultValForList = "Select";
    static HttpParams httpParameters;
    static HttpClient httpClient;

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
    public static boolean isServerProblem(Object error) {
        return (error instanceof ServerError || error instanceof AuthFailureError);
    }
    public static boolean isParsingProblem(Object error) {
        return (error instanceof ParseError );
    }

    public static boolean isNetworkProblem (Object error){
        return (error instanceof NetworkError || error instanceof NoConnectionError);
    }
    public static boolean isFirst(Context context){
        final SharedPreferences reader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        final boolean first = reader.getBoolean("is_first", true);
        if(first){
            final SharedPreferences.Editor editor = reader.edit();
            editor.putBoolean("is_first", false);
            editor.commit();
        }
        return first;
    }
    public static boolean isFirstSplash(Context context){
        final SharedPreferences reader = context.getSharedPreferences(MY_PREFERENCES_SPLASH, Context.MODE_PRIVATE);
        final boolean first = reader.getBoolean("is_firstSplash", true);
        if(first){
            final SharedPreferences.Editor editor = reader.edit();
            editor.putBoolean("is_firstSplash", false);
            editor.commit();
        }
        return first;
    }
    public static boolean deleteDirectory( File dir ) {

        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDirectory(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();

    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            //Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            // Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
//    public String getTime1(String puranaDate)
//    {
//        try {
//            currentDate = new Date();
//            oldDate = dateFormat.parse(puranaDate);
//
//            cDate = currentDate.getTime();
//            diff = currentDate.getTime() - oldDate.getTime();
//            long seconds = diff / 1000;
//            long minutes = seconds / 60;
//            long hours = minutes / 60;
//            long days = hours / 24;
//            long mnths = days / 30;
//            long years=mnths/12;
//            if (oldDate.before(currentDate))
//            {
//
//                Log.e("oldDate", "is previous date");
//                Log.e("Difference: ", " seconds: " + seconds + " minutes: " + minutes
//                        + " hours: " + hours + " days: " + days);
//                //  String data = mnths + "Months ago" + days + "Days ago" + hours + "Hours ago" + minutes + "Minuts ago" + seconds + "seconds ago";
//                String dataMonths = mnths + " months";
//                String dataDayz = days + " Days ago";
//                String dataHours = hours + " Hrs ago";
//                String dataMinuts = minutes + " Mins ago";
//                String dataSeconds = seconds + " Secs ago";
//                String dataYears = years + " Yrs ago";
//                if(years > 0)
//                {
//                    return dataYears;
//                }
//                else if (mnths > 0) {
//                    return dataMonths;
//                }
//                else if (days > 0 ) {
//                    return dataDayz;
//
//
//                } else if (hours > 0 ) {
//                    return dataHours;
//
//
//
//                } else if (minutes > 0) {
//                    return dataMinuts;
//
//
//                } else if (seconds > 10) {
//                    return dataSeconds;
//
//                } else {
//                    return "just now";
//
//                }
//
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return "just now";
//    }

    public static boolean isOld(String puranaDate)
    {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(AppConstant.DATE_FORMAT);

            String currentDate=getDate(System.currentTimeMillis(),AppConstant.DATE_FORMAT);
            Date currDate=formatter.parse(currentDate);
            Date oldDate=formatter.parse(puranaDate);





            if (oldDate.before(currDate))
            {

              return true;

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
