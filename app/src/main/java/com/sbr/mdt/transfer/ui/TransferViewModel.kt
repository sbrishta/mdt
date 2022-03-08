package com.sbr.mdt.transfer.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbr.mdt.dashboard.data.balance.BalanceGetResponse
import com.sbr.mdt.dashboard.data.transactions.TransactionsGetResponse
import com.sbr.mdt.register.api.RegisterRequest
import com.sbr.mdt.register.api.RegisterResponse
import com.sbr.mdt.transfer.data.payees.PayeesGetResponse
import com.sbr.mdt.transfer.data.post_transfer.TransferRequest
import com.sbr.mdt.transfer.data.post_transfer.TransferResponse
import com.sbr.mdt.transfer.repository.TransferRepository
import com.sbr.mdt.util.Resource
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

class TransferViewModel(val repository : TransferRepository):ViewModel() {
    val allPayees: MutableLiveData<Resource<PayeesGetResponse>> = MutableLiveData()
    init {
        getPayees()
    }
    private fun getPayees(){
        viewModelScope.launch {
            allPayees.postValue(Resource.Loading())
            val response = repository.getPayees()
            allPayees.postValue(handleGetPayeesResponse(response))
        }
    }
    private fun handleGetPayeesResponse(response: Response<PayeesGetResponse>): Resource<PayeesGetResponse> {
        if(response.isSuccessful){
            response.body()?.let { resultData ->
                return Resource.Success(resultData)
            }
        }
        return Resource.Error(response.body(),response.errorBody().toString()+response.message())
    }
    val transferResponse:MutableLiveData<Resource<TransferResponse>> = MutableLiveData()

    fun makeTransfer(amount:Double,description:String,receipientAccountNo:String){
        viewModelScope.launch {
            val response = repository.makeTransfer(TransferRequest(amount, description, receipientAccountNo))
            transferResponse.postValue(handleTransferResponse(response))
        }
    }

    private fun handleTransferResponse(response : Response<TransferResponse>) : Resource<TransferResponse> {
        if(response.isSuccessful){
            response.body()?.let { resultData ->
                return Resource.Success(resultData)
            }
        }
        val jObjError = JSONObject(response.errorBody()?.string() ?: response.message())
        val msg = jObjError.getString("error")
        return Resource.Error(response.body(),msg)

    }

}