package serio.tim.android.com.pianohelper.chord

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.jaredrummler.materialspinner.MaterialSpinner
import kotlinx.android.synthetic.main.activity_chord.*
import serio.tim.android.com.pianohelper.R

class ChordActivity : AppCompatActivity(), ChordActivityContract.ChordView {

    private lateinit var presenter: ChordActivityContract.ChordPresenter

    private val NOTE_KEY = "note"
    private val CHORD_TYPE_KEY = "chordType"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chord)

        supportActionBar?.title = getString(R.string.text_chord_appbar)

        presenter = ChordActivityPresenter(this)

        initView()

        piano_chord.checkedKeys = presenter.calculateChord(presenter.getNote(), presenter.getChordType())

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(NOTE_KEY, presenter.getNote())
        outState?.putString(CHORD_TYPE_KEY, presenter.getChordType())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        presenter.setNote(savedInstanceState!!.getString(NOTE_KEY))
        presenter.setChordType(savedInstanceState.getString(CHORD_TYPE_KEY))
        piano_chord.checkedKeys = presenter.calculateChord(presenter.getNote(), presenter.getChordType())
    }

    override fun showNotesDropdown(notesList: MutableList<String>) {
        spinner_chord_note.setItems(notesList)
    }

    override fun showChordsDropdown(chordTypes: List<String>) {
        spinner_chord_type.setItems(chordTypes)
    }

    fun initView() {
        var notes: Array<String> = presenter.getNotesArray()
        presenter.setNote(notes[spinner_chord_note.selectedIndex])

        spinner_chord_note.setOnItemSelectedListener(MaterialSpinner.OnItemSelectedListener { _, position, _, _: String ->
            presenter.setNote(notes[position])
            piano_chord.checkedKeys = presenter.calculateChord(presenter.getNote(), presenter.getChordType())
        })

        val chordTypes: List<String> = presenter.getChordTypes()
        presenter.setChordType(chordTypes[spinner_chord_note.selectedIndex])

        spinner_chord_type.setOnItemSelectedListener(MaterialSpinner.OnItemSelectedListener { _, position, _, _: String ->
            presenter.setChordType(chordTypes[position])
            piano_chord.checkedKeys = presenter.calculateChord(presenter.getNote(), presenter.getChordType())
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.chord_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var id = item?.itemId

        if (id == R.id.chord_menu) {
            this.finish()
        }
        return true
    }
}
