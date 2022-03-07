package com.sbr.mdt.dashboard.data.balance

data class BalanceGetResponse(
    val accountNo: String,
    val balance: Double,
    val status: String
)