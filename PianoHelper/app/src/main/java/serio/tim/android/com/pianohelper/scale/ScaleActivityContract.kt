package serio.tim.android.com.pianohelper.scale

interface ScaleActivityContract {
    interface ScaleApi {
        fun addInterval(interval: Int)
        fun getScaleArray(): IntArray
        fun clear()
    }
    interface ScaleView {
        fun showNotesDropdown(notesList: MutableList<String>)
        fun showScalesDropdown(scaleTypes: List<String>)
    }
    interface ScalePresenter {
        fun calculateScale(note: String, scaleType: String): IntArray
        fun getNotesArray(): Array<String>
        fun getSortedNotes(): MutableList<String>
        fun getScaleTypes(): List<String>
        fun setNote(note: String)
        fun getNote(): String
        fun setScaleType(scaleType: String)
        fun getScaleType(): String
    }
}