package com.setup.deku.mvvm.models.local

import android.content.Context

private const val APP_PREFERENCES_FILE = "APP_PREFERENCES"
private const val DAILY_INTAKE = "PREF_KEY_DAILY_INTAKE"
private const val USERNAME = "PREF_KEY_USERNAME"

/**
 * Concreate implementation of Shared Preferences access intended for general application settings
 */
class AppPreferencesHelper(context: Context): PreferencesHelper(context, APP_PREFERENCES_FILE) {

    /**
     * Get the value of daily calorie intake set into the shared preferences
     */
    fun getDailyIntake(): Int {
        return preferences.getInt(DAILY_INTAKE, -1)
    }

    /**
     * Set the value of daily calorie intake into the shared preferences
     * @param value The integer value we want to register as our daily calorie intake
     * @return Whether the desired value was written to the shared preferences correctly or not
     */
    fun setDailyIntake(value: Int): Boolean {
        return preferences.edit().putInt(DAILY_INTAKE, value).commit()
    }

    /**
     * Get the value of the username set into the shared preferences
     */
    fun getUsername(): String {
        return preferences.getString(USERNAME, "")
    }

    /**
     * Set the value of the username into the shared preferences
     * @param value The string value we want to register as the username
     * @return Whether the desired value was written to the shared preferences correctly or not
     */
    fun setUsername(value: String): Boolean {
        return preferences.edit().putString(USERNAME, value).commit()
    }
}