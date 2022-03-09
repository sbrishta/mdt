package com.sbr.mdt.dashboard.data.adapter_helper

data class TransactionDateHeader(
    val date : String,
    ): ListItem(){
    override val type : Int
        get() = TYPE_DATE

}
