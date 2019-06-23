package com.setup.deku.mvvm.ui.navigators

/**
 * Navigation interface that declares which activities the Splash screen can open
 */
interface SplashNavigator {
    fun openInitializerView()

    fun openCounterView()
}