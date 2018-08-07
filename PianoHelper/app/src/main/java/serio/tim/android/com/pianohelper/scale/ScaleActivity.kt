package serio.tim.android.com.pianohelper.scale

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.jaredrummler.materialspinner.MaterialSpinner
import kotlinx.android.synthetic.main.activity_scale.*
import serio.tim.android.com.pianohelper.R

class ScaleActivity : AppCompatActivity(), ScaleActivityContract.ScaleView {

    private lateinit var presenter: ScaleActivityContract.ScalePresenter
    private val NOTE_KEY = "note"
    private val SCALE_TYPE_KEY = "scaleType"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scale)

        supportActionBar?.title = getString(R.string.text_scale_appbar)

        presenter = ScaleActivityPresenter(this)

        initView()

        piano_scale.checkedKeys = presenter.calculateScale(presenter.getNote(), presenter.getScaleType())
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState!!.putString(NOTE_KEY, presenter.getNote())
        outState.putString(SCALE_TYPE_KEY, presenter.getScaleType())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        presenter.setNote(savedInstanceState!!.getString(NOTE_KEY))
        presenter.setScaleType(savedInstanceState.getString(SCALE_TYPE_KEY))
        piano_scale.checkedKeys = presenter.calculateScale(presenter.getNote(), presenter.getScaleType())
    }

    override fun showNotesDropdown(notesList: MutableList<String>) {
        spinner_scale_note.setItems(notesList)
    }

    override fun showScalesDropdown(scaleTypes: List<String>) {
        spinner_scale_type.setItems(scaleTypes)
    }

    fun initView() {
        var notes: Array<String> = presenter.getNotesArray()
        presenter.setNote(notes[spinner_scale_note.selectedIndex])

        spinner_scale_note.setOnItemSelectedListener(MaterialSpinner.OnItemSelectedListener { _, position, _, _: String ->
            presenter.setNote(notes[position])
            piano_scale.checkedKeys = presenter.calculateScale(presenter.getNote(), presenter.getScaleType())
        })

        val scaleTypes: List<String> = presenter.getScaleTypes()
        presenter.setScaleType(scaleTypes[spinner_scale_note.selectedIndex])

        spinner_scale_type.setOnItemSelectedListener(MaterialSpinner.OnItemSelectedListener { _, position, _, _: String ->
            presenter.setScaleType(scaleTypes[position])
            piano_scale.checkedKeys = presenter.calculateScale(presenter.getNote(), presenter.getScaleType())
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.scale_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var id = item?.itemId

        if (id == R.id.scale_menu) {
            this.finish()
        }
        return true
    }
}
