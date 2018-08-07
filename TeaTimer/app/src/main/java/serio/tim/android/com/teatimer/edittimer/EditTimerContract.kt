package serio.tim.android.com.teatimer.edittimer

interface EditTimerContract {
    interface View {
        fun showTimerLengthInput()
        fun showWaterTempInput()
        fun showDeleteOnlyTimerError()
        fun finishDelete()
        fun finishEdit()
        fun showZeroTimerLengthError()
        fun showTeaName(value: String)
        fun showMinsInput(value: Int)
        fun showSecsTensInput(value: Int)
        fun showSecsOnesInput(value: Int)
        fun showDegreeHundredsInput(value: Int)
        fun showDegreeTensInput(value: Int)
        fun showDegreeOnesInput(value: Int)
    }

    interface Presenter {
        fun setNameOfTea(teaName: String)
        fun getNameOfTea(): String
        fun getDegreeHundreds(): Int
        fun getDegreeTens(): Int
        fun getDegreeOnes(): Int
        fun setTeaKeysSize(teaKeysSize: Int)
        fun setTotalSeconds(totalSeconds: Long)
        fun setWaterTemp(waterTemp: Long)
        fun setTimerInputValues()
        fun setWaterTempInputValues()
        fun getNumbrMins(): Int
        fun getSecsTens(): Int
        fun getSecsOnes(): Int
        fun onEditButtonClicked()
        fun getTotalSecs(): Long
        fun getTotalDegrees(): Long
        fun onNumPickerMinutesValueChanged(value: Int)
        fun onNumPickerSecondsValueChanged(value: Int)
        fun onNumPickerSecondsOnesPlaceValueChanged(value: Int)
        fun onNumPickerDegreeHundredsChanged(value: Int)
        fun onNumPickerDegreeTensChanged(value: Int)
        fun onNumPickerDegreeOnesChanged(value: Int)
        fun onDeleteButtonClicked()
        fun onCreate()
    }
}