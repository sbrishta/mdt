package com.sbr.mdt.register.ui

import android.app.Activity
import android.content.Context
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.sbr.mdt.R

import com.sbr.mdt.databinding.ActivityRegisterBinding
import com.sbr.mdt.register.api.RegisterResponse
import com.sbr.mdt.util.Resource

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel : RegisterViewModel
    private lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.etUsername
        val password = binding.etPassword
        val confirmPassword = binding.etConfirmPassword
        val register = binding.btnRegister
        val loading = binding.loginProgress

        viewModel = ViewModelProvider(this, RegisterViewModelFactory())
            .get(RegisterViewModel::class.java)

        viewModel.registerFormState.observe(this, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
//            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
            if (loginState.passwordMatchError != null) {
                confirmPassword.error = getString(loginState.passwordMatchError)
            }
        })


        viewModel.loginResponse.observe(this, Observer {
            loading.visibility = View.GONE
            when(it){
                is Resource.Success -> {
                    it.data?.let { it1 -> RegisterResponse(it1.status,it.data.token) }
                        ?.let { it2 -> updateUiWithUser(it2) }
                }
                is Resource.Error -> {

                    showLoginFailed(it.message)
                }
                is Resource.Loading ->{
                    //wait for the loading to finish. might showing please wait message in future
                }
            }
        })
        username.afterTextChanged {
            viewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString(),
                confirmPassword.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                viewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString(),
                    confirmPassword.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        startLogin(loading,username,password)
                }
                false
            }
        }
        register.setOnClickListener {
            startLogin(loading,username,password)
        }
    }
    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    private fun startLogin(loading:ProgressBar,username:TextInputEditText,password:TextInputEditText){
        loading.visibility = View.VISIBLE
        hideKeyboard((currentFocus?: this) as View)
        viewModel.register(username.text.toString().trim(), password.text.toString().trim())
    }
    private fun showDashBoard() {
        onBackPressed()
        finish()
    }

    private fun updateUiWithUser(model : RegisterResponse) {
        val welcome = getString(R.string.register_success)
        val displayName =

                    Toast . makeText (
                    applicationContext,
            "$welcome ",
            Toast.LENGTH_LONG
        ).show()
        showDashBoard()
    }

    private fun showLoginFailed(errorString : String?) {
        Toast.makeText(applicationContext, errorString ?: getString(R.string.register_failed), Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged : (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable : Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s : CharSequence, start : Int, count : Int, after : Int) {}

        override fun onTextChanged(s : CharSequence, start : Int, before : Int, count : Int) {
            afterTextChanged.invoke(s.toString())
        }
    })
}