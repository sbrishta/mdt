package com.sbr.mdt.login

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val displayName : String,
    val authToken : String
    //... other data fields that may be accessible to the UI
)