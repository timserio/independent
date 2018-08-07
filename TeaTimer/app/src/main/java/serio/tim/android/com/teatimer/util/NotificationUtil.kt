package serio.tim.android.com.teatimer.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import serio.tim.android.com.teatimer.R
import serio.tim.android.com.teatimer.backgroundtimer.BackgroundTimerService
import serio.tim.android.com.teatimer.timer.TimerActivity


class NotificationUtil {


    companion object {
        private val expiredNotificationId = 3
        private lateinit var mBuilder: NotificationCompat.Builder

        fun dismiss(context: Context) {


            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.cancel(expiredNotificationId)

        }

        fun timerDone(context: Context) {
            val CHANNEL_ID = "doneChannelId"

            val intent = Intent(context, TimerActivity::class.java)
            val requestID = System.currentTimeMillis().toInt() //unique requestID to differentiate between various notification with same NotifId
            val flags = PendingIntent.FLAG_CANCEL_CURRENT // cancel old intent and create new one
            val pIntent = PendingIntent.getActivity(context, requestID, intent, flags)

            val startIntent = Intent(context, BackgroundTimerService::class.java)

            val startPendingIntent = PendingIntent.getService(context, 0, startIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, "Timer Done Channel", importance)
                channel.setDescription("Reminders")
                // Register the channel with the notifications manager
                val mNotificationManager =
                 context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                mNotificationManager.createNotificationChannel(channel)

                mBuilder =
                        // Builder class for devices targeting API 26+ requires a channel ID
                        NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_timer_white)
                        .setContentTitle("TeaTimer is done")
                        .setContentText("Timer expired")
                        .setContentIntent(pIntent)
                        .setAutoCancel(true)
                        .addAction(R.drawable.ic_restart, "Restart", startPendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT).setDefaults(Notification.DEFAULT_SOUND)
            } else {
                mBuilder =
                        // this Builder class is deprecated
                        NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_timer_white)
                        .setContentTitle("TeaTimer is done")
                        .setContentText("Timer expired")
                        .setContentIntent(pIntent)
                        .setAutoCancel(true)
                        .addAction(R.drawable.ic_restart, "Restart", startPendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT).setDefaults(Notification.DEFAULT_SOUND)
            }

            val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            mNotificationManager.notify(expiredNotificationId, mBuilder.build())
        }
    }
}