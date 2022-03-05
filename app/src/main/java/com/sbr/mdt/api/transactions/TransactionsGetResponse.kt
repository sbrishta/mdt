package com.sbr.mdt.api.transactions

data class TransactionsGetResponse(
    val `data`: List<Data>,
    val status: String
)