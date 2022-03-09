package com.sbr.mdt.dashboard.data.transactions

import com.sbr.mdt.dashboard.data.ListItem

data class TransactionInfo(
    val amount: Double,
    val description: String,
    val receipient: Receipient,
    val sender: Sender,
    val transactionDate: String,
    val transactionId: String,
    val transactionType: String
):ListItem(){
    override val type : Int
        get() = ListItem.TYPE_GENERAL

}