package com.setup.deku.mvvm.ui.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.setup.deku.mvvm.R
import com.setup.deku.mvvm.ui.navigators.InitializerNavigator
import com.setup.deku.mvvm.ui.viewmodels.InitializerViewModel
import com.setup.deku.mvvm.ui.viewmodels.providerfactories.InitializerViewModelFactory
import kotlinx.android.synthetic.main.initializer.*

/**
 * Activity for our initializer screen
 */
class InitializerView: MVVMActivity(), InitializerNavigator {
    private lateinit var viewModel: InitializerViewModel

    /**
     * Load our viewmodel class from the relevant view model factory
     * Ensures that the viewmodel loaded is tied to the lifecycle of this activity
     * Observes the write status livedata in the viewmodel
     */
    override fun initViewModel() {
        val factory = InitializerViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, factory).get(InitializerViewModel::class.java)
        viewModel.writeStatus.observe(this, writeStatusObserver)
    }

    /**
     * Observer that listens for changes on the write status live data in the viewmodel
     * If the write operation was successful and returns true, then we can launch the counter activity
     * Otherwise we should inform the user that they provided an invalid input
     */
    private var writeStatusObserver = Observer<Boolean> {
        if(it == true) {
            openCounterView()
        } else {
            Toast.makeText(this, R.string.invalid_input, Toast.LENGTH_SHORT)
        }
    }

    /**
     * Sets up the initial state of the view for this activity
     */
    override fun setupView() {
        setContentView(R.layout.initializer)

        intake_button.setOnClickListener(intakeClickListener)
    }

    /**
     * OnClickListener that processes click events on the intake button by trying to save the value in the intake edittext to the shared preferences
     */
    private var intakeClickListener = View.OnClickListener {
        viewModel.saveIntakeValue(intake_edittext.text.toString())
    }

    /**
     * Starts the counter activity
     */
    override fun openCounterView() {
        startActivity(Intent(this, CounterView::class.java))
    }
}