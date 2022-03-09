package com.sbr.mdt.login.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
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
import com.sbr.mdt.dashboard.ui.MainActivity

import com.sbr.mdt.databinding.ActivityLoginBinding
import com.sbr.mdt.login.data.api.LoginResponse
import com.sbr.mdt.register.ui.RegisterActivity
import com.sbr.mdt.util.Resource

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel : LoginViewModel
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.etUsername
        val password = binding.etPassword
        val login = binding.btnLogin
        val register = binding.btnRegister
        val loading = binding.loginProgress
        login.isEnabled = false

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })


        loginViewModel.loginResponse.observe(this, Observer {
            loading.visibility = View.GONE
            when(it){
                is Resource.Success -> {
                    it.data?.let { it1 -> LoginResponse(it1.accountNo,it.data.status,it.data.token,it.data.username) }
                        ?.let { it2 -> updateUiWithUser(it2) }
                }
                is Resource.Error -> {
                    showLoginFailed(R.string.login_failed)
                }
                is Resource.Loading ->{
                    //wait for the loading to finish. might showing please wait message in future
                }
            }
        })
        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
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
        login.setOnClickListener {
            startLogin(loading,username,password)
        }
        register.setOnClickListener {
            startRegistration()
        }
    }

    private fun startRegistration() {
        val intent = Intent(this, RegisterActivity::class.java)
        // start main activity
        startActivity(intent)
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    private fun startLogin(loading:ProgressBar,username:TextInputEditText,password:TextInputEditText){
        loading.visibility = View.VISIBLE
        hideKeyboard((currentFocus?: this) as View)
        loginViewModel.login(username.text.toString().trim(), password.text.toString().trim())
    }
    private fun showDashBoard() {
        val intent = Intent(this, MainActivity::class.java)
        // start main activity
        startActivity(intent)
        finish()
    }

    private fun updateUiWithUser(model : LoginResponse) {
        val welcome = getString(R.string.welcome)
        val displayName = model.username
        //----------------------
       loginViewModel.saveLoginUser(model)
                    //----------------------------
                    Toast . makeText (
                    applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
        showDashBoard()
    }

    private fun showLoginFailed(@StringRes errorString : Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
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

        override fun onTextChanged(s : CharSequence, start : Int, before : Int, count : Int) {}
    })
}