package com.sbr.mdt.dashboard.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sbr.mdt.R
import com.sbr.mdt.dashboard.data.balance.BalanceGetResponse
import com.sbr.mdt.dashboard.repository.TransactionBalanceRepository
import com.sbr.mdt.dashboard.ui.adapter.TransactionGroupedListAdapter

import com.sbr.mdt.databinding.ActivityMainBinding
import com.sbr.mdt.login.data.api.LoginResponse
import com.sbr.mdt.transfer.ui.TransferActivity
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
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val balance = binding.tvBalance
        val accountNo = binding.tvAccountNo
        val accountHolder = binding.tvAccountHolder
        val makeTransfer = binding.btnMakeTransfer

        val rvTransactionHistory = binding.rvTransactionHistory
        //val dateAdapter = TransactionDateHeaderListAdapter()
        val dateAdapter = TransactionGroupedListAdapter()
        rvTransactionHistory.adapter = dateAdapter
        rvTransactionHistory.isNestedScrollingEnabled = false
        rvTransactionHistory.adapter = dateAdapter
        rvTransactionHistory.layoutManager = LinearLayoutManager(this)
        rvTransactionHistory.setHasFixedSize(true)


        val token = SessionManager.fetchAuthToken()
        val repository = TransactionBalanceRepository(token)
        val viewModelProviderFactory = DashBoardViewModelProviderFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(DashBoardViewModel::class.java)

       //-------set up user data from login response-----------
        val userData:LoginResponse? = SessionManager.get<LoginResponse>(Constants.USER_DATA)
        if (userData != null) {
            accountNo.text = userData.accountNo
            accountHolder.text = userData.username
        }

        makeTransfer.setOnClickListener {
            goToTransfer()

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
                    response.data?.let { viewModel.populateData(it.data) }
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
        viewModel.transactionLists.observe(this, Observer { items ->
            items.let{
                //adapter.submitList(it[0].transactionItems)
                dateAdapter.transactionHistory = it
                dateAdapter.notifyItemRangeInserted(0,it.size-1)
            }
        })
    }

    override fun onCreateOptionsMenu(menu : Menu?) : Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        when (item.itemId) {
            R.id.logout_menu -> {
                logout()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        viewModel.logout()
        finish()
    }

    private fun setBalanceData(response:Resource<BalanceGetResponse>,balance:TextView){
        val balanceData = response.data?.balance?.toDouble()
        val formatted = java.lang.String.format(Locale.getDefault(),getString(R.string.currency_formatter), balanceData)
        balance.text = formatted
    }
    private fun goToTransfer(){
        val intent = Intent(this, TransferActivity::class.java)
        // start main activity
        startActivity(intent)
    }
}