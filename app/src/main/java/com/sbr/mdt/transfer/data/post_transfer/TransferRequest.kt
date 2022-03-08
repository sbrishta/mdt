package com.sbr.mdt.transfer.data.post_transfer

data class TransferRequest(
    val amount: Double,
    val description: String,
    val receipientAccountNo: String
)