package com.setup.deku.mvvm.ui.viewmodels

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.setup.deku.mvvm.models.CalorieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * ViewModel for the Counter activity, utilizing the lifecycle extension's view model for lifecycle awareness
 */
class CounterViewModel(application: Application) : ViewModel() {
    private val calorieRepo = CalorieRepository(application)
    private val disposables = CompositeDisposable()

    var currentDate = MutableLiveData<Date>()
    var count = MutableLiveData<Int>()


    init {
        currentDate.value = Calendar.getInstance().time

        val disposable = calorieRepo.getIntake(currentDate.value).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            count.postValue(it)
        }
        disposables.add(disposable)
    }

    /**
     * Increments the value stored in the livedata counter by 10 if it is not null
     * Updates the backend database with the new value
     */
    fun incrementCounter() {
        count.value = count.value?.plus(10)

        val disposable = calorieRepo.changeIntake(currentDate.value, count.value).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
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