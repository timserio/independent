package serio.tim.android.com.teatimer.timer

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.jaredrummler.materialspinner.MaterialSpinner

import kotlinx.android.synthetic.main.activity_timer.*
import kotlinx.android.synthetic.main.content_timer.*
import serio.tim.android.com.teatimer.*
import serio.tim.android.com.teatimer.addtimer.AddTimerActivity
import serio.tim.android.com.teatimer.backgroundtimer.BackgroundTimerService
import serio.tim.android.com.teatimer.data.TimerRepository
import serio.tim.android.com.teatimer.di.DaggerAppComponent
import serio.tim.android.com.teatimer.edittimer.EditTimerActivity
import serio.tim.android.com.teatimer.util.TimerInfo
import javax.inject.Inject

class TimerActivity : AppCompatActivity(), TimerContract.View {

    companion object {
        private const val TAG = "TimerActivity"
    }

    private lateinit var cdt: CountDownTimer
    private lateinit var text_timer_countdown: TextView
    private val EDIT_TIMER_REQUEST = 1
    private val ADD_TIMER_REQUEST = 2
    private var data: Intent? = null
    @Inject
    lateinit var repository: TimerRepository
    private lateinit var presenter: TimerContract.Presenter
    var player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        setSupportActionBar(toolbar)

        DaggerAppComponent.create().inject(this)

        presenter = TimerPresenter(repository, this)

        text_timer_countdown = findViewById(R.id.text_timer_countdown)

        fab_start.setOnClickListener { _ ->
            presenter.onStartButtonClicked()
        }

        fab_pause.setOnClickListener { _ ->
            presenter.onPauseButtonClicked()
        }

        fab_stop.setOnClickListener { _ ->
            presenter.onStopButtonClicked()
        }

        presenter.onCreate()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putLong("currTimerLength", TimerInfo.currTimerLength)
        outState?.putLong("timerLength", TimerInfo.timerLength)
        outState?.putInt("stateOrdinal", TimerInfo.state.ordinal)
        outState?.putString("teaName", TimerInfo.teaName)
        outState?.putLong("waterTemp", TimerInfo.waterTemp)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        TimerInfo.currTimerLength = savedInstanceState!!.getLong("currTimerLength")
        TimerInfo.timerLength = savedInstanceState.getLong("timerLength")
        progress_timer_countdown.max = TimerInfo.timerLength.toInt()
        TimerInfo.state = TimerInfo.TimerState.values()[savedInstanceState.getInt("stateOrdinal")]
        TimerInfo.teaName = savedInstanceState.getString("teaName")
        spinner_timer_tea_type.selectedIndex = presenter.getTeaIndex()
        TimerInfo.waterTemp = savedInstanceState.getLong("waterTemp")
        text_water_temp.text = "${TimerInfo.waterTemp}°F"
    }

    override fun onResume() {
        super.onResume()

        stopService(Intent(this@TimerActivity, BackgroundTimerService::class.java))

        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()

        presenter.onPauseStartService()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        this.data = data
        presenter.onActivityResult(requestCode, resultCode)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var id = item?.itemId
        presenter.onTimerMenuButtonClicked(id)
        return true
    }

    override fun getResultOk(): Int {
        return Activity.RESULT_OK
    }

    override fun getIntentData(): Intent? {
        return this.data
    }

    override fun startTimer() {
        cdt = object : CountDownTimer(TimerInfo.currTimerLength * 1000, 1000) {
            override fun onTick(p0: Long) {
                presenter.onTimerTick(p0)
            }

            override fun onFinish() {
                presenter.onTimerFinished()
            }
        }.start()
    }

    override fun cancelTimer() {
        cdt.cancel()
    }

    override fun onTeaTypeSelected() {
        spinner_timer_tea_type.setItems(presenter.getTeaKeysList())

        spinner_timer_tea_type.setOnItemSelectedListener(MaterialSpinner.OnItemSelectedListener { _, position, _, _: String ->
            presenter.onTeaTypeSelected(position)
        })
    }

    override fun enableStartButton() {
        fab_start.isEnabled = true
    }

    override fun disableStartButton() {
        fab_start.isEnabled = false
    }

    override fun enablePauseButton() {
        fab_pause.isEnabled = true
    }

    override fun disablePauseButton() {
        fab_pause.isEnabled = false
    }

    override fun enableStopButton() {
        fab_stop.isEnabled = true
    }

    override fun disableStopButton() {
        fab_stop.isEnabled = false
    }

    override fun updateTimerText() {
        text_timer_countdown.text = presenter.updateTimerText()
    }

    override fun updateWaterTempText() {
        text_water_temp.text = presenter.updateWaterTempText()
    }

    override fun updateTimerMaxProgress(value: Int) {
        progress_timer_countdown.max = value
    }

    override fun updateTimerProgress(value: Int) {
        progress_timer_countdown.progress = value
    }

    override fun updateWaterTemp(value: Long) {
        text_water_temp.text = "${value}°F"
    }

    override fun getDropdownSelectedIndex(): Int {
        return spinner_timer_tea_type.selectedIndex
    }

    override fun startService() {
        startService(Intent(this@TimerActivity, BackgroundTimerService::class.java))
    }

    override fun showTimerText(text: String) {
        text_timer_countdown.text = text
    }

    override fun showWaterTempText(text: String) {
        text_water_temp.text = text
    }

    override fun showAddTimer() {
        val intent = Intent(this, AddTimerActivity::class.java)
        intent.putExtra("fromAdd", "fromAdd")
        startActivityForResult(intent, ADD_TIMER_REQUEST)
    }

    override fun showEditTimer() {
        val intent = Intent(this, EditTimerActivity::class.java)
        intent.putExtra("nameOfTea", TimerInfo.teaName)
        intent.putExtra("timerLength", TimerInfo.timerLength)
        intent.putExtra("teaKeysSize", presenter.getTeaKeysSize())
        intent.putExtra("waterTemp", TimerInfo.waterTemp)
        startActivityForResult(intent, EDIT_TIMER_REQUEST)
    }

    override fun showFirstDropdownItem() {
        spinner_timer_tea_type.selectedIndex = 0
    }

    override fun setSelectedTeaIndex(index: Int) {
        spinner_timer_tea_type.selectedIndex = index
    }

    override fun playSound() {
        stopSound()

        player = MediaPlayer.create(this, R.raw.ding)
        player!!.start()
    }

    private fun stopSound() {
        player?.stop()
        player = null
    }
}
