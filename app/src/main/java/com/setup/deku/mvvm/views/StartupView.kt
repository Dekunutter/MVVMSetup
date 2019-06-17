package com.setup.deku.mvvm.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.view.View
import com.setup.deku.mvvm.R
import com.setup.deku.mvvm.viewmodels.StartupViewModel
import com.setup.deku.mvvm.viewmodels.providerfactories.StartupViewModelFactory
import kotlinx.android.synthetic.main.startup.*

/**
 * Activity for our startup screen that inherits from our abstract MVVM activity class
 */
class StartupView : MVVMActivity() {
    private lateinit var viewModel: StartupViewModel

    /**
     * Load our viewmodel class from the relevant view model factory.
     * Ensures that the viewmodel loaded is tied to the lifecycle of this activity
     */
    override fun initViewModel() {
        val factory = StartupViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(StartupViewModel::class.java)
        viewModel.count.observe(this, incrementObserver)
    }

    /**
     * Oberserver that listens for changes on the counter live data in the viewmodel
     */
    private var incrementObserver = Observer<Int> {
        counter_textview.text = it.toString()
    }

    /**
     * Sets up the initial state of the view for this activity
     */
    override fun setupView() {
        setContentView(R.layout.startup)

        increment_button.setOnClickListener(incrementClickListener)

        counter_textview.text = viewModel.count.toString()
    }

    /**
     * OnClickListener that processes click events on the increment button by incrementing the value in the view model
     */
    private var incrementClickListener = View.OnClickListener {
        viewModel.incrementCounter()
    }
}