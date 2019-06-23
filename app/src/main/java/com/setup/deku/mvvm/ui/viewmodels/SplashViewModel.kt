package com.setup.deku.mvvm.ui.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.setup.deku.mvvm.models.local.AppPreferencesHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Lifecycle-aware viewmodel for the Splash screen activity utilizing the AndroidViewModel for access to the application context
 */
class SplashViewModel(application: Application): AndroidViewModel(application) {
    private val preferences: AppPreferencesHelper = AppPreferencesHelper(application)
    private val disposables = CompositeDisposable()

    var intake = MutableLiveData<Int>()

    init {
        fetchInitialSettings()
    }

    /**
     * Fetches the initial settings from the shared preferences and wraps the result in an observable for the view to process
     */
    private fun fetchInitialSettings() {
        var disposable = Observable.just(preferences.getDailyIntake()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ result ->
            intake.value = result
        }, { error ->
            intake.value = -1
            Log.i("Startup", "Issue while fetching shared preferences value: ${error.message}")
        })
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