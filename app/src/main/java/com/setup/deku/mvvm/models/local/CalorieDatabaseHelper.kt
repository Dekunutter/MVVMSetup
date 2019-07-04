package com.setup.deku.mvvm.models.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.*

const val DATABASE_NAME = "dailyCalories.db"
const val DATABASE_VERSION = 1
const val TABLE_NAME = "calories"
const val COLUMN_ID = "id"
const val COLUMN_DATE = "date"
const val COLUMN_INTAKE = "intake"

/**
 * SQLite access for the calorie tracking database
 */
class CalorieDatabaseHelper(context: Context, factory: SQLiteDatabase.CursorFactory?): SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    /**
     * Called whenever the database is being created for the first time
     * @param database The database we are creating our table in
     */
    override fun onCreate(database: SQLiteDatabase?) {
        val createTableStatement = ("CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COLUMN_DATE TEXT,$COLUMN_INTAKE INTEGER)")
        database?.execSQL(createTableStatement)
    }

    /**
     * Called whenever the database needs to be upgraded by dropping the current tables and recreating them
     * @param database The database we are upgrading
     * @param previousVersion The version the database was in
     * @param newVersion The version the database will be upgraded to
     */
    override fun onUpgrade(database: SQLiteDatabase?, previousVersion: Int, newVersion: Int) {
        val updateStatement = ("DROP TABLE IF EXISTS $TABLE_NAME")
        database?.execSQL(updateStatement)
        onCreate(database)
    }

    /**
     * Called whenever the database needs to be upgraded.
     * Runs the same operation as the database upgrading would
     * @param database The database we are upgrading
     * @param oldVersion The version the database was in
     * @param newVersion The version the database will be downgraded to
     */
    override fun onDowngrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(database, oldVersion, newVersion)
    }

    /**
     * Adds the specified calorie intake to the database for today's date
     * Creates a new column for today if one does not exist, otherwise it will update the intake value of the current date's column
     * @param intake The current value of our calory intake
     */
    fun addIntake(intake: Int?) {
        val date = getCurrentDate()

        val values = ContentValues()
        values.put(COLUMN_INTAKE, intake)
        values.put(COLUMN_DATE, date)

        val cursor = getIntake(date)
        if(cursor != null && cursor.count > 0) {
            writableDatabase.update(TABLE_NAME, values, "$COLUMN_DATE=?", arrayOf(date))
        } else {
            writableDatabase.insert(TABLE_NAME, null, values)
        }
        writableDatabase.close()
    }

    /**
     * Gets the calorie intake for today from the database
     * @return Calorie intake for today's date as fetched from the database, or 0 if there is no entry for today in the database
     */
    fun getTodaysIntake(): Int {
        val date = getCurrentDate()

        val cursor = getIntake(date)
        if(cursor != null && cursor.count > 0) {
            cursor.moveToNext()
            return cursor.getInt(cursor.getColumnIndex(COLUMN_INTAKE))
        }
        return 0
    }

    /**
     * Gets the calorie intake for the specified date
     * @param date Formatted date that we want to fetch the calorie information for
     * @return Cursor pointing towards our results for this query
     */
    private fun getIntake(date: String): Cursor? {
        return readableDatabase.query(TABLE_NAME, arrayOf(COLUMN_DATE, COLUMN_INTAKE), "$COLUMN_DATE=?", arrayOf(date), null, null, null)
    }

    /**
     * Gets the current date from the device's calendar
     * Formats the date in dd-MM-yyyy for writing and reading to the database
     * @return Formatted string for today's date
     */
    private fun getCurrentDate(): String {
        val format = SimpleDateFormat("dd-MM-yyyy")
        return format.format(Calendar.getInstance().time)
    }
}