package com.sbr.mdt.dashboard.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbr.mdt.dashboard.data.balance.BalanceGetResponse
import com.sbr.mdt.dashboard.data.transactions.TransactionsGetResponse
import com.sbr.mdt.dashboard.repository.TransactionBalanceRepository
import com.sbr.mdt.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class DashBoardViewModel(val repository:TransactionBalanceRepository): ViewModel() {
    val userBalance:MutableLiveData<Resource<BalanceGetResponse>> = MutableLiveData()
    val transactions: MutableLiveData<Resource<TransactionsGetResponse>> = MutableLiveData()
    init {
        getBalanceData()
        getTransactions()
    }
    fun getBalanceData(){
        viewModelScope.launch {
            userBalance.postValue(Resource.Loading())
            val response = repository.getBalance()
            userBalance.postValue(handleGetBalanceResponse(response))
        }
    }
    private fun handleGetBalanceResponse(response: Response<BalanceGetResponse>):Resource<BalanceGetResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultData ->
                return Resource.Success(resultData)
            }
        }
        return Resource.Error(response.body(),response.errorBody().toString()+response.message())
    }

    fun getTransactions(){
        viewModelScope.launch {
            transactions.postValue(Resource.Loading())
            val response = repository.getTransactions()
            transactions.postValue(handleTransactionsResponse(response))
        }
    }
    private fun handleTransactionsResponse(response: Response<TransactionsGetResponse>):Resource<TransactionsGetResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultData ->
                return Resource.Success(resultData)
            }
        }
        return Resource.Error(response.body(),response.errorBody().toString()+response.message())
    }

    fun logout(){
        //clear user data,user info
    }

}