package com.setup.deku.mvvm.viewmodels.providerfactories

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.setup.deku.mvvm.viewmodels.StartupViewModel

/**
 * Implementation of the ViewModelProvider factory for instantiating Startup view models that may need more than just default constructors
 */
class StartupViewModelFactory : ViewModelProvider.Factory {
    /**
     * Creates a StartupViewModel
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StartupViewModel() as T
    }
}