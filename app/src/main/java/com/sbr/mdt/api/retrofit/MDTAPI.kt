package com.sbr.mdt.api.retrofit

import com.sbr.mdt.api.balance.BalanceGetResponse
import com.sbr.mdt.api.payees.PayeesGetResponse
import com.sbr.mdt.api.transactions.TransactionsGetResponse
import com.sbr.mdt.login.data.api.LoginRequest
import com.sbr.mdt.login.data.api.LoginResponse
import com.sbr.mdt.util.Constants
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MDTAPI {
    @GET("transactions")
    suspend fun getTransactions(@Header(Constants.AUTH_KEY) token : String?) : Response<TransactionsGetResponse>

    @GET("balance")
    suspend fun getBalance(
        @Header(Constants.AUTH_KEY) token : String?
    ) : Response<BalanceGetResponse>

    @GET("payees")
    suspend fun getPayees(@Header(Constants.AUTH_KEY) token : String?) : Response<PayeesGetResponse>

    @POST("login")
    suspend fun login(@Body request : LoginRequest) : Response<LoginResponse>


}