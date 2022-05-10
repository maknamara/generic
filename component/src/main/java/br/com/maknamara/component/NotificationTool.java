package br.com.maknamara.component;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class NotificationTool {
    private static final String CHANNEL_ID = NotificationTool.class.getName();

    public static Notification createNotification(@NonNull Context context,@NonNull String title,@NonNull String message) {
        Intent intent = new Intent(context, context.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, context.getClass().getName());

        notificationBuilder.setContentTitle(title);//required
        notificationBuilder.setSmallIcon(android.R.drawable.ic_dialog_alert);//required
        notificationBuilder.setContentText(message);//required
        notificationBuilder.setDefaults(Notification.DEFAULT_ALL);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setTicker(message);
        notificationBuilder.setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);

        return notificationBuilder.build();
    }
}
