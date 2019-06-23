package com.setup.deku.mvvm.ui.viewmodels.providerfactories

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.setup.deku.mvvm.ui.viewmodels.SplashViewModel

/**
 * Implementation of the ViewModelPorivder factory for instantiating Splash screen view models
 */
class SplashViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    /**
     * Creates a SplashViewModel
     * @param modelClass The generic class that we want to instantiate as a viewmodel
     * @return Generic viewmodel for the counter activity
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SplashViewModel(application) as T
    }
}