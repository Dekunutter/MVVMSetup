package com.setup.deku.mvvm.ui.viewmodels

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.setup.deku.mvvm.models.local.CalorieDatabaseHelper

/**
 * ViewModel for the Counter activity, utilizing the lifecycle extension's view model for lifecycle awareness
 */
class CounterViewModel(application: Application) : ViewModel() {
    private val calorieDatabase: CalorieDatabaseHelper = CalorieDatabaseHelper(application, null)

    var count = MutableLiveData<Int>()

    init {
        count.value = calorieDatabase.getTodaysIntake()
    }

    /**
     * Increments the value stored in the livedata counter by 10 if it is not null
     * Updates the backend database with the new value
     */
    fun incrementCounter() {
        count.value = count.value?.plus(10)
        calorieDatabase.addIntake(count.value)
    }
}