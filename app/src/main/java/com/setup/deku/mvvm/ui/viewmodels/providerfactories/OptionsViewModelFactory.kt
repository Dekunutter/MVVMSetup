package com.setup.deku.mvvm.ui.viewmodels.providerfactories

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.setup.deku.mvvm.ui.viewmodels.OptionsViewModel

/**
 * Implementation of the ViewModelProvider factory for instantiating the Options view models
 */
class OptionsViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    /**
     * Creates an OptionsViewModel
     * @param modelClass the generic class that we want to instantiate as a viewmodel
     * @return Generic viewmodel for the options activity
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OptionsViewModel(application) as T
    }
}