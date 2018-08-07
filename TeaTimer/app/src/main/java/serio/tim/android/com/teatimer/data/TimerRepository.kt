package serio.tim.android.com.teatimer.data

interface TimerRepository {

    fun saveTimer(timer: Timer)

    fun updateTimer(timer: Timer)

    fun deleteTimer(timer: Timer)

    fun setUpTimersDropdown(): MutableMap<String, Int>

    fun setUpWaterTemp(): MutableMap<String, Int>

    fun teaNameExists(teaName: String): Boolean
}