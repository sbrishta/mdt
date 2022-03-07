package com.sbr.mdt.dashboard.data.transactions

data class Data(
    val amount: Double,
    val description: String,
    val receipient: Receipient,
    val sender: Sender,
    val transactionDate: String,
    val transactionId: String,
    val transactionType: String
)