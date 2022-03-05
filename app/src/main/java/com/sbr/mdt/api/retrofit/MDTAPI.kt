package com.sbr.mdt.api.retrofit

import com.sbr.mdt.api.balance.BalanceGetResponse
import com.sbr.mdt.api.payees.PayeesGetResponse
import com.sbr.mdt.api.transactions.TransactionsGetResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface MDTAPI {
    @GET("transactions")
    suspend fun getTransactions(@Header("Authorization") token: String): Response<TransactionsGetResponse>

    @GET("balance")
    suspend fun getBalance(
        @Header("Authorization") token: String
    ): Response<BalanceGetResponse>

    @GET("payees")
    suspend fun getPayees(@Header("Authorization") token: String): Response<PayeesGetResponse>


    @GET("balance")
    fun getenqubalance(@Header("Authorization") token: String): Call<BalanceGetResponse>

}