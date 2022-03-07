package com.sbr.mdt.dashboard.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sbr.mdt.R
import com.sbr.mdt.dashboard.repository.TransactionBalanceRepository
import com.sbr.mdt.util.Resource
import com.sbr.mdt.util.SessionManager

class MainActivity : AppCompatActivity() {
    lateinit var viewModel : DashBoardViewModel
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val token = SessionManager(this).fetchAuthToken()
        val repository = TransactionBalanceRepository(token)
        val viewModelProviderFactory = DashBoardViewModelProviderFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(DashBoardViewModel::class.java)
        findViewById<Button>(R.id.button).setOnClickListener {
            viewModel.getBalanceData()
            viewModel.getTransactions()
        }
        viewModel.userBalance.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    Log.d("BALANCe", response.data.toString())
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