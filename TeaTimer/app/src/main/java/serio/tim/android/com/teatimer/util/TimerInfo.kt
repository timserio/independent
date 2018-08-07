package serio.tim.android.com.teatimer.util

class TimerInfo {
    enum class TimerState{
        Stopped, Paused, Running
    }

    companion object {
        var state: TimerState = TimerState.Stopped
        var timerLength: Long = 30L
        var currTimerLength: Long = 30L
        var teaName: String = ""
        var waterTemp: Long = 205L
    }
}