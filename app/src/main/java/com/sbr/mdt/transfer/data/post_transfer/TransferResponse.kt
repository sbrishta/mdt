package com.sbr.mdt.transfer.data.post_transfer

data class TransferResponse(
    val amount: Int,
    val description: String,
    val recipientAccount: String,
    val status: String,
    val transactionId: String
)