package com.sbr.mdt.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sbr.mdt.repository.MDTRepository

class DashBoardViewModelProviderFactory(
    val mdtRepository: MDTRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DashBoardViewModel(mdtRepository) as T
    }
}