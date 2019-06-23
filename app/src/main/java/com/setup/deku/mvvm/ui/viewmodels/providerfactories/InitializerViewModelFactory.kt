package com.setup.deku.mvvm.ui.viewmodels.providerfactories

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.setup.deku.mvvm.ui.viewmodels.InitializerViewModel

/**
 * Implementation of the ViewModelProvider factory for instantiating the Initializer view models
 */
class InitializerViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    /**
     * Creates an InitializerViewModel
     * @param modelClass the generic class that we want to instantiate as a viewmodel
     * @return Generic viewmodel for the initializer activity
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return InitializerViewModel(application) as T
    }
}