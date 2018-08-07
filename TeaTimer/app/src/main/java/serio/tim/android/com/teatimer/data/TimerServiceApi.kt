package serio.tim.android.com.teatimer.data

interface TimerServiceApi {

    fun saveTimer(timer: Timer, dbHelper: TimerDatabase)

    fun updateTimer(timer: Timer, dbHelper: TimerDatabase)

    fun deleteTimer(timer: Timer, dbHelper: TimerDatabase)

    fun setUpTimersDropdown(dbHelper: TimerDatabase): MutableMap<String, Int>

    fun setUpWaterTemp(dbHelper: TimerDatabase): MutableMap<String, Int>

    fun teaNameExists(teaName: String, dbHelper: TimerDatabase): Boolean

}