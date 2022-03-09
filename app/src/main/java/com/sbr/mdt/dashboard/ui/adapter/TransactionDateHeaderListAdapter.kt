package com.sbr.mdt.dashboard.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.sbr.mdt.dashboard.data.ListItem
import com.sbr.mdt.dashboard.data.TransactionDateHeader
import com.sbr.mdt.dashboard.ui.adapter.TransactionDateHeaderListAdapter.HeaderViewHolder
import com.sbr.mdt.databinding.DateHeaderItemBinding
import com.sbr.mdt.databinding.TransactionDetailsItemBinding


open class TransactionDateHeaderListAdapter: RecyclerView.Adapter<HeaderViewHolder>() {
    private val viewPool = RecycledViewPool()
    var transactionHistory :List<TransactionDateHeader> = ArrayList()
    class HeaderViewHolder(val binding:DateHeaderItemBinding):RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : HeaderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DateHeaderItemBinding.inflate(inflater, parent, false)
        return HeaderViewHolder(binding)
    }

    override fun onBindViewHolder(holder : HeaderViewHolder, position : Int) {
        val parentItem = transactionHistory[position]
        holder.binding.tvHeaderDate.text = parentItem.date



//        layoutManager.initialPrefetchItemCount = parentItem
//            .transactionItems.size
        val itemAdapter = TransactionItemListAdapter()
        holder.binding.rvTransactionDetails.adapter = itemAdapter
        holder.binding.rvTransactionDetails.setHasFixedSize(true)

        // holder.binding.rvTransactionDetails.setRecycledViewPool(viewPool)
        itemAdapter.submitList(parentItem.transactionItems)
    }

    override fun getItemCount() : Int {
        return transactionHistory.size
    }

}