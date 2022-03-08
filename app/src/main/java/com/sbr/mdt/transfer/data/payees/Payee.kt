package com.sbr.mdt.transfer.data.payees

data class Payee(
    val accountNo: String,
    val id: String,
    val name: String
){
    override fun toString() : String {
        return name
    }
}