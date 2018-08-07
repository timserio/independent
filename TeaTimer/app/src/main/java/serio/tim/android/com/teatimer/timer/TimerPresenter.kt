package serio.tim.android.com.teatimer.timer

import serio.tim.android.com.teatimer.R
import serio.tim.android.com.teatimer.data.TimerRepository
import serio.tim.android.com.teatimer.di.MyApp
import serio.tim.android.com.teatimer.util.NotificationUtil
import serio.tim.android.com.teatimer.util.TimerInfo

class TimerPresenter(repository: TimerRepository, view: TimerContract.View) : TimerContract.Presenter {
    private lateinit var teas: MutableMap<String, Int>
    private lateinit var waterTemps: MutableMap<String, Int>
    private lateinit var teaKeys: Array<String>
    private val EDIT_TIMER_REQUEST = 1
    private val ADD_TIMER_REQUEST = 2
    private var fromEdit = false
    private var fromAdd = false
    private var view: TimerContract.View
    private var repository: TimerRepository

    init {
        this.view = view
        this.repository = repository
    }

    override fun onStartButtonClicked() {
        TimerInfo.state = TimerInfo.TimerState.Running
        view.startTimer()
        updateButtons()
    }

    override fun onPauseButtonClicked() {
        view.cancelTimer()
        TimerInfo.state = TimerInfo.TimerState.Paused
        updateButtons()
    }

    override fun onStopButtonClicked() {
        view.cancelTimer()
        TimerInfo.currTimerLength = TimerInfo.timerLength
        view.updateTimerProgress(0)
        view.updateTimerText()
        TimerInfo.state = TimerInfo.TimerState.Stopped
        updateButtons()
    }

    private fun updateButtons() {
        if (TimerInfo.state.equals(TimerInfo.TimerState.Running)) {
            view.disableStartButton()
            view.enablePauseButton()
            view.enableStopButton()
        } else if (TimerInfo.state.equals(TimerInfo.TimerState.Paused)) {
            view.enableStartButton()
            view.disablePauseButton()
            view.enableStopButton()
        } else if (TimerInfo.state.equals(TimerInfo.TimerState.Stopped)) {
            view.enableStartButton()
            view.enablePauseButton()
            view.disableStopButton()
        }
    }

    override fun onTimerTick(p0: Long) {
        TimerInfo.currTimerLength = p0 / 1000
        view.updateTimerText()
        view.updateTimerProgress((TimerInfo.timerLength - TimerInfo.currTimerLength).toInt())
    }

    override fun onTimerFinished() {
        TimerInfo.state = TimerInfo.TimerState.Stopped
        TimerInfo.currTimerLength = TimerInfo.timerLength
        view.updateTimerText()
        view.updateTimerProgress(0)
        updateButtons()
        view.playSound()
    }

    override fun updateTimerText(): String {
        var secs = (TimerInfo.currTimerLength % 60).toString()
        return "${TimerInfo.currTimerLength / 60}:${if (secs.length == 1) "0" + secs else secs}"
    }

    override fun updateWaterTempText(): String {
        var degreeHundreds = TimerInfo.waterTemp / 100
        val degreeTens = (TimerInfo.waterTemp % 100) / 10
        val degreeOnes = (TimerInfo.waterTemp % 100) % 10
        return "$degreeHundreds$degreeTens$degreeOnes"
    }

    override fun setUpTimersDropdown() {
        teas = repository.setUpTimersDropdown()

        waterTemps = repository.setUpWaterTemp()

        teaKeys = teas.keys.toTypedArray()

        teaKeys.sort()

        if (TimerInfo.teaName.equals("")) {
            TimerInfo.teaName = teaKeys[0]
        }

        view.onTeaTypeSelected()
    }

    override fun getTeaKeysList(): MutableList<String> {
        return teaKeys.toMutableList()
    }

    override fun onTeaTypeSelected(position: Int) {
        if(TimerInfo.state.equals(TimerInfo.TimerState.Running) || TimerInfo.state.equals(TimerInfo.TimerState.Paused)) {
            TimerInfo.state = TimerInfo.TimerState.Stopped
            updateButtons()
            view.cancelTimer()
        }
        TimerInfo.teaName = teaKeys[position]

        var timeSecs = teas.get(TimerInfo.teaName)

        var waterTemp = waterTemps.get(TimerInfo.teaName)

        TimerInfo.waterTemp = waterTemp!!.toLong()

        TimerInfo.timerLength = timeSecs!!.toLong()

        TimerInfo.currTimerLength = TimerInfo.timerLength

        view.updateTimerText()

        view.updateTimerMaxProgress(TimerInfo.timerLength.toInt())

        view.updateTimerProgress(0)

        view.updateWaterTemp(TimerInfo.waterTemp)
    }

    override fun getTimerLength(): Long {
        return teas.get(teaKeys[view.getDropdownSelectedIndex()])!!.toLong()
    }

    override fun getCurrProgress(): Int {
        return (TimerInfo.timerLength - TimerInfo.currTimerLength).toInt()
    }

    override fun startTimerOnResume() {
        if (TimerInfo.state.equals(TimerInfo.TimerState.Running)) {
            view.startTimer()
        }
    }

    override fun getTeaIndex(): Int {
        var indexOfTeaKey = 0
        if (teaKeys.size > 1) {
            for (i in 0..(teaKeys.size - 1)) {
                if (teaKeys[i] == TimerInfo.teaName) {
                    indexOfTeaKey = i
                }
            }
        }
        return indexOfTeaKey
    }

    override fun setTimerLengthEdit() {
        var timeSecs = teas.get(TimerInfo.teaName)
        TimerInfo.timerLength = timeSecs!!.toLong()
        TimerInfo.currTimerLength = TimerInfo.timerLength
    }

    override fun setTimerLengthAdd() {
        var timeMins = teas.get(teaKeys.get(0))
        TimerInfo.timerLength = timeMins!!.toLong()
        TimerInfo.currTimerLength = TimerInfo.timerLength
    }

    override fun onPauseStartService() {
        if (TimerInfo.state.equals(TimerInfo.TimerState.Running)) {
            view.cancelTimer()

            view.startService()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int) {
        if(requestCode == 0) {
            return
        }

        if (requestCode == ADD_TIMER_REQUEST && resultCode == view.getResultOk()) {
            fromAdd = true
        }

        if (requestCode == EDIT_TIMER_REQUEST && resultCode == view.getResultOk()) {
            if ((view.getIntentData()?.getLongExtra("editTime", 0)?.equals(0))?.not() ?: false) {
                val totalSeconds: Long? = view.getIntentData()?.getLongExtra("editTime", 0)
                val waterTemp: Long? = view.getIntentData()?.getLongExtra("editDegrees", 0)
                TimerInfo.timerLength = totalSeconds!!
                TimerInfo.currTimerLength = totalSeconds
                TimerInfo.waterTemp = waterTemp!!
                teas.put(TimerInfo.teaName, totalSeconds.toInt())
                waterTemps.put(TimerInfo.teaName, TimerInfo.waterTemp.toInt())
                fromEdit = true
            }

            if (view.getIntentData()?.getStringExtra("deleteTimer") != null) {
                val timerToDelete = view.getIntentData()?.getStringExtra("deleteTimer")
                teas = repository.setUpTimersDropdown()
                waterTemps = repository.setUpWaterTemp()
                teas.remove(timerToDelete)
                teaKeys = teas.keys.toTypedArray()
                TimerInfo.teaName = teaKeys.get(0)
                TimerInfo.timerLength = teas.get(TimerInfo.teaName)!!.toLong()
                TimerInfo.currTimerLength = TimerInfo.timerLength
                TimerInfo.waterTemp = waterTemps.get(TimerInfo.teaName)!!.toLong()
                view.showFirstDropdownItem()
                view.updateTimerText()
                fromEdit = true
            }
        }
    }

    override fun onTimerMenuButtonClicked(id: Int?) {
        if (id == R.id.add_menu) {
            if(TimerInfo.state.equals(TimerInfo.TimerState.Running)) {
                view.cancelTimer()
                TimerInfo.state = TimerInfo.TimerState.Paused
                updateButtons()
            }
            view.showAddTimer()
        } else if (id == R.id.edit_menu && teas.size > 0) {
            if(TimerInfo.state.equals(TimerInfo.TimerState.Running)) {
                view.cancelTimer()
                TimerInfo.state = TimerInfo.TimerState.Paused
                updateButtons()
            }
            view.showEditTimer()
        }
    }

    override fun getTeaKeysSize(): Int {
        return teaKeys.size
    }

    override fun onCreate() {
        setUpTimersDropdown()

        TimerInfo.timerLength = getTimerLength()

        TimerInfo.currTimerLength = TimerInfo.timerLength

        view.updateTimerText()

        view.updateTimerMaxProgress(TimerInfo.timerLength.toInt())

        view.updateTimerProgress(0)

        view.updateWaterTemp(TimerInfo.waterTemp)
    }

    override fun onResume() {
        NotificationUtil.dismiss(MyApp.getContext())
        view.updateTimerText()
        view.updateTimerProgress(getCurrProgress())
        updateButtons()
        startTimerOnResume()

        setUpTimersDropdown()


        if (fromEdit) {
            setTimerLengthEdit()

            view.updateTimerProgress(0)
            view.updateTimerMaxProgress(TimerInfo.timerLength.toInt())
            updateTimerText()
            view.updateTimerText()

            updateWaterTempText()

            fromEdit = false

            view.setSelectedTeaIndex(getTeaIndex())
            view.showWaterTempText("${waterTemps[TimerInfo.teaName]}°F")
        }

        if (fromAdd) {
            setTimerLengthAdd()

            view.updateTimerProgress(0)

            updateTimerText()

            fromAdd = false

            view.setSelectedTeaIndex(getTeaIndex())
        }
    }
}