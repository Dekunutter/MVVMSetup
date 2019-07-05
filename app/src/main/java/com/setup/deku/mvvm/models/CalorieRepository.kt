package com.setup.deku.mvvm.models

import android.app.Application
import android.arch.persistence.room.Room
import com.setup.deku.mvvm.models.local.room.CalorieDatabase
import com.setup.deku.mvvm.models.local.room.dao.DailyIntakeDAO
import com.setup.deku.mvvm.models.local.room.entities.DailyIntake
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.*

/**
 * Repository class that handles all access to the calorie database and performs all functions necessary for interacting with it
 */
class CalorieRepository(application: Application) {
    private var intakeAccess: DailyIntakeDAO

    init {
        val database = Room.databaseBuilder(application, CalorieDatabase::class.java, "dailyCalories").build()
        intakeAccess = database.dailyIntakeDao()
    }

    /**
     * Gets the calorie intake value for the specified date
     * @param currentDate Date that we want to get the intake value for
     * @return Observable of integer values that are our intake values found from the database for the given date
     */
    fun getIntake(currentDate: Date?): Observable<Int> {
        return Observable.fromCallable {
            intakeAccess.getIntakeForDate(CalorieDatabase.formatDate(currentDate))
        }.filter { results ->
            results.isNotEmpty()
        }.flatMap {results ->
            Observable.just(results[0].intake)
        }
    }

    /**
     * Changes the calorie intake value for the specified date in the database
     * Performs an update if a value already exists for the specified date
     * Otherwise, performs an insert
     * @param date The date we want change the calorie intake for
     * @param intake the new calorie intake value we want saved to the database
     * @return Completable that returns whether the operation was a success or not
     */
    fun changeIntake(date: Date?, intake: Int?): Completable {
        val formattedDate = CalorieDatabase.formatDate(date)

        return Completable.fromCallable {
            val results = intakeAccess.getIntakeForDate(formattedDate)
            var dailyIntake =
                DailyIntake(date = formattedDate, intake = intake)
            if (results != null && results.isNotEmpty()) {
                intakeAccess.updateIntakeForDate(formattedDate, intake)
            } else {
                intakeAccess.addIntakeForDate(dailyIntake)
            }
        }
    }
}