package com.sbr.mdt.dashboard.ui.adapter

import android.graphics.Color
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sbr.mdt.R
import com.sbr.mdt.dashboard.data.transactions.TransactionInfo
import com.sbr.mdt.databinding.DateHeaderItemBinding
import com.sbr.mdt.databinding.TransactionDetailsItemBinding
import com.sbr.mdt.util.Constants
import java.util.*


open class TransactionItemListAdapter : ListAdapter<TransactionInfo, TransactionItemListAdapter.ItemViewHolder>(ItemComparator())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TransactionDetailsItemBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)

        holder.binding.tvAccountName.text = current.receipient.accountHolder
        holder.binding.tvAccount.text = current.receipient.accountNo

        val price = current.amount
        val mContext = holder.itemView.context
        val formatter = mContext.getString(R.string.currency_formatter)
        val formatted = java.lang.String.format(Locale.getDefault(),formatter, price)
        holder.binding.tvAmount.text = formatted
        if(price > 0) holder.binding.tvAmount.setTextColor(Color.parseColor(Constants.PRICE_COLOR))
        else holder.binding.tvAmount.setTextColor(Color.BLACK)

    }
    class ItemViewHolder(val binding : TransactionDetailsItemBinding) : RecyclerView.ViewHolder(binding.root)

    //comparator function defines whether words are same or the content same
    class ItemComparator : DiffUtil.ItemCallback<TransactionInfo>() {
        override fun areItemsTheSame(oldItem: TransactionInfo, newItem: TransactionInfo): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: TransactionInfo, newItem: TransactionInfo): Boolean {
            return oldItem.transactionId == newItem.transactionId
        }
    }
}