package com.sbr.mdt.register.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbr.mdt.R
import com.sbr.mdt.register.api.RegisterRequest
import com.sbr.mdt.register.api.RegisterResponse
import com.sbr.mdt.register.repository.RegisterRepository
import com.sbr.mdt.util.Resource
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

class RegisterViewModel(val repository : RegisterRepository) : ViewModel() {
    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState : LiveData<RegisterFormState> = _registerForm

    fun loginDataChanged(username : String, password : String,confirmPassword : String) {
        if (!isUserNameValid(username)) {
            _registerForm.value = RegisterFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        }  else if (!isConfirmPasswordValid(password,confirmPassword)) {
            _registerForm.value = RegisterFormState(passwordMatchError = R.string.invalid_confirm_password)
        } else {
            _registerForm.value = RegisterFormState(isDataValid = true)
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
    // A placeholder password validation check
    private fun isConfirmPasswordValid(password : String,confirmPassword : String) : Boolean {
        return password == confirmPassword
    }

    val loginResponse:MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()

    fun register(username : String,password : String){
        viewModelScope.launch {
            val response = repository.register(RegisterRequest(username,password))
            loginResponse.postValue(handleLoginResponse(response))
        }
    }

    private fun handleLoginResponse(response : Response<RegisterResponse>) : Resource<RegisterResponse> {
        if(response.isSuccessful){
            response.body()?.let { resultData ->
                return Resource.Success(resultData)
            }
        }
        val jObjError = JSONObject(response.errorBody()?.string() ?: response.message())
        val msg = jObjError.getString("error")
        Log.d("FAIL",msg)
        return Resource.Error(response.body(),msg)

    }

}