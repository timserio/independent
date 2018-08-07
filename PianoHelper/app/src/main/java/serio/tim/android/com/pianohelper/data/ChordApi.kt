package serio.tim.android.com.pianohelper.data

import serio.tim.android.com.pianohelper.chord.ChordActivityContract

class ChordApi : ChordActivityContract.ChordApi {
    private var chords: MutableList<Int> = mutableListOf()

    override fun addInterval(interval: Int) {
        chords.add(interval)
    }

    override fun getChordArray(): IntArray {
        return chords.toIntArray()
    }

    override fun clear() {
        chords.clear()
    }
}