package com.sbr.mdt.dashboard.repository

import com.sbr.mdt.api.retrofit.MDTAPI
import com.sbr.mdt.api.retrofit.RetrofitInstance

class TransactionBalanceRepository(val authToken:String?) {
    val api : MDTAPI = RetrofitInstance.api

    suspend fun getBalance()=
        api.getBalance(authToken)

    suspend fun getTransactions() =
        api.getTransactions(authToken)


}