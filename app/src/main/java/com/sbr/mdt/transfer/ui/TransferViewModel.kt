package com.sbr.mdt.transfer.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbr.mdt.R
import com.sbr.mdt.transfer.data.payees.PayeesGetResponse
import com.sbr.mdt.transfer.data.post_transfer.TransferRequest
import com.sbr.mdt.transfer.data.post_transfer.TransferResponse
import com.sbr.mdt.transfer.repository.TransferRepository
import com.sbr.mdt.util.NetworkStatus
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
            if(NetworkStatus.checkForInternet()) {
                allPayees.postValue(Resource.Loading())
                val response = repository.getPayees()
                allPayees.postValue(handleGetPayeesResponse(response))
            }else allPayees.postValue(Resource.NetworkError(R.string.no_internet_message))
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
            if(NetworkStatus.checkForInternet()) {
                val response = repository.makeTransfer(
                    TransferRequest(
                        amount,
                        description,
                        receipientAccountNo
                    )
                )
                transferResponse.postValue(handleTransferResponse(response))
            }else transferResponse.postValue(Resource.NetworkError(R.string.no_internet_message))
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

    private val _transferFormState = MutableLiveData<TransferFormState>()
    val transferFormState : LiveData<TransferFormState> = _transferFormState


    fun formDataChanged(amount : String) {
        if (!isAmountValid(amount)) {
            _transferFormState.value = TransferFormState(amountError = R.string.invalid_amount)
        } else {
            _transferFormState.value = TransferFormState(isDataValid = true)
        }
    }

    private fun isAmountValid(amount : String) : Boolean {
        if(amount.isNotEmpty()){
            val amountData = amount.toDouble()
            return amountData > 0
        }
        return false
    }


}