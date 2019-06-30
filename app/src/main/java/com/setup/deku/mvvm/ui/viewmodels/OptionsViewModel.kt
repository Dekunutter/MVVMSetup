package com.setup.deku.mvvm.ui.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.setup.deku.mvvm.models.local.AppPreferencesHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

/**
 * ViewModel for the Options activity
 */
class OptionsViewModel(application: Application) : AndroidViewModel(application) {
    private val preferences: AppPreferencesHelper = AppPreferencesHelper(application)
    private val disposables = CompositeDisposable()

    var intake = MutableLiveData<Int>()
    var username = MutableLiveData<String>()
    var writeStatus = MutableLiveData<Boolean>()

    /**
     * Loads the daily calorie intake value from the shared preferences
     */
    fun loadDailyIntakeValue() {
        var disposable = Observable.just(preferences.getDailyIntake()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe { result ->
            intake.value = result
        }
        disposables.add(disposable)
    }

    /**
     * Loads the username from the shared preferences
     */
    fun loadUsernameValue() {
        var disposable = Observable.just(preferences.getUsername()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe { result ->
            username.value = result
        }
        disposables.add(disposable)
    }

    /**
     * Validates the value to ensure that it is an integer
     * @param intake the String that represents our daily calorie intake
     * @return whether the given string is a valid integer or not
     */
    fun validateIntake(intake: String): Boolean {
        intake.toIntOrNull() ?: return false
        return true
    }

    /**
     * Validates the username to ensure that it is not empty
     * @param username the String that represents the username
     * @return whether the given string is empty or not
     */
    fun validateUsername(username: String): Boolean {
        if(username == null || username.isEmpty()) {
            return false
        }
        return true
    }

    /**
     * Saves the given inputs to the shared preferences
     * Updates the writestatus livedata with the result of the writing process
     */
    fun saveInputs(intake: String, username: String) {
        var intakeObserver = Observable.just(preferences.setDailyIntake(intake.toInt())).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        var usernameObserver = Observable.just(preferences.setUsername(username)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        var disposable = Observable.zip(intakeObserver, usernameObserver, BiFunction<Boolean, Boolean, Boolean> { intake, username ->
            intake && username
        }).subscribe { result ->
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