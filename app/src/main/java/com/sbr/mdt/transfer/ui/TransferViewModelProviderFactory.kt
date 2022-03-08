package com.sbr.mdt.transfer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sbr.mdt.dashboard.repository.TransactionBalanceRepository
import com.sbr.mdt.dashboard.ui.DashBoardViewModel
import com.sbr.mdt.transfer.repository.TransferRepository

class TransferViewModelProviderFactory(
    val mdtRepository: TransferRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TransferViewModel(mdtRepository) as T
    }
}