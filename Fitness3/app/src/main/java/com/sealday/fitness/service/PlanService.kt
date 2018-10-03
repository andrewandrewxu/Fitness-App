package com.sealday.fitness.service

import android.app.Notification
import android.app.NotificationManager
import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Handler
import android.os.Message
import android.support.v4.app.NotificationCompat
import com.sealday.fitness.R
import com.sealday.fitness.db.AppDatabase


public class PlanService : JobService() {
    private val mJobHandler = Handler(Handler.Callback { msg ->
        val dict = msg.obj as Map<String, Any>
        val plan = dict["plan"] as String
        val params = dict["jobParams"] as JobParameters
        val notificationManager = getSystemService(JobService.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat
                .Builder(this, "plan")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Plan Notification")
                .setContentText(plan)
                .setDefaults(Notification.DEFAULT_SOUND)
                .build()
        notificationManager.notify(msg.what, notification)
        jobFinished(params, false)
        true
    })

    override fun onStartJob(params: JobParameters): Boolean {
        Thread {
            val plan = AppDatabase.getInstance(this).planDao().findById(params.jobId)
            if (plan != null) {
                val dict = mapOf("plan" to plan.name, "jobParams" to params)
                mJobHandler.sendMessage(Message.obtain(mJobHandler, params.jobId,  dict))
            }
        }.start()
        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        mJobHandler.removeMessages(params.jobId)
        return false
    }
}
