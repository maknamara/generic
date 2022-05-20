package br.com.maknamara.component;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

@SuppressWarnings("AndroidFrameworkPendingIntentMutability")
public class NotificationTool {
    private static final String CHANNEL_ID = NotificationTool.class.getName();

    @NonNull
    public static Notification createNotification(@NonNull Context context, @NonNull String title, @NonNull String message) {
        Intent intent = new Intent(context, context.getClass());

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentTitle(title);//required
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert);//required
        builder.setContentText(message);//required
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
        builder.setTicker(message);
        builder.setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        builder.setPriority(Notification.PRIORITY_HIGH);

        return builder.build();
    }
}
