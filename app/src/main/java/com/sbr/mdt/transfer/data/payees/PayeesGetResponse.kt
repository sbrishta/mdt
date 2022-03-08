package com.sbr.mdt.transfer.data.payees

data class PayeesGetResponse(
    val `data`: List<Payee>,
    val status: String
)