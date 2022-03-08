package com.sbr.mdt.transfer.ui

import com.sbr.mdt.transfer.data.payees.Payee

/**
 * Data validation state of the login form.
 */
data class TransferFormState(
    val amountError : Int? = null,
    val isDataValid : Boolean = false
)