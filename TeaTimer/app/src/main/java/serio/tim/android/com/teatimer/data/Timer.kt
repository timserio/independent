package serio.tim.android.com.teatimer.data

import java.util.*

data class Timer(val teaName: String, val timerLength: Long, val waterTemp: Long) {
    val teaId: String = UUID.randomUUID().toString()
}