package com.setup.deku.mvvm.models.local.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import androidx.annotation.NonNull

/**
 * Data class for our calories table in the database
 * Tracks the user's daily calorie count
 */
@Entity(tableName = "calories")
data class DailyIntake(
    @PrimaryKey(autoGenerate = true) @NonNull @ColumnInfo(name = "id") var id: Long = 0L,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "intake") var intake: Int?
)