package com.setup.deku.mvvm.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Abstract implementation of an AppCompatActivity that all our activities in the MVVM architecture should inherit from
 */
abstract class MVVMActivity : AppCompatActivity() {
    /**
     * Lifecycle method called whenever the activity is created.
     * Ensures that the view model is loaded and the view is initialized
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        setupView()
    }

    /**
     * Abstract function needed to load the view model of each activity
     */
    abstract fun initViewModel()

    /**
     * Abstract function needed to setup the view of each activity
     */
    abstract fun setupView()
}