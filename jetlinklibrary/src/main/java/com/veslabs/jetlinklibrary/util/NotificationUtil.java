package com.veslabs.jetlinklibrary.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.veslabs.jetlinklibrary.R;
import com.veslabs.jetlinklibrary.messaging.JetLinkChatActivity;
import com.veslabs.jetlinklibrary.network.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Burhan Aras on 11/1/2016.
 */
public class NotificationUtil {
    private static final String TAG = NotificationUtil.class.getSimpleName();
    private static final int NOTIFICATION_ID_FOR_INCOMING_MESSAGE = 100;
    private static List<String> incomingUnreadChatMessageTexts = new ArrayList();

    public static void showNotification(Context context, Message message) {
          /* Invoking the default notification service */
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

        mBuilder.setContentTitle(context.getString(R.string.customer_services));
        mBuilder.setContentText(message.getText());
        mBuilder.setTicker(message.getText());
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setAutoCancel(true);
        mBuilder.setLights(Color.GREEN, 500, 500);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        mBuilder.setVibrate(new long[]{500L, 200L, 200L, 500L});

        incomingUnreadChatMessageTexts.add(message.getText());
        mBuilder.setNumber(incomingUnreadChatMessageTexts.size());



        /* Add Big View Specific Configuration */
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        // Sets a title for the Inbox style big view
        inboxStyle.setBigContentTitle(context.getString(R.string.customer_services));
        inboxStyle.setSummaryText("");

        // Moves events into the big view
        for (int i = 0; i < incomingUnreadChatMessageTexts.size(); i++) {
            inboxStyle.addLine(incomingUnreadChatMessageTexts.get(i));
        }


        mBuilder.setStyle(inboxStyle);


        Intent detailIntent = new Intent(context, JetLinkChatActivity.class);
        detailIntent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID_FOR_INCOMING_MESSAGE, detailIntent, PendingIntent.FLAG_ONE_SHOT);
        mBuilder.setContentIntent(pendingIntent);
        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
//        stackBuilder.addParentStack(ItemListActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(detailIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

   /* notificationID allows you to update the notification later on. */
        Notification notification = mBuilder.build();
        notification.priority = Notification.PRIORITY_MAX;
        mNotificationManager.notify(NOTIFICATION_ID_FOR_INCOMING_MESSAGE, notification);
    }

    public static void calcelNotifications(Context context) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(NOTIFICATION_ID_FOR_INCOMING_MESSAGE);

    }
}
