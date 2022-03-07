package com.sbr.mdt.dashboard.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sbr.mdt.dashboard.repository.TransactionBalanceRepository

class DashBoardViewModelProviderFactory(
    val mdtRepository: TransactionBalanceRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DashBoardViewModel(mdtRepository) as T
    }
}