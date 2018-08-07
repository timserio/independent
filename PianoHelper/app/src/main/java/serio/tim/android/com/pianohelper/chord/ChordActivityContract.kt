package serio.tim.android.com.pianohelper.chord

interface ChordActivityContract {
    interface ChordApi {
        fun addInterval(interval: Int)
        fun getChordArray(): IntArray
        fun clear()
    }

    interface ChordView {
        fun showNotesDropdown(notesList: MutableList<String>)
        fun showChordsDropdown(chordTypes: List<String>)
    }

    interface ChordPresenter {
        fun calculateChord(note: String, chordType: String): IntArray
        fun setNote(note: String)
        fun getNote(): String
        fun setChordType(chordType: String)
        fun getChordType(): String
        fun getNotesArray(): Array<String>
        fun getChordTypes(): List<String>
    }
}