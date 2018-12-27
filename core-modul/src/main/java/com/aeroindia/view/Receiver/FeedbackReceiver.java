package com.aeroindia.view.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by SUNAINA on 19-12-2018.
 */

public class FeedbackReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceintent = new Intent(context, UpdateFeedbackService.class);
        context.startService(serviceintent);

    }
}
