package com.setup.deku.mvvm.ui.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import com.setup.deku.mvvm.ui.navigators.SplashNavigator
import com.setup.deku.mvvm.ui.viewmodels.SplashViewModel
import com.setup.deku.mvvm.ui.viewmodels.providerfactories.SplashViewModelFactory

/**
 * Activity for our splash screen
 */
class SplashView: MVVMActivity(), SplashNavigator {
    private lateinit var viewModel: SplashViewModel

    /**
     * Load our viewmodel class from the relevant view model factory
     * Ensures that the viewmodel loaded is tied to the lifecycle of this activity
     * Observes the intake livedata in the viewmodel
     */
    override fun initViewModel() {
        val factory = SplashViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, factory).get(SplashViewModel::class.java)
        viewModel.intake.observe(this, intakeObserver)
    }

    /**
     * Sets up the initial state of the view for this activity
     * Unused as the activity is just rendering a theme while the application loads
     */
    override fun setupView() {
    }

    /**
     * Observer that listens for changes on the intake live data in the viewmodel
     * Routes the splash screen to the appropriate activity depending on the observed value
     */
    private var intakeObserver = Observer<Int> {
        routeToAppropriatePage(it)
    }

    /**
     * Navigate to the correct activity depending on the given value and kill this activity
     * @param value The integer value that determines if we have a daily calorie intake specified
     */
    private fun routeToAppropriatePage(value: Int?) {
        if(value == -1) {
            openInitializerView()
        } else {
            openCounterView()
        }
        finish()
    }

    /**
     * Starts the initializer activity
     */
    override fun openInitializerView() {
        startActivity(Intent(this, InitializerView::class.java))
    }

    /**
     * Starts the counter activity
     */
    override fun openCounterView() {
        startActivity(Intent(this, CounterView::class.java))
    }
}