package com.setup.deku.mvvm.models.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.setup.deku.mvvm.models.local.dao.DailyIntakeDAO
import com.setup.deku.mvvm.models.local.entities.DailyIntake
import java.text.SimpleDateFormat
import java.util.*

/**
 * Provides access to the calorie Room database
 */
@Database(entities = [DailyIntake::class], version = 1)
abstract class CalorieDatabase : RoomDatabase() {
    abstract fun dailyIntakeDao(): DailyIntakeDAO

    /**
     * Static utility functions for database access
     */
    companion object DatabaseUtils {
        /**
         * Formats the given date into the formatted date string that is stored and recognised by the database
         * @param date The date we want to convert into a database-ready format
         * @return The formatted string of the given date
         */
        fun formatDate(date: Date?): String {
            val format = SimpleDateFormat("dd-MM-yyyy")
            return format.format(date)
        }
    }
}