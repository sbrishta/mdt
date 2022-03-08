package com.sbr.mdt.api.retrofit

import com.sbr.mdt.dashboard.data.balance.BalanceGetResponse
import com.sbr.mdt.transfer.data.payees.PayeesGetResponse
import com.sbr.mdt.dashboard.data.transactions.TransactionsGetResponse
import com.sbr.mdt.login.data.api.LoginRequest
import com.sbr.mdt.login.data.api.LoginResponse
import com.sbr.mdt.register.api.RegisterRequest
import com.sbr.mdt.register.api.RegisterResponse
import com.sbr.mdt.transfer.data.post_transfer.TransferRequest
import com.sbr.mdt.transfer.data.post_transfer.TransferResponse
import com.sbr.mdt.util.Constants
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

    @POST("register")
    suspend fun register(@Body request : RegisterRequest) : Response<RegisterResponse>

    @POST("transfer")
    suspend fun transfer(@Header(Constants.AUTH_KEY) token : String?,@Body request : TransferRequest) : Response<TransferResponse>


}