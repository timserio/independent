package serio.tim.android.com.pianohelper.scale

import serio.tim.android.com.pianohelper.data.Notes
import serio.tim.android.com.pianohelper.data.ScaleApi
import serio.tim.android.com.pianohelper.data.Scales

class ScaleActivityPresenter(view: ScaleActivityContract.ScaleView) : ScaleActivityContract.ScalePresenter {

    private var view: ScaleActivityContract.ScaleView
    private lateinit var api: ScaleActivityContract.ScaleApi
    private val scales = Scales()
    private lateinit var note: String
    private lateinit var scaleType: String

    init {
        this.view = view
        initPresenter()
    }

    private fun initPresenter() {
        this.api = ScaleApi()
        view.showNotesDropdown(getSortedNotes())
        view.showScalesDropdown(getScaleTypes())

    }

    override fun calculateScale(note: String, scaleType: String): IntArray {
        api.clear()
        val root: Int = Notes.notes.get(note)!!
        api.addInterval(root)
        var intervals = scales.scaleMap.get(scaleType)

        for(i in 0..(intervals!!.size - 1)) {
            api.addInterval(root.plus(intervals[i]))
        }

        return api.getScaleArray()
    }

    private fun sortedKeys(): Array<String> {
        var notesArr: Array<String> = Notes.notes.keys.toTypedArray()
        notesArr.sort()
        return notesArr
    }

    override fun getNotesArray(): Array<String> {
        var notes: Array<String> = sortedKeys()
        return notes
    }

    override fun getSortedNotes(): MutableList<String> {
        var notes: Array<String> = sortedKeys()
        var notesList: MutableList<String> = notes.toMutableList()
        return notesList
    }

    override fun getScaleTypes(): List<String> {
        return scales.scaleMap.keys.toList()
    }

    override fun setNote(note: String) {
        this.note = note
    }

    override fun getNote(): String {
        return this.note
    }

    override fun setScaleType(scaleType: String) {
        this.scaleType = scaleType
    }

    override fun getScaleType(): String {
        return this.scaleType
    }
}