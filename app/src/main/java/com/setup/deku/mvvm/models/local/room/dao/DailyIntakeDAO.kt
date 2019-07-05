package com.setup.deku.mvvm.models.local.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.setup.deku.mvvm.models.local.room.entities.DailyIntake

/**
 * Data access object for tracking daily calorie intakes
 * Provides Room queries for accessing and modifying the Room database
 */
@Dao
interface DailyIntakeDAO {
    @Query("SELECT * FROM calories WHERE date = :date")
    fun getIntakeForDate(date: String): List<DailyIntake>

    @Insert
    fun addIntakeForDate(dailyIntake: DailyIntake)

    @Query("UPDATE calories SET intake = :intake WHERE date = :date")
    fun updateIntakeForDate(date: String, intake: Int?)
}