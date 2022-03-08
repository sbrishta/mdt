package com.sbr.mdt.login.data

import com.sbr.mdt.api.retrofit.RetrofitInstance
import com.sbr.mdt.login.data.api.LoginRequest

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository {


    suspend fun login(loginRequest : LoginRequest) =
        RetrofitInstance.api.login(loginRequest)

}