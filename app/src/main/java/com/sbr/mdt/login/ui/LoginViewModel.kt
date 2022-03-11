package com.sbr.mdt.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbr.mdt.R
import com.sbr.mdt.login.data.LoginRepository
import com.sbr.mdt.login.data.api.LoginRequest
import com.sbr.mdt.login.data.api.LoginResponse
import com.sbr.mdt.util.Constants
import com.sbr.mdt.util.NetworkStatus
import com.sbr.mdt.util.Resource
import com.sbr.mdt.util.SessionManager
import kotlinx.coroutines.launch
import retrofit2.Response


class LoginViewModel(private val loginRepository : LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState : LiveData<LoginFormState> = _loginForm

    val loginResponse:MutableLiveData<Resource<LoginResponse>> = MutableLiveData()

    fun login(username : String,password : String){
        viewModelScope.launch {
            if(NetworkStatus.checkForInternet()) {
                val response = loginRepository.login(LoginRequest(username, password))
                loginResponse.postValue(handleLoginResponse(response))
            }else loginResponse.postValue(Resource.NetworkError(errorMessage = R.string.no_internet_message))
        }
    }

    private fun handleLoginResponse(response : Response<LoginResponse>) : Resource<LoginResponse> {
        if(response.isSuccessful){
            response.body()?.let { resultData ->
                return Resource.Success(resultData)
            }
        }
        return Resource.Error(response.body(),response.errorBody().toString()+response.message())

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
        return password.length >= 1
    }

    fun saveLoginUser(model:LoginResponse){
        SessionManager.put(model,Constants.USER_DATA)
        SessionManager.saveAuthToken(model.token)
    }
}