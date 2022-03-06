package com.sbr.mdt.login.data

import com.sbr.mdt.api.retrofit.RetrofitInstance
import com.sbr.mdt.login.LoggedInUserView
import com.sbr.mdt.login.LoginResult
import com.sbr.mdt.login.data.api.LoginRequest
import com.sbr.mdt.login.data.api.LoginResponse
import com.sbr.mdt.login.data.model.LoggedInUser
import com.sbr.mdt.util.Resource
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource : LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user : LoggedInUser? = null
        private set

    val isLoggedIn : Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }


    suspend fun login(loginRequest : LoginRequest) =
        RetrofitInstance.api.login(loginRequest)



//    fun login(username : String, password : String) : Result<LoggedInUser> {
//        // handle login
//        val result = dataSource.login(username, password)
//
//        if (result is Result.Success) {
//            setLoggedInUser(result.data)
//        }
//
//        return result
//    }


    private fun setLoggedInUser(loggedInUser : LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}