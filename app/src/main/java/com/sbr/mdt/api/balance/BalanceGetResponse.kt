package com.sbr.mdt.api.balance

data class BalanceGetResponse(
    val accountNo: String,
    val balance: Double,
    val status: String
)