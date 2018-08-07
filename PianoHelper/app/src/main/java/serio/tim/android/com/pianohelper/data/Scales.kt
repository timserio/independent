package serio.tim.android.com.pianohelper.data

class Scales {
    var scaleMap = mapOf(
            "Aeolian" to arrayOf(Interval.majorSecond, Interval.minorThird, Interval.perfectFourth, Interval.perfectFifth, Interval.minorSixth, Interval.minorSeventh),
            "Dorian" to arrayOf(Interval.majorSecond, Interval.minorThird, Interval.perfectFourth, Interval.perfectFifth, Interval.majorSixth, Interval.minorSeventh),
            "Ionian" to arrayOf(Interval.majorSecond, Interval.majorThird, Interval.perfectFourth, Interval.perfectFifth, Interval.majorSixth, Interval.majorSeventh),
            "Locrian" to arrayOf(Interval.minorSecond, Interval.minorThird, Interval.perfectFourth, Interval.augmentedFourth, Interval.minorSixth, Interval.minorSeventh),
            "Lydian" to arrayOf(Interval.majorSecond, Interval.majorThird, Interval.augmentedFourth, Interval.perfectFifth, Interval.majorSixth, Interval.majorSeventh),
            "Mixolydian" to arrayOf(Interval.majorSecond, Interval.majorThird, Interval.perfectFourth, Interval.perfectFifth, Interval.majorSixth, Interval.minorSeventh),
            "Phrygian" to arrayOf(Interval.minorSecond, Interval.minorThird, Interval.perfectFourth, Interval.perfectFifth, Interval.minorSixth, Interval.minorSeventh),
            "Pentatonic" to arrayOf(Interval.majorSecond, Interval.majorThird, Interval.perfectFifth, Interval.majorSixth),
            "Minor Pentatonic" to arrayOf(Interval.minorThird, Interval.perfectFourth, Interval.perfectFifth, Interval.minorSeventh),
            "Harmonic Minor" to arrayOf(Interval.majorSecond, Interval.minorThird, Interval.perfectFourth, Interval.perfectFifth, Interval.minorSixth, Interval.majorSeventh),
            "Melodic Minor" to arrayOf(Interval.majorSecond, Interval.minorThird, Interval.perfectFourth, Interval.perfectFifth, Interval.majorSixth, Interval.majorSeventh),
            "Whole Tone" to arrayOf(Interval.majorSecond, Interval.majorThird, Interval.augmentedFourth, Interval.minorSixth, Interval.minorSeventh),
            "Augmented" to arrayOf(Interval.minorThird, Interval.majorThird, Interval.perfectFifth, Interval.minorSixth, Interval.majorSeventh),
            "Diminished (Whole Half)" to arrayOf(Interval.majorSecond, Interval.minorThird, Interval.perfectFourth, Interval.augmentedFourth, Interval.minorSixth, Interval.majorSixth, Interval.majorSeventh),
            "Diminished (Half Whole)" to arrayOf(Interval.minorSecond, Interval.minorThird, Interval.majorThird, Interval.augmentedFourth, Interval.perfectFifth, Interval.majorSixth, Interval.minorSeventh)
    )
}