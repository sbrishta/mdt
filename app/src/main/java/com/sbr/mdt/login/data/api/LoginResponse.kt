package com.sbr.mdt.login.data.api

data class LoginResponse(
    val accountNo: String,
    val status: String,
    val token: String,
    val username: String
)