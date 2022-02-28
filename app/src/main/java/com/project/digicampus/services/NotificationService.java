package com.project.digicampus.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.project.digicampus.R;
import com.project.digicampus.SubjectViewActivity;
import com.project.digicampus.Utils;

import java.util.Map;
import java.util.Objects;

public class NotificationService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String,String> dataMap = remoteMessage.getData();

        if(dataMap.size() > 0) {
            String SUBJECT_ID = dataMap.get("SUBJECT_ID");
            Intent notifIntent = new Intent(this, SubjectViewActivity.class);
            notifIntent.putExtra("SUBJ_RTDB_ID", SUBJECT_ID);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifIntent, 0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "0")
                    .setSmallIcon(R.drawable.ic_baseline_announcement_24)
                    .setContentTitle(String.format("Νέα ανακοίνωση στο μάθημα %s", Objects.requireNonNull(Utils.mSubjectIDMap.get(SUBJECT_ID)).getName()))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1, builder.build());
        }
    }
}