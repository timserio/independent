package serio.tim.android.com.teatimer.addtimer

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import biz.kasual.materialnumberpicker.MaterialNumberPicker

import kotlinx.android.synthetic.main.activity_add_timer.*
import kotlinx.android.synthetic.main.content_add_timer.*
import serio.tim.android.com.teatimer.R
import serio.tim.android.com.teatimer.data.TimerRepository
import serio.tim.android.com.teatimer.di.*
import javax.inject.Inject

class AddTimerActivity : AppCompatActivity(), AddTimerContract.View {

    @Inject
    lateinit var repository: TimerRepository

    private lateinit var presenter: AddTimerContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_timer)
        setSupportActionBar(toolbarAdd)

        supportActionBar?.setTitle(R.string.toolbar_add_timer)

        DaggerAppComponent.create().inject(this)

        presenter = AddTimerPresenter(repository, this)

        text_add_error.setText(R.string.label_empty)

        presenter.initializeViews(edittext_add.text.toString().trim())


        edittext_add.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                presenter.afterTextChanged(edittext_add.text.toString().trim(), edittext_add.text.toString().length)
            }
        })

        fab_add.setOnClickListener { v ->
            presenter.saveTimer(edittext_add.text.toString())
        }
    }

    override fun showEmptyTeaNameError() {
        edittext_add.error = getString(R.string.error_add_timer_empty_teaname)
    }

    override fun enableButton() {
        fab_add.isEnabled = true
    }

    override fun disableButton() {
        fab_add.isEnabled = false
    }

    override fun showTimerLengthInput() {
        setUpMinPicker()
        setUpSecsPicker()
        setUpSecsOnesPicker()
    }

    override fun showWaterTempInput() {
        setUpDegreeHundredsPicker()
        setUpDegreeTensPicker()
        setUpDegreeOnesPicker()
    }

    override fun showZeroTimerLengthError() {
        text_add_error.setText(R.string.error_empty_timer_length)
    }

    override fun showTimerExistsError() {
        text_add_error.setText(R.string.error_timer_exists)
    }

    override fun showZeroWaterTempError() {
        text_add_error.setText(R.string.error_empty_water_temp)
    }

    override fun finishAdd() {
        setResult(Activity.RESULT_OK, Intent().putExtra("fromAdd", "fromAdd"))

        this.finish()
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

        picker_add_minutes.setOnValueChangedListener { numberPicker, i, j ->
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

        picker_add_seconds_tens.value = 0

        picker_add_seconds_tens.setOnValueChangedListener { numberPicker, i, j ->
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

        picker_add_seconds_ones.value = 0

        picker_add_seconds_ones.setOnValueChangedListener { numberPicker, i, j ->
            presenter.onNumPickerSecondsOnesPlaceValueChanged(j)
        }
    }

    private fun setUpDegreeHundredsPicker() {
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

        picker_add_degrees_hundreds.value = 1

        picker_add_degrees_hundreds.setOnValueChangedListener { numberPicker, i, j ->
            presenter.onNumPickerDegreeHundredsValueChanged(j)
        }
    }

    private fun setUpDegreeTensPicker() {
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

        picker_add_degrees_tens.value = 7

        picker_add_degrees_tens.setOnValueChangedListener { numberPicker, i, j ->
            presenter.onNumPickerDegreeTensValueChanged(j)
        }
    }

    private fun setUpDegreeOnesPicker() {
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

        picker_add_degrees_ones.value = 0

        picker_add_degrees_ones.setOnValueChangedListener { numberPicker, i, j ->
            presenter.onNumPickerDegreeOnesValueChanged(j)
        }
    }

}
