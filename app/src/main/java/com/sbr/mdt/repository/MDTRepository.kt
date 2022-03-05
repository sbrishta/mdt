package com.sbr.mdt.repository

import androidx.lifecycle.LiveData
import com.sbr.mdt.api.balance.BalanceGetResponse
import com.sbr.mdt.api.retrofit.MDTAPI
import com.sbr.mdt.api.retrofit.RetrofitInstance
import retrofit2.Response

class MDTRepository {
    val api : MDTAPI = RetrofitInstance.api
    suspend fun getTransactions(authToken : String) =
        api.getTransactions(authToken)


    suspend fun getBalance(authToken : String) =
         api.getBalance(authToken)


    suspend fun getPayees(authToken : String) =
        api.getPayees(authToken)


}