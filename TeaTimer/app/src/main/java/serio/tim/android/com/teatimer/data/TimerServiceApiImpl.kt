package serio.tim.android.com.teatimer.data

import android.content.ContentValues

class TimerServiceApiImpl : TimerServiceApi {

    private val teas = mutableMapOf<String, Int>()
    private val waterTemps = mutableMapOf<String, Int>()

    override fun saveTimer(timer: Timer, dbHelper: TimerDatabase) {
        val teaName = timer.teaName
        val timerLength = timer.timerLength
        val waterTemp = timer.waterTemp
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_TEATYPE, teaName)
            put(TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_TIMENUM, timerLength)
            put(TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_WATERTEMP, waterTemp)
        }

        val newRowId = db?.insert(TimerDatabase.FeedReaderContract.FeedEntry.TABLE_NAME, null, values)
    }

    override fun updateTimer(timer: Timer, dbHelper: TimerDatabase) {
        val teaName = timer.teaName
        val timerLength = timer.timerLength
        val waterTemp = timer.waterTemp
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_TEATYPE, teaName)
            put(TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_TIMENUM, timerLength)
            put(TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_WATERTEMP, waterTemp)
        }

        val selection = "${TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_TEATYPE} LIKE ?"
        val selectionArgs = arrayOf(teaName)

        val newRowId = db?.update(TimerDatabase.FeedReaderContract.FeedEntry.TABLE_NAME, values, selection, selectionArgs)
    }

    override fun deleteTimer(timer: Timer, dbHelper: TimerDatabase) {
        val teaName = timer.teaName

        val db = dbHelper.readableDatabase

        val selection = "${TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_TEATYPE} LIKE ?"
        val selectionArgs = arrayOf(teaName)
        val deletedRows = db.delete(TimerDatabase.FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs)
    }

    override fun setUpTimersDropdown(dbHelper: TimerDatabase): MutableMap<String, Int> {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_TEATYPE, TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_TIMENUM, TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_WATERTEMP)

        val sortOrder = "${TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_TEATYPE}"

        val cursor = db.query(
                TimerDatabase.FeedReaderContract.FeedEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        )

        with(cursor) {
            while (moveToNext()) {

                val teaText = getString(getColumnIndexOrThrow(TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_TEATYPE))
                val timeNum = getInt(getColumnIndexOrThrow(TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_TIMENUM))

                teas[teaText] = timeNum
            }
        }

        return teas
    }

    override fun setUpWaterTemp(dbHelper: TimerDatabase): MutableMap<String, Int> {

        val db = dbHelper.readableDatabase

        val projection = arrayOf(TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_TEATYPE, TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_TIMENUM, TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_WATERTEMP)

        val sortOrder = "${TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_TEATYPE}"

        val cursor = db.query(
                TimerDatabase.FeedReaderContract.FeedEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        )


        with(cursor) {
            while (moveToNext()) {

                val teaText = getString(getColumnIndexOrThrow(TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_TEATYPE))

                val waterTemp = getInt(getColumnIndexOrThrow(TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_WATERTEMP))

                waterTemps[teaText] = waterTemp
            }
        }

        return waterTemps
    }

    override fun teaNameExists(teaName: String, dbHelper: TimerDatabase): Boolean {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_TEATYPE)

        val sortOrder = "${TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_TEATYPE}"

        val selection = "${TimerDatabase.FeedReaderContract.FeedEntry.COLUMN_NAME_TEATYPE}=?"

        val selectionArgs = arrayOf(teaName)

        val cursor = db.query(
                TimerDatabase.FeedReaderContract.FeedEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )

        return cursor.count > 0
    }
}