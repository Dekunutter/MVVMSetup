package com.setup.deku.mvvm.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * ViewModel for the Startup activity, utilizing the lifecycle extension's view model for lifecycle awareness
 */
class StartupViewModel : ViewModel() {
    var count = MutableLiveData<Int>()

    init {
        count.value = 0
    }

    /**
     * Increments the value stored in the livedata counter if it is not null
     */
    fun incrementCounter() {
        count.value = count.value?.inc()
    }
}