package com.setup.deku.mvvm.ui.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.view.View
import android.widget.Toast
import com.setup.deku.mvvm.R
import com.setup.deku.mvvm.models.local.OptionsModel
import com.setup.deku.mvvm.ui.viewmodels.OptionsViewModel
import com.setup.deku.mvvm.ui.viewmodels.providerfactories.OptionsViewModelFactory
import kotlinx.android.synthetic.main.options.*

/**
 * Activity for the options screen
 */
class OptionsView : MVVMActivity() {
    private lateinit var viewModel: OptionsViewModel

    /**
     * Load our viewmodel class from the relevant view model factory
     * Ensures that the viewmodel loaded is tied to the lifecycle of this activity
     * Observes the form validation result livedata in the viewmodel
     */
    override fun initViewModel() {
        var factory = OptionsViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, factory).get(OptionsViewModel::class.java)
        viewModel.validation.observe(this, validationObserver)
    }

    /**
     * Observer that listens for changes on the validation result live data in the viewmodel
     */
    private var validationObserver = Observer<Boolean> {
        if(it == true) {
            finish()
        } else {
            Toast.makeText(this, R.string.invalid_input, Toast.LENGTH_SHORT)
        }
    }

    /**
     * Sets up the initial state of the view for this activity
     */
    override fun setupView() {
        setContentView(R.layout.options)

        save_button.setOnClickListener(saveClickListener)
    }

    /**
     * OnClickListener that processes click events on the save button by performing validation on the input form
     */
    private var saveClickListener = View.OnClickListener {
        val formModel = OptionsModel(daily_edittext.text.toString(), username_edittext.text.toString())
        viewModel.validateInputs(formModel)
    }
}