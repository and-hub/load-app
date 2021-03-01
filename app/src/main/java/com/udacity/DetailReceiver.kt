package com.udacity

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

private const val REQUEST_CODE = 0

class DetailReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val detailIntent = Intent(context, DetailActivity::class.java)
        context.startActivity(detailIntent)

        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.cancelAll()
    }
}