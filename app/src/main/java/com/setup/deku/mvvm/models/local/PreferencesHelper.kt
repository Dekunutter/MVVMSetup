package com.setup.deku.mvvm.models.local

import android.content.Context
import android.content.SharedPreferences

/**
 * Abstract class that sets up access to the shared preference files
 */
abstract class PreferencesHelper(context: Context, preferenceFile: String) {
    protected val preferences: SharedPreferences = context.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE)
}