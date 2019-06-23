package com.setup.deku.mvvm.ui.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * ViewModel for the Counter activity, utilizing the lifecycle extension's view model for lifecycle awareness
 */
class CounterViewModel : ViewModel() {
    var count = MutableLiveData<Int>()

    init {
        count.value = 0
    }

    /**
     * Increments the value stored in the livedata counter by 10 if it is not null
     */
    fun incrementCounter() {
        count.value = count.value?.plus(10)
    }
}