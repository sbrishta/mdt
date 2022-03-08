package com.sbr.mdt.transfer.repository

import com.sbr.mdt.api.retrofit.MDTAPI
import com.sbr.mdt.api.retrofit.RetrofitInstance
import com.sbr.mdt.transfer.data.post_transfer.TransferRequest

class TransferRepository(private val authToken : String?) {
    val api : MDTAPI = RetrofitInstance.api

    suspend fun getPayees()=
        api.getPayees(authToken)

    suspend fun makeTransfer(request:TransferRequest)=
        api.transfer(authToken,request)


}