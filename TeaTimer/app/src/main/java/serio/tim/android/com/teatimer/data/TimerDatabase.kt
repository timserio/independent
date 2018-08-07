package serio.tim.android.com.teatimer.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class TimerDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    object FeedReaderContract {
        // Table contents are grouped together in an anonymous object.
        object FeedEntry : BaseColumns {
            const val TABLE_NAME = "teas"
            const val COLUMN_NAME_TEATYPE = "teaType"
            const val COLUMN_NAME_TIMENUM = "timeNum"
            const val COLUMN_NAME_WATERTEMP = "waterTemp"
        }
    }

    private val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${FeedReaderContract.FeedEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${FeedReaderContract.FeedEntry.COLUMN_NAME_TEATYPE} VARCHAR(30)," +
                    "${FeedReaderContract.FeedEntry.COLUMN_NAME_TIMENUM} INTEGER," +
                    "${FeedReaderContract.FeedEntry.COLUMN_NAME_WATERTEMP} INTEGER);"

    private fun putInDB(db: SQLiteDatabase?, teaType: String, timeNum: Int, waterTemp: Int) {

        val values = ContentValues().apply {
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_TEATYPE, teaType)
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_TIMENUM, timeNum)
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_WATERTEMP, waterTemp)
        }

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db?.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(SQL_CREATE_ENTRIES)
        putInDB(p0, "Green Tea",180, 176)
        putInDB(p0, "Sencha 1", 60, 170)
        putInDB(p0, "Sencha 2", 30, 175)
        putInDB(p0, "Sencha 3", 45, 180)
        putInDB(p0, "Sencha 4", 90, 185)
        putInDB(p0, "Sencha 5", 180, 190)
        putInDB(p0, "Black Tea", 180, 205)
        putInDB(p0, "Pu Erh Tea", 180, 205)
        putInDB(p0, "Yerba Mate", 180, 212)
        putInDB(p0, "White Tea",  60, 185)
        putInDB(p0, "Rooibos", 300, 212)
        putInDB(p0, "Oolong Tea", 180, 185)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }



    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FeedReader.db"
    }
}