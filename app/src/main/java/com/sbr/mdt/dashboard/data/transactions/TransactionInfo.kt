package com.sbr.mdt.dashboard.data.transactions

data class TransactionInfo(
    val amount: Double,
    val description: String,
    val receipient: Receipient,
    val sender: Sender,
    val transactionDate: String,
    val transactionId: String,
    val transactionType: String
)