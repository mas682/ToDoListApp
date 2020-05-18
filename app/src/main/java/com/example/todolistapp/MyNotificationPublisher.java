package com.example.todolistapp;
import android.app.Notification ;
import android.app.NotificationManager ;
import android.content.BroadcastReceiver ;
import android.content.Context ;
import android.content.Intent ;
public class MyNotificationPublisher extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";
    public static String TAG_ID = "com.example.todolistapp";

    // this is executed when a alarm goes off to kick off a notification
    @Override
    public void onReceive (Context context , Intent intent) {
        System.out.println("Executing");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION) ;
        int id = intent.getIntExtra( NOTIFICATION_ID , 0 );
        assert notificationManager != null;
        notificationManager.notify(TAG_ID, id, notification);
    }
}