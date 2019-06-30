package com.setup.deku.mvvm.ui.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.setup.deku.mvvm.models.local.AppPreferencesHelper
import com.setup.deku.mvvm.models.local.OptionsModel

/**
 * ViewModel for the Options activity
 */
class OptionsViewModel(application: Application) : AndroidViewModel(application) {
    private val preferences: AppPreferencesHelper = AppPreferencesHelper(application)

    var validation = MutableLiveData<Boolean>()

    /**
     * Runs validation checks on the input form and updates the livedata boolean to match the result
     */
    fun validateInputs(formModel: OptionsModel) {
        validation.value = (formModel.isIntakeValid() && formModel.isUsernameValid())
    }
}