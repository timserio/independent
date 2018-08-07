package serio.tim.android.com.teatimer.addtimer

import serio.tim.android.com.teatimer.data.Timer
import serio.tim.android.com.teatimer.data.TimerRepository

class AddTimerPresenter(repository: TimerRepository, view: AddTimerContract.View) : AddTimerContract.Presenter {
    private var view: AddTimerContract.View
    private var repository: TimerRepository
    private var numbrMins = 1
    private var numbrSecs = 0
    private var numbrSecsOnes = 0
    private var degreeHundreds = 1
    private var degreeTens = 7
    private var degreeOnes = 0

    init {
        this.view = view
        this.repository = repository
    }

    override fun saveTimer(teaName: String) {
        val finalSecs = (numbrSecs * 10) + numbrSecsOnes
        val finalTimerLength = ((numbrMins * 60) + finalSecs).toLong()
        val degrees = ((degreeHundreds * 100) + (degreeTens * 10) + (degreeOnes)).toLong()

        if(finalTimerLength == 0L) {
            view.showZeroTimerLengthError()
        } else if(repository.teaNameExists(teaName)) {
            view.showTimerExistsError()
        } else if(degrees == 0L) {
            view.showZeroWaterTempError()
        } else {
            val timer = Timer(teaName, finalTimerLength, degrees)
            repository.saveTimer(timer)

            view.finishAdd()
        }
    }

    override fun checkForEmptyTeaNameError(teaName: String) {
        if(teaName.isEmpty()) {
            view.showEmptyTeaNameError()
            view.disableButton()
        } else {
            view.enableButton()
        }
    }

    override fun checkForMaxTeaNameLengthError(teaNameLength: Int) {
        if(teaNameLength > 29) {
            view.disableButton()
        }
    }

    override fun onNumPickerMinutesValueChanged(value: Int) {
        numbrMins = value
    }

    override fun onNumPickerSecondsValueChanged(value: Int) {
        numbrSecs = value
    }

    override fun onNumPickerSecondsOnesPlaceValueChanged(value: Int) {
        numbrSecsOnes = value
    }

    override fun onNumPickerDegreeHundredsValueChanged(value: Int) {
        degreeHundreds = value
    }

    override fun onNumPickerDegreeTensValueChanged(value: Int) {
        degreeTens = value
    }

    override fun onNumPickerDegreeOnesValueChanged(value: Int) {
        degreeOnes = value
    }

    override fun initializeViews(text: String) {
        view.showTimerLengthInput()
        view.showWaterTempInput()
        checkForEmptyTeaNameError(text)
    }

    override fun afterTextChanged(text: String, length: Int) {
        checkForEmptyTeaNameError(text)
        checkForMaxTeaNameLengthError(length)
    }
}