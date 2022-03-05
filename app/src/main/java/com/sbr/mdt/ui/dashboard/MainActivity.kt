package com.sbr.mdt.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sbr.mdt.R
import com.sbr.mdt.repository.MDTRepository
import com.sbr.mdt.util.Resource

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: DashBoardViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = MDTRepository()
        val viewModelProviderFactory = DashBoardViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(DashBoardViewModel::class.java)
        findViewById<Button>(R.id.button).setOnClickListener{
            viewModel.getBalanceData()
        }
        viewModel.userBalance.observe(this, Observer { response ->
            when(response){
                is Resource.Success ->{
                    Log.d("BALANCe",response.data.toString())
                }
                is Resource.Error ->{
                    Log.d("BALANCe",response.message.toString())
                }
                is Resource.Loading -> {
                    //show loading progress bar
                }
            }
        })
    }
}