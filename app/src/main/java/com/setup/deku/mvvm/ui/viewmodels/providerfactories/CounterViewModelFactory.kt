package com.setup.deku.mvvm.ui.viewmodels.providerfactories

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.setup.deku.mvvm.ui.viewmodels.CounterViewModel

/**
 * Implementation of the ViewModelProvider factory for instantiating Counter view models that may need more than just default constructors
 */
class CounterViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    /**
     * Creates a CounterViewModel
     * @param modelClass The generic class that we want to instantiate as a viewmodel
     * @return Generic viewmodel for the counter activity
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CounterViewModel(application) as T
    }
}