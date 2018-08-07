package serio.tim.android.com.teatimer.edittimer

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import biz.kasual.materialnumberpicker.MaterialNumberPicker
import kotlinx.android.synthetic.main.activity_edit_timer.*
import kotlinx.android.synthetic.main.content_edit_timer.*

import serio.tim.android.com.teatimer.R
import serio.tim.android.com.teatimer.data.TimerRepository
import serio.tim.android.com.teatimer.di.DaggerAppComponent
import javax.inject.Inject

class EditTimerActivity : AppCompatActivity(), EditTimerContract.View {

    @Inject
    lateinit var repository: TimerRepository

    private lateinit var presenter: EditTimerContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_timer)

        setSupportActionBar(toolbarEdit)

        supportActionBar?.setTitle(R.string.toolbar_edit_timer)

        DaggerAppComponent.create().inject(this)

        presenter = EditTimerPresenter(repository, this)

        text_delete_error.setText(R.string.label_empty)

        val intent = getIntent()

        presenter.setNameOfTea(intent.getStringExtra("nameOfTea"))

        presenter.setTeaKeysSize(intent.getIntExtra("teaKeysSize", 0))

        presenter.setTotalSeconds(intent.getLongExtra("timerLength", 60L))

        presenter.setWaterTemp(intent.getLongExtra("waterTemp", 205L))

        presenter.onCreate()

        fab_edit.setOnClickListener { v ->

            presenter.onEditButtonClicked()
        }
    }

    override fun finishEdit() {
        setResult(Activity.RESULT_OK, Intent().putExtra("editTime", presenter.getTotalSecs()))
        setResult(Activity.RESULT_OK, Intent().putExtra("editDegrees", presenter.getTotalDegrees()))
        this.finish()
    }

    override fun showZeroTimerLengthError() {
        //text_delete_error.text = "You can't have a timer length of 0."
        text_delete_error.setText(R.string.error_empty_timer_length)
    }

    private fun setUpMinPicker() {
        MaterialNumberPicker.Builder(this)
                .minValue(0)
                .maxValue(20)
                .defaultValue(1)
                .backgroundColor(Color.parseColor("#173B07"))
                .separatorColor(Color.parseColor("#B0D346"))
                .textColor(Color.parseColor("#B0D346"))
                .textSize(25.0F)
                .enableFocusability(false)
                .wrapSelectorWheel(true)
                .build()

        picker_edit_minutes.setOnValueChangedListener { numberPickerEdit, i, j ->
            presenter.onNumPickerMinutesValueChanged(j)
        }
    }

    private fun setUpSecsPicker() {
        MaterialNumberPicker.Builder(this)
                .minValue(0)
                .maxValue(5)
                .defaultValue(0)
                .backgroundColor(Color.parseColor("#173B07"))
                .separatorColor(Color.parseColor("#B0D346"))
                .textColor(Color.parseColor("#B0D346"))
                .textSize(25.0F)
                .enableFocusability(false)
                .wrapSelectorWheel(true)
                .build()

        picker_edit_seconds_tens.setOnValueChangedListener { numberPickerEditSecs, i, j ->
            presenter.onNumPickerSecondsValueChanged(j)
        }
    }

    private fun setUpSecsOnesPicker() {
        MaterialNumberPicker.Builder(this)
                .minValue(0)
                .maxValue(9)
                .defaultValue(0)
                .backgroundColor(Color.parseColor("#173B07"))
                .separatorColor(Color.parseColor("#B0D346"))
                .textColor(Color.parseColor("#B0D346"))
                .textSize(25.0F)
                .enableFocusability(false)
                .wrapSelectorWheel(true)
                .build()

        picker_edit_seconds_ones.setOnValueChangedListener { numberPickerEditSecs, i, j ->
            presenter.onNumPickerSecondsOnesPlaceValueChanged(j)
        }
    }

    private fun setUpDegreesHundredsPicker() {
        MaterialNumberPicker.Builder(this)
                .minValue(0)
                .maxValue(9)
                .defaultValue(0)
                .backgroundColor(Color.parseColor("#173B07"))
                .separatorColor(Color.parseColor("#B0D346"))
                .textColor(Color.parseColor("#B0D346"))
                .textSize(25.0F)
                .enableFocusability(false)
                .wrapSelectorWheel(true)
                .build()

        picker_edit_degrees_hundreds.setOnValueChangedListener { numberPickerEditDegrees, i, j ->
            presenter.onNumPickerDegreeHundredsChanged(j)
        }
    }

    private fun setUpDegreesTensPicker() {
        MaterialNumberPicker.Builder(this)
                .minValue(0)
                .maxValue(9)
                .defaultValue(0)
                .backgroundColor(Color.parseColor("#173B07"))
                .separatorColor(Color.parseColor("#B0D346"))
                .textColor(Color.parseColor("#B0D346"))
                .textSize(25.0F)
                .enableFocusability(false)
                .wrapSelectorWheel(true)
                .build()

        picker_edit_degrees_tens.setOnValueChangedListener { numberPickerEditDegrees, i, j ->
            presenter.onNumPickerDegreeTensChanged(j)
        }
    }

    private fun setUpDegreesOnesPicker() {
        MaterialNumberPicker.Builder(this)
                .minValue(0)
                .maxValue(9)
                .defaultValue(0)
                .backgroundColor(Color.parseColor("#173B07"))
                .separatorColor(Color.parseColor("#B0D346"))
                .textColor(Color.parseColor("#B0D346"))
                .textSize(25.0F)
                .enableFocusability(false)
                .wrapSelectorWheel(true)
                .build()

        picker_edit_degrees_ones.setOnValueChangedListener { numberPickerEditDegrees, i, j ->
            presenter.onNumPickerDegreeOnesChanged(j)
        }
    }

    override fun showTimerLengthInput() {
        setUpMinPicker()
        setUpSecsPicker()
        setUpSecsOnesPicker()
    }

    override fun showWaterTempInput() {
        setUpDegreesHundredsPicker()
        setUpDegreesTensPicker()
        setUpDegreesOnesPicker()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        presenter.onDeleteButtonClicked()

        return true
    }

    override fun finishDelete() {
        setResult(Activity.RESULT_OK, Intent().putExtra("deleteTimer", presenter.getNameOfTea()))

        this.finish()
    }

    override fun showDeleteOnlyTimerError() {
        text_delete_error.setText(R.string.error_delete)
    }

    override fun showTeaName(value: String) {
        text_edit_teaname.text = value
    }

    override fun showMinsInput(value: Int) {
        picker_edit_minutes.value = value
    }

    override fun showSecsTensInput(value: Int) {
        picker_edit_seconds_tens.value = value
    }

    override fun showSecsOnesInput(value: Int) {
        picker_edit_seconds_ones.value = value
    }

    override fun showDegreeHundredsInput(value: Int) {
        picker_edit_degrees_hundreds.value = value
    }

    override fun showDegreeTensInput(value: Int) {
        picker_edit_degrees_tens.value = value
    }

    override fun showDegreeOnesInput(value: Int) {
        picker_edit_degrees_ones.value = value
    }
}
