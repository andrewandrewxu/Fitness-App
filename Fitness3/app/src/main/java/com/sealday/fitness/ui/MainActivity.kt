package com.sealday.fitness.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobService
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sealday.fitness.R
import com.sealday.fitness.service.PlanService


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, PlanService::class.java)
        startService(intent)

        val notificationManager = getSystemService(JobService.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("plan", "channel plan", NotificationManager.IMPORTANCE_HIGH)
            channel.description = "channel plan"
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }


    }
}
