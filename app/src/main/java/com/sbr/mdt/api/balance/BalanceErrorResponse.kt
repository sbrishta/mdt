package com.sbr.mdt.api.balance

data class BalanceErrorResponse(
    val error: Error,
    val status: String
)