package com.setup.deku.mvvm.ui.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.view.View
import android.widget.Toast
import com.jakewharton.rxbinding3.widget.textChangeEvents
import com.setup.deku.mvvm.R
import com.setup.deku.mvvm.ui.viewmodels.OptionsViewModel
import com.setup.deku.mvvm.ui.viewmodels.providerfactories.OptionsViewModelFactory
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.options.*

/**
 * Activity for the options screen
 */
class OptionsView : MVVMActivity() {
    private lateinit var viewModel: OptionsViewModel

    override fun onStart() {
        super.onStart()
        populateOptions()
    }

    /**
     * Load our viewmodel class from the relevant view model factory
     * Ensures that the viewmodel loaded is tied to the lifecycle of this activity
     * Observes the form validation result livedata in the viewmodel
     */
    override fun initViewModel() {
        var factory = OptionsViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, factory).get(OptionsViewModel::class.java)
        viewModel.intake.observe(this, intakeLoadingObserver)
        viewModel.username.observe(this, usernameLoadingObserver)
        viewModel.writeStatus.observe(this, writeStatusObserver)
    }

    /**
     * Observer that listens on results for the daily calorie intake value loaded from shared preferences
     * Updates the daily intake edittext with the found value
     */
    private var intakeLoadingObserver = Observer<Int> {
        daily_edittext.setText(it?.toString())
    }

    /**
     * Observer that listens on the results for the username value loaded from shared preferences
     * Updates the username edittext with the found value
     */
    private var usernameLoadingObserver = Observer<String> {
        username_edittext.setText(it)
    }

    /**
     * Observer that listens for changes on the write status live data in the viewmodel
     * If the write operation was successful and returns true, then we can launch the counter activity
     * Otherwise we should inform the user that they provided an invalid input
     */
    private var writeStatusObserver = Observer<Boolean> {
        if(it == true) {
            finish()
        } else {
            Toast.makeText(this, R.string.failed_to_write_preferences, Toast.LENGTH_SHORT)
        }
    }

    /**
     * Sets up the initial state of the view for this activity
     * Binds observables to listen to changes in the UI elements
     * Observes the validation state of input fields to ensure that the save button is only enabled when the fields are all validated
     */
    override fun setupView() {
        setContentView(R.layout.options)

        val intakeObservable = daily_edittext.textChangeEvents().skipInitialValue().map {
            it.text.toString()
        }
        val usernameObservable = username_edittext.textChangeEvents().skipInitialValue().map {
            it.text.toString()
        }
        val checker: Observable<Boolean> = Observable.combineLatest(intakeObservable, usernameObservable, BiFunction<String, String, Boolean> { intake, username ->
            viewModel.validateIntake(intake) && viewModel.validateUsername(username)
        })
        checker.subscribe {
            save_button.isEnabled = it
        }

        save_button.setOnClickListener(saveClickListener)
    }

    /**
     * Populates the Options activity input fields with the values found in the shared preferences
     */
    private fun populateOptions() {
        viewModel.loadDailyIntakeValue()
        viewModel.loadUsernameValue()
    }

    /**
     * OnClickListener that processes click events on the save button by saving input values to the shared preferences
     */
    private var saveClickListener = View.OnClickListener {
        viewModel.saveInputs(daily_edittext.text.toString(), username_edittext.text.toString())
    }
}