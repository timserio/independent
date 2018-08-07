package serio.tim.android.com.teatimer.data

import serio.tim.android.com.teatimer.di.MyApp

class SqlLiteTimerRepository(timerServiceApi: TimerServiceApi): TimerRepository {
    private var timerServiceApi: TimerServiceApi

    private var dbHelper = TimerDatabase(MyApp.getContext())

    init {
        this.timerServiceApi = checkNotNull(timerServiceApi)
    }

    override fun saveTimer(timer: Timer) {
        checkNotNull(timer)
        timerServiceApi.saveTimer(timer, dbHelper)
    }

    override fun updateTimer(timer: Timer) {
        checkNotNull(timer)
        timerServiceApi.updateTimer(timer, dbHelper)
    }

    override fun deleteTimer(timer: Timer) {
        checkNotNull(timer)
        timerServiceApi.deleteTimer(timer, dbHelper)
    }

    override fun setUpTimersDropdown(): MutableMap<String, Int> {
        val teas = timerServiceApi.setUpTimersDropdown(dbHelper)
        return teas
    }

    override fun setUpWaterTemp(): MutableMap<String, Int> {
        val waterTemps = timerServiceApi.setUpWaterTemp(dbHelper)
        return waterTemps
    }

    override fun teaNameExists(teaName: String): Boolean {
        val exists = timerServiceApi.teaNameExists(teaName, dbHelper)
        return exists
    }
}