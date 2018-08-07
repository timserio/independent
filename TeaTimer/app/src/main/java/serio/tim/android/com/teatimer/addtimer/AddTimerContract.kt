package serio.tim.android.com.teatimer.addtimer

interface AddTimerContract {
    interface View {
        fun showEmptyTeaNameError()
        fun enableButton()
        fun disableButton()
        fun showTimerLengthInput()
        fun showWaterTempInput()
        fun showZeroTimerLengthError()
        fun showTimerExistsError()
        fun showZeroWaterTempError()
        fun finishAdd()
    }

    interface Presenter {
        fun saveTimer(teaName: String)
        fun checkForEmptyTeaNameError(teaName: String)
        fun checkForMaxTeaNameLengthError(teaNameLength: Int)
        fun onNumPickerMinutesValueChanged(value: Int)
        fun onNumPickerSecondsValueChanged(value: Int)
        fun onNumPickerSecondsOnesPlaceValueChanged(value: Int)
        fun onNumPickerDegreeHundredsValueChanged(value: Int)
        fun onNumPickerDegreeTensValueChanged(value: Int)
        fun onNumPickerDegreeOnesValueChanged(value: Int)
        fun initializeViews(text: String)
        fun afterTextChanged(text: String, length: Int)
    }
}