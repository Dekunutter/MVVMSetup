package com.setup.deku.mvvm.ui.viewmodels

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.persistence.room.Room
import com.setup.deku.mvvm.models.local.CalorieDatabase
import com.setup.deku.mvvm.models.local.dao.DailyIntakeDAO
import com.setup.deku.mvvm.models.local.entities.DailyIntake
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * ViewModel for the Counter activity, utilizing the lifecycle extension's view model for lifecycle awareness
 */
class CounterViewModel(application: Application) : ViewModel() {
    private val calorieDatabase = Room.databaseBuilder(application, CalorieDatabase::class.java, "dailyCalories").build()
    private val intakeAccess: DailyIntakeDAO

    private val disposables = CompositeDisposable()

    var currentDate = MutableLiveData<Date>()
    var count = MutableLiveData<Int>()


    init {
        intakeAccess = calorieDatabase.dailyIntakeDao()

        currentDate.value = Calendar.getInstance().time

        val disposable = Observable.fromCallable {
            val date = currentDate.value
            intakeAccess.getIntakeForDate(CalorieDatabase.formatDate(date))
        }.doOnNext { results ->
            if (results != null && results.isNotEmpty()) {
                count.postValue(results[0].intake)
            } else {
                count.postValue(0)
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
        disposables.add(disposable)
    }

    /**
     * Increments the value stored in the livedata counter by 10 if it is not null
     * Updates the backend database with the new value
     */
    fun incrementCounter() {
        count.value = count.value?.plus(10)

        val date = currentDate.value
        val formattedDate = CalorieDatabase.formatDate(date)
        val disposable = Observable.fromCallable {
            intakeAccess.getIntakeForDate(formattedDate)
        }.doOnNext { results ->
            var dailyIntake = DailyIntake(date = formattedDate, intake = count.value)
            if (results != null && results.isNotEmpty()) {
                intakeAccess.updateIntakeForDate(formattedDate, count.value)
            } else {
                intakeAccess.addIntakeForDate(dailyIntake)
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
        disposables.add(disposable)
    }

    /**
     * Clear out all disposables created by observables when the viewmodel is cleared
     */
    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}