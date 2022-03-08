package com.sbr.mdt.dashboard.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sbr.mdt.R
import com.sbr.mdt.dashboard.repository.TransactionBalanceRepository
import com.sbr.mdt.databinding.ActivityLoginBinding
import com.sbr.mdt.databinding.ActivityMainBinding
import com.sbr.mdt.util.Resource
import com.sbr.mdt.util.SessionManager

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
        makeTransfer.setOnClickListener {
            viewModel.getBalanceData()
            viewModel.getTransactions()
        }
        logout.setOnClickListener{
            viewModel.logout()
        }
        viewModel.userBalance.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {

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
}