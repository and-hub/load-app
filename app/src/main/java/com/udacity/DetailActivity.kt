package com.udacity

import android.app.DownloadManager
import android.app.NotificationManager
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

private const val EXTRA_NOTIFICATION_ID = "com.udacity.NOTIFICATION_ID"
private const val EXTRA_FILE_NAME = "com.udacity.FILE_NAME"
private const val EXTRA_STATUS = "com.udacity.STATUS"

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        val notificationID = intent.getIntExtra(EXTRA_NOTIFICATION_ID, -1)
        if (notificationID != -1) {
            val notificationManager = ContextCompat.getSystemService(
                this,
                NotificationManager::class.java
            ) as NotificationManager
            notificationManager.cancel(notificationID)
        }

        file_name_text.text = intent.getStringExtra(EXTRA_FILE_NAME) ?: ""
        val status = intent.getStringExtra(EXTRA_STATUS) ?: ""
        status_text.text = status

        if (status == getString(R.string.failed)) {
            status_text.setTextColor(Color.RED)
        } else {
            status_text.setTextColor(getColor(R.color.colorPrimaryDark))
        }
    }
}
