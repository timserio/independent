package serio.tim.android.com.pianohelper.data

class Chords {
    var chordMap = mapOf(
            "Major" to arrayOf(Interval.majorThird, Interval.perfectFifth),
            "Major 7" to arrayOf(Interval.majorThird, Interval.perfectFifth, Interval.majorSeventh),
            "Major 6" to arrayOf(Interval.majorThird, Interval.perfectFifth, Interval.majorSixth),
            "Dominant 7" to arrayOf(Interval.majorThird, Interval.perfectFifth, Interval.minorSeventh),
            "Minor" to arrayOf(Interval.minorThird, Interval.perfectFifth),
            "Minor 7" to arrayOf(Interval.minorThird, Interval.perfectFifth, Interval.minorSeventh),
            "Minor 6" to arrayOf(Interval.minorThird, Interval.perfectFifth, Interval.majorSixth),
            "Diminished" to arrayOf(Interval.minorThird, Interval.augmentedFourth),
            "Half-Diminished 7" to arrayOf(Interval.minorThird, Interval.augmentedFourth, Interval.minorSeventh),
            "Diminished 7" to arrayOf(Interval.minorThird, Interval.augmentedFourth, Interval.majorSixth),
            "Augmented" to arrayOf(Interval.majorThird, Interval.minorSixth),
            "Augmented 7" to arrayOf(Interval.majorThird, Interval.minorSixth, Interval.minorSeventh)
    )
}