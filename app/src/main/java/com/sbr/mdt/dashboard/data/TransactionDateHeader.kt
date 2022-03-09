package com.sbr.mdt.dashboard.data

import com.sbr.mdt.dashboard.data.balance.BalanceGetResponse
import com.sbr.mdt.dashboard.data.transactions.TransactionInfo
import com.sbr.mdt.dashboard.data.transactions.TransactionsGetResponse

data class TransactionDateHeader(
    val date : String,
    val transactionItems : List<TransactionInfo>):ListItem(){
    override val type : Int
        get() = ListItem.TYPE_DATE

}
