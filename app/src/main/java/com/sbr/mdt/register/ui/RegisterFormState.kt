package com.sbr.mdt.register.ui

/**
 * Data validation state of the login form.
 */
data class RegisterFormState(
    val usernameError : Int? = null,
    val passwordError : Int? = null,
    val passwordMatchError : Int? = null,
    val isDataValid : Boolean = false
)