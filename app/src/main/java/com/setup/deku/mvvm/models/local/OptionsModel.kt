package com.setup.deku.mvvm.models.local

/**
 * Model class for holding the state of the options input form
 */
class OptionsModel(intake: String, username: String) {
    var intake = intake
    var username = username

    /**
     * Validates the daily calorie intake field to ensure that a valid numerical value was entered
     * @return Whether the intake value is valid
     */
    fun isIntakeValid(): Boolean {
        val intakeNum = intake.toIntOrNull()
        if(intakeNum == null) {
            return false
        }
        return true
    }

    /**
     * Validates the username was actually entered and not left null
     * @return Whether the username is null
     */
    fun isUsernameValid(): Boolean {
        if(username == null) {
            return false
        }
        return true
    }
}