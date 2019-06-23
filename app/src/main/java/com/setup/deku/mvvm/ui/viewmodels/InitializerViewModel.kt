package com.setup.deku.mvvm.ui.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.setup.deku.mvvm.models.local.AppPreferencesHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Lifecycle-aware viewmodel for the Initializer activity utilizing the AndroidViewModel class for access to the application context
 */
class InitializerViewModel(application: Application): AndroidViewModel(application) {
    private val preferences: AppPreferencesHelper = AppPreferencesHelper(application)
    private val disposables = CompositeDisposable()

    var writeStatus = MutableLiveData<Boolean>()

    /**
     * Saves the given value to the shared preferences which reports back whether the write call was successful
     * @param input String taken directly from the input field in the view. We want this to be an integer
     */
    fun saveIntakeValue(input: String) {
        val num = input.toIntOrNull()
        if(num == null) {
            writeStatus.value = false
            return
        }

        var disposable = Observable.just(preferences.setDailyIntake(num)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe { result ->
            writeStatus.value = result
        }
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