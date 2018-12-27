package com.aeroindia.Notif;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.aeroindia.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


/**
 * Created by Belal on 5/27/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
       // super.onMessageReceived(remoteMessage);
        //Displaying data in log
        //It is optional
        Log.d("FIREBASE", "YESS");
        if(remoteMessage.getNotification()!=null)
        {
            Log.d("NOTI",remoteMessage.getNotification().toString());
            String title=remoteMessage.getNotification().getTitle();
            String body=remoteMessage.getNotification().getBody();
            //String screenTag=remoteMessage.getNotification().getTag();
            sendNotification(title,body);
        }
        else if(remoteMessage.getData()!=null)
        {
            String title=remoteMessage.getData().get("title");
            String body=remoteMessage.getData().get("body");
            String screenTag=remoteMessage.getData().get("screen");
            sendNotification(title,body);
        }
      //  super.onMessageReceived(remoteMessage);
       // Log.d(TAG, "From: " + remoteMessage.getFrom() + remoteMessage.getData().toString());
        /*Log.d(TAG, "Notification Message Body: " + remoteMessage.getData());
        Log.d(TAG, "Notification Message Body1: " + remoteMessage.getData().get("body"));*/
        //Calling method to generate notification

        //  sendNotification(remoteMessage.getData());
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts

    private void sendNotification(String title, String body) {

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);


            Intent intent=null;
//            if (body != null) {
//                if (tag.equals("4")) {
//                    intent = new Intent(this, RunningBidsActivity.class);
//                }
//
//                else if (tag.equals("1")) {
//                    intent = new Intent(this, ViewBidsActivity.class);
//                    intent.putExtra("FROM","0");
//
//                }else if (tag.equals("2")) {
//                    intent = new Intent(this, ViewBidsActivity.class);
//                    intent.putExtra("FROM","1");
//
//                }else if (tag.equals("3")) {
//                    intent = new Intent(this, ViewBidsActivity.class);
//                    intent.putExtra("FROM","2");
//
//                }
//
//            } else {
//                intent = new Intent(this, RunningBidsActivity.class);
//
//            }
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
//                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            notificationBuilder.setSmallIcon(R.drawable.iconn1)

                    .setContentTitle(title)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(body))
                    .setContentText(body)
                    .setAutoCancel(true)

                    .setSound(defaultSoundUri)
//                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);
            String channelId = this.getString(R.string.default_notification_channel_id);
            NotificationChannel channel = new NotificationChannel(channelId,   title, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(body);
            notificationManager.createNotificationChannel(channel);
            notificationBuilder.setChannelId(channelId);
        }
        else
        {
           // if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                notificationBuilder.setSmallIcon(R.drawable.iconn1)

                        .setContentTitle(title)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(body))
                        .setContentText(body)
                        .setAutoCancel(true)

                        .setSound(defaultSoundUri)
//                    .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);


        }
        notificationManager.notify(0, notificationBuilder.build());
    }


}