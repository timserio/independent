package serio.tim.android.com.teatimer.timer

import android.content.Intent

interface TimerContract {
    interface View {
        fun enableStartButton()
        fun disableStartButton()
        fun enablePauseButton()
        fun disablePauseButton()
        fun enableStopButton()
        fun disableStopButton()
        fun updateTimerText()
        fun updateWaterTempText()
        fun updateTimerProgress(value: Int)
        fun updateWaterTemp(value: Long)
        fun updateTimerMaxProgress(value: Int)
        fun getDropdownSelectedIndex(): Int
        fun startService()
        fun getResultOk(): Int
        fun getIntentData(): Intent?
        fun showAddTimer()
        fun showEditTimer()
        fun showTimerText(text: String)
        fun showWaterTempText(text: String)
        fun showFirstDropdownItem()
        fun onTeaTypeSelected()
        fun startTimer()
        fun cancelTimer()
        fun setSelectedTeaIndex(index: Int)
        fun playSound()
    }

    interface Presenter {
        fun onStartButtonClicked()
        fun onPauseButtonClicked()
        fun onStopButtonClicked()
        fun updateTimerText(): String
        fun updateWaterTempText(): String
        fun getTeaKeysList(): MutableList<String>
        fun onTeaTypeSelected(position: Int)
        fun onPauseStartService()
        fun onActivityResult(requestCode: Int, resultCode: Int)
        fun onTimerMenuButtonClicked(id: Int?)
        fun getTeaKeysSize(): Int
        fun onTimerTick(p0: Long)
        fun onTimerFinished()
        fun onCreate()
        fun onResume()
        fun setUpTimersDropdown()
        fun getTimerLength(): Long
        fun getCurrProgress(): Int
        fun startTimerOnResume()
        fun getTeaIndex(): Int
        fun setTimerLengthEdit()
        fun setTimerLengthAdd()
    }
}