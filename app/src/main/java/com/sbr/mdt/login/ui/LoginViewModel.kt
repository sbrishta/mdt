package com.sbr.mdt.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbr.mdt.R
import com.sbr.mdt.login.LoggedInUserView
import com.sbr.mdt.login.LoginResult
import com.sbr.mdt.login.data.LoginRepository
import com.sbr.mdt.login.data.api.LoginRequest
import com.sbr.mdt.login.data.api.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Response


class LoginViewModel(private val loginRepository : LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState : LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult : LiveData<LoginResult> = _loginResult

//    fun login(username : String, password : String) {
//        // can be launched in a separate asynchronous job
//        val result = loginRepository.login(username, password)
//
//        if (result is Result.Success) {
//            _loginResult.value =
//                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
//        } else {
//            _loginResult.value = LoginResult(error = R.string.login_failed)
//        }
//    }

    fun login(username : String,password : String){
        viewModelScope.launch {
            val response = loginRepository.login(LoginRequest(username,password))
            _loginResult.postValue(handleLoginResponse(response))
        }
    }

    private fun handleLoginResponse(response : Response<LoginResponse>) : LoginResult {
        if(response.isSuccessful){
            response.body()?.let { resultData ->
                return LoginResult(success = LoggedInUserView(displayName = resultData.username,resultData.token))
            }
        }
        return LoginResult(error = R.string.login_failed)

    }


    fun loginDataChanged(username : String, password : String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username : String) : Boolean {
        return username.isNotBlank() || username.isNotEmpty()

    }

    // A placeholder password validation check
    private fun isPasswordValid(password : String) : Boolean {
        return password.length > 5
    }
}