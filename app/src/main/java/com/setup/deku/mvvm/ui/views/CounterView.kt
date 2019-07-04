package com.setup.deku.mvvm.ui.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.setup.deku.mvvm.R
import com.setup.deku.mvvm.ui.navigators.CounterNavigator
import com.setup.deku.mvvm.ui.viewmodels.CounterViewModel
import com.setup.deku.mvvm.ui.viewmodels.providerfactories.CounterViewModelFactory
import kotlinx.android.synthetic.main.counter.*

/**
 * Activity for our counter screen that inherits from our abstract MVVM activity class
 */
class CounterView : MVVMActivity(), CounterNavigator {
    private lateinit var viewModel: CounterViewModel

    /**
     * Load our viewmodel class from the relevant view model factory.
     * Ensures that the viewmodel loaded is tied to the lifecycle of this activity
     * Observes the counter livedata in the viewmodel
     */
    override fun initViewModel() {
        val factory = CounterViewModelFactory(application)
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

        setSupportActionBar(toolbar as Toolbar)

        increment_button.setOnClickListener(incrementClickListener)

        counter_textview.text = viewModel.count.toString()
    }

    /**
     * Inflates a menu onto our custom toolbar
     * @param menu The menu that we are inflating with our custom menu layout
     * @return Whether the custom menu was loaded
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    /**
     * Reacts to options being selected from the menu in the custom toolbar
     * @param item The selected menu item
     * @return Whether we processed the menu item's selection
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.toolbar_options) {
            openOptionsView()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Starts the Options activity
     */
    override fun openOptionsView() {
        startActivity(Intent(this, OptionsView::class.java))
    }

    /**
     * OnClickListener that processes click events on the increment button by incrementing the value in the view model
     */
    private var incrementClickListener = View.OnClickListener {
        viewModel.incrementCounter()
    }
}