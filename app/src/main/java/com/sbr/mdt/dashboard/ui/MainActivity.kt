package com.sbr.mdt.dashboard.ui

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sbr.mdt.R
import com.sbr.mdt.dashboard.data.balance.BalanceGetResponse
import com.sbr.mdt.dashboard.repository.TransactionBalanceRepository
import com.sbr.mdt.databinding.ActivityMainBinding
import com.sbr.mdt.login.data.api.LoginResponse
import com.sbr.mdt.util.Constants
import com.sbr.mdt.util.Resource
import com.sbr.mdt.util.SessionManager
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel : DashBoardViewModel
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val balance = binding.tvBalance
        val accountNo = binding.tvAccountNo
        val accountHolder = binding.tvAccountHolder
        val makeTransfer = binding.btnMakeTransfer
        val logout = binding.btnLogout

        val token = SessionManager.fetchAuthToken()
        val repository = TransactionBalanceRepository(token)
        val viewModelProviderFactory = DashBoardViewModelProviderFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(DashBoardViewModel::class.java)

       //-------set up user data from login response-----------
        val userData = SessionManager.get<LoginResponse>(Constants.USER_DATA)
        if (userData != null) {
            accountNo.text = userData.accountNo
            accountHolder.text = userData.username
        }

        makeTransfer.setOnClickListener {


        }
        logout.setOnClickListener{
            viewModel.logout()
        }
        viewModel.userBalance.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    setBalanceData(response,balance)
                }
                is Resource.Error -> {
                    Log.d("BALANCe", response.message.toString())
                }
                is Resource.Loading -> {
                    //show loading progress bar
                }
            }
        })
        viewModel.transactions.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    Log.d("TRANSACTION", response.data.toString())
                }
                is Resource.Error -> {
                    Log.d("TRANSACTION", response.message.toString())
                }
                is Resource.Loading -> {
                    //show loading progress bar
                }
            }
        })
    }
    private fun setBalanceData(response:Resource<BalanceGetResponse>,balance:TextView){
        val balanceData = response.data?.balance?.toDouble()
        val formatted = java.lang.String.format(Locale.getDefault(),getString(R.string.currency_formatter), balanceData)
        balance.text = formatted
    }
}