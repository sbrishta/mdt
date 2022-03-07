package com.sbr.mdt.dashboard.data.transactions

data class TransactionsGetResponse(
    val `data`: List<Data>,
    val status: String
)