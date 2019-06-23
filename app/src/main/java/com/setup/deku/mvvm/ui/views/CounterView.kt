package com.setup.deku.mvvm.ui.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.view.View
import com.setup.deku.mvvm.R
import com.setup.deku.mvvm.ui.viewmodels.CounterViewModel
import com.setup.deku.mvvm.ui.viewmodels.providerfactories.CounterViewModelFactory
import kotlinx.android.synthetic.main.counter.*

/**
 * Activity for our counter screen that inherits from our abstract MVVM activity class
 */
class CounterView : MVVMActivity() {
    private lateinit var viewModel: CounterViewModel

    /**
     * Load our viewmodel class from the relevant view model factory.
     * Ensures that the viewmodel loaded is tied to the lifecycle of this activity
     * Observes the counter livedata in the viewmodel
     */
    override fun initViewModel() {
        val factory = CounterViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(CounterViewModel::class.java)
        viewModel.count.observe(this, incrementObserver)
    }

    /**
     * Observer that listens for changes on the counter live data in the viewmodel
     */
    private var incrementObserver = Observer<Int> {
        counter_textview.text = it.toString()
    }

    /**
     * Sets up the initial state of the view for this activity
     */
    override fun setupView() {
        setContentView(R.layout.counter)

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