package com.sbr.mdt.ui.dashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbr.mdt.api.balance.BalanceErrorResponse
import com.sbr.mdt.api.balance.BalanceGetResponse
import com.sbr.mdt.api.retrofit.RetrofitInstance
import com.sbr.mdt.api.transactions.TransactionsGetResponse
import com.sbr.mdt.repository.MDTRepository
import com.sbr.mdt.util.Constants
import com.sbr.mdt.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashBoardViewModel(val repository:MDTRepository): ViewModel() {
    val userBalance:MutableLiveData<Resource<BalanceGetResponse>> = MutableLiveData()
    val transactions: MutableLiveData<Resource<TransactionsGetResponse>> = MutableLiveData()

    fun getBalanceData(){
        viewModelScope.launch {
            userBalance.postValue(Resource.Loading())
            val response = repository.getBalance(Constants.token)
            userBalance.postValue(handleGetBalanceResponse(response))
        }
    }
    private fun handleGetBalanceResponse(response: Response<BalanceGetResponse>):Resource<BalanceGetResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultData ->
                return Resource.Success(resultData)
            }
        }
        return Resource.Error(response.body(),response.message())
    }

}