package serio.tim.android.com.teatimer.edittimer

import serio.tim.android.com.teatimer.data.Timer
import serio.tim.android.com.teatimer.data.TimerRepository
import serio.tim.android.com.teatimer.util.TimerInfo

class EditTimerPresenter(repository: TimerRepository, view: EditTimerContract.View) : EditTimerContract.Presenter {
    private var nameOfTea: String = ""
    private var numbrMins = 1
    private var totalSeconds = 60L
    private var teaKeysSize = 0
    private var secsTens = 0
    private var secsOnes = 0
    private var degreesHundreds = 1
    private var degreesTens = 7
    private var degreesOnes = 0
    private var waterTemp = 205L
    private var view: EditTimerContract.View
    private var repository: TimerRepository

    init {
        this.view = view
        this.repository = repository
    }

    override fun setNameOfTea(teaName: String) {
        this.nameOfTea = teaName
    }

    override fun getNameOfTea(): String {
        return this.nameOfTea
    }

    override fun setTeaKeysSize(teaKeysSize: Int) {
        this.teaKeysSize = teaKeysSize
    }

    override fun setTotalSeconds(totalSeconds: Long) {
        this.totalSeconds = totalSeconds
    }

    override fun setWaterTemp(waterTemp: Long) {
        this.waterTemp = waterTemp
        TimerInfo.waterTemp = waterTemp
    }

    override fun setTimerInputValues() {
        this.numbrMins = (this.totalSeconds / 60).toInt()
        this.secsTens = ((this.totalSeconds % 60) / 10).toInt()
        this.secsOnes = ((this.totalSeconds % 60) % 10).toInt()
    }

    override fun setWaterTempInputValues() {
        this.degreesHundreds = (this.waterTemp / 100).toInt()
        this.degreesTens = ((this.waterTemp % 100) / 10).toInt()
        this.degreesOnes = ((this.waterTemp % 100) % 10).toInt()
    }

    override fun getNumbrMins(): Int {
        return this.numbrMins
    }

    override fun getSecsTens(): Int {
        return this.secsTens
    }

    override fun getSecsOnes(): Int {
        return this.secsOnes
    }

    override fun onEditButtonClicked() {
        if(getTotalSecs() == 0L) {
            view.showZeroTimerLengthError()
        } else {
            val timer = Timer(this.nameOfTea, getTotalSecs(), getTotalDegrees())
            repository.updateTimer(timer)

            view.finishEdit()
        }
    }

    override fun getTotalSecs(): Long {
        val finalSecs = (secsTens * 10) + secsOnes
        return ((numbrMins * 60) + finalSecs).toLong()
    }

    override fun getDegreeHundreds(): Int {
        return this.degreesHundreds
    }

    override fun getDegreeTens(): Int {
        return this.degreesTens
    }

    override fun getDegreeOnes(): Int {
        return this.degreesOnes
    }

    override fun getTotalDegrees(): Long {
        val finalSecs = (degreesTens * 10) + degreesOnes
        return ((degreesHundreds * 100) + finalSecs).toLong()
    }

    override fun onNumPickerMinutesValueChanged(value: Int) {
        this.numbrMins = value
    }

    override fun onNumPickerSecondsValueChanged(value: Int) {
        this.secsTens = value
    }

    override fun onNumPickerSecondsOnesPlaceValueChanged(value: Int) {
        this.secsOnes = value
    }

    override fun onNumPickerDegreeHundredsChanged(value: Int) {
        this.degreesHundreds = value
    }

    override fun onNumPickerDegreeTensChanged(value: Int) {
        this.degreesTens = value
    }

    override fun onNumPickerDegreeOnesChanged(value: Int) {
        this.degreesOnes = value
    }

    override fun onDeleteButtonClicked() {
        if(this.teaKeysSize <= 1) {
            view.showDeleteOnlyTimerError()

        } else {
            val timer = Timer(this.nameOfTea, getTotalSecs(), waterTemp)
            repository.deleteTimer(timer)

            view.finishDelete()
        }
    }

    override fun onCreate() {

        view.showTeaName(getNameOfTea())

        setTimerInputValues()

        setWaterTempInputValues()

        view.showTimerLengthInput()

        view.showMinsInput(getNumbrMins())

        view.showSecsTensInput(getSecsTens())

        view.showSecsOnesInput(getSecsOnes())

        view.showWaterTempInput()

        view.showDegreeHundredsInput(getDegreeHundreds())

        view.showDegreeTensInput(getDegreeTens())

        view.showDegreeOnesInput(getDegreeOnes())
    }
}