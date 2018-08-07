package serio.tim.android.com.teatimer.backgroundtimer

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import serio.tim.android.com.teatimer.util.NotificationUtil
import serio.tim.android.com.teatimer.util.TimerInfo

class BackgroundTimerService : Service() {
    private lateinit var cdt: CountDownTimer


    override fun onCreate() {
        super.onCreate()

        NotificationUtil.dismiss(this)

        cdt = object : CountDownTimer(TimerInfo.currTimerLength * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                TimerInfo.currTimerLength = millisUntilFinished / 1000
                TimerInfo.state = TimerInfo.TimerState.Running
            }

            override fun onFinish() {
                TimerInfo.currTimerLength = TimerInfo.timerLength
                TimerInfo.state = TimerInfo.TimerState.Stopped
                NotificationUtil.timerDone(this@BackgroundTimerService)
                stopService(Intent(this@BackgroundTimerService, BackgroundTimerService::class.java))
            }
        }.start()
    }

    override fun onDestroy() {

        cdt.cancel()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}
