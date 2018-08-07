package serio.tim.android.com.pianohelper.data

import serio.tim.android.com.pianohelper.scale.ScaleActivityContract

class ScaleApi : ScaleActivityContract.ScaleApi {
    private var scales: MutableList<Int> = mutableListOf()

    override fun addInterval(interval: Int) {
        scales.add(interval)
    }

    override fun getScaleArray(): IntArray {
        return scales.toIntArray()
    }

    override fun clear() {
        scales.clear()
    }
}