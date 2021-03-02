package com.udacity

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

private const val NOTIFICATION_ID = 0
private const val EXTRA_NOTIFICATION_ID = "com.udacity.NOTIFICATION_ID"

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {

    val detailIntent = Intent(applicationContext, DetailActivity::class.java).apply {
        putExtra(EXTRA_NOTIFICATION_ID, NOTIFICATION_ID)
    }

    val detailPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        detailIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.download_notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setContentTitle(
            applicationContext
                .getString(R.string.notification_title)
        )
        .setContentText(messageBody)
        .addAction(
            R.drawable.ic_assistant_black_24dp,
            applicationContext.getString(R.string.notification_button),
            detailPendingIntent
        )
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setDefaults(Notification.DEFAULT_ALL)

    notify(NOTIFICATION_ID, builder.build())
}
