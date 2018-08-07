package serio.tim.android.com.pianohelper.chord

import serio.tim.android.com.pianohelper.data.ChordApi
import serio.tim.android.com.pianohelper.data.Chords
import serio.tim.android.com.pianohelper.data.Notes

class ChordActivityPresenter(view: ChordActivityContract.ChordView) : ChordActivityContract.ChordPresenter {
    private var view: ChordActivityContract.ChordView
    private lateinit var api: ChordActivityContract.ChordApi
    private var chords = Chords()
    private lateinit var note: String
    private lateinit var chordType: String

    init {
        this.view = view
        initPresenter()
    }

    private fun initPresenter() {
        view.showNotesDropdown(getSortedNotes())
        view.showChordsDropdown(getChordTypes())
        this.api = ChordApi()
    }

    override fun calculateChord(note: String, chordType: String): IntArray {
        api.clear()
        val root: Int = Notes.notes.get(note)!!
        api.addInterval(root)
        var intervals = chords.chordMap.get(chordType)


        for(i in 0..(intervals!!.size - 1)) {
            api.addInterval(root.plus(intervals[i]))
        }

        return api.getChordArray()
    }

    override fun setNote(note: String) {
        this.note = note
    }

    override fun getNote(): String {
        return this.note
    }

    override fun setChordType(chordType: String) {
        this.chordType = chordType
    }

    override fun getChordType(): String {
        return this.chordType
    }

    override fun getNotesArray(): Array<String> {
        var notes: Array<String> = sortedKeys()
        return notes
    }

    override fun getChordTypes(): List<String> {
        return chords.chordMap.keys.toList()
    }

    private fun sortedKeys(): Array<String> {
        var notesArr: Array<String> = Notes.notes.keys.toTypedArray()
        notesArr.sort()
        return notesArr
    }

    private fun getSortedNotes(): MutableList<String> {
        var notes: Array<String> = sortedKeys()
        var notesList: MutableList<String> = notes.toMutableList()
        return notesList
    }
}