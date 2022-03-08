package com.sbr.mdt.register.repository

import com.sbr.mdt.api.retrofit.RetrofitInstance
import com.sbr.mdt.login.data.api.LoginRequest
import com.sbr.mdt.register.api.RegisterRequest

class RegisterRepository {
    suspend fun register(loginRequest : RegisterRequest) =
        RetrofitInstance.api.register(loginRequest)

}