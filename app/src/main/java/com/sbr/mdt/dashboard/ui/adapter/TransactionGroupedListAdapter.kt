package com.sbr.mdt.dashboard.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.sbr.mdt.R
import com.sbr.mdt.dashboard.data.adapter_helper.ListItem
import com.sbr.mdt.dashboard.data.adapter_helper.TransactionDateHeader
import com.sbr.mdt.dashboard.data.transactions.TransactionInfo
import com.sbr.mdt.databinding.DateHeaderItemBinding
import com.sbr.mdt.databinding.TransactionDetailsItemBinding
import com.sbr.mdt.util.Constants
import java.util.*
import kotlin.collections.ArrayList


class TransactionGroupedListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val viewPool = RecycledViewPool()
    var transactionHistory :List<ListItem> = ArrayList()
    class HeaderViewHolder(val headerBinding:DateHeaderItemBinding):RecyclerView.ViewHolder(headerBinding.root)
    class ItemViewHolder(val detailsItemBinding : TransactionDetailsItemBinding ) : RecyclerView.ViewHolder(detailsItemBinding.root)



    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val holder = when(viewType){
            ListItem.TYPE_DATE -> {
                 HeaderViewHolder(DateHeaderItemBinding.inflate(inflater,parent,false))
            }
            else -> {
                ItemViewHolder(TransactionDetailsItemBinding.inflate(inflater, parent, false))
            }
        }
        return holder
    }

    override fun getItemCount() : Int {
        return transactionHistory.size
    }
    override fun getItemViewType(position : Int) : Int {
        return transactionHistory.get(position).type
    }
    override fun onBindViewHolder(holder : RecyclerView.ViewHolder, position : Int) {
        val current = transactionHistory.get(position)
        when(getItemViewType(position)){
            ListItem.TYPE_DATE -> {
                val dateHolder = holder as HeaderViewHolder
                current as TransactionDateHeader
                dateHolder.headerBinding.tvHeaderDate.text = current.date
            }
            else -> {
                current as TransactionInfo
                val itemHolder = holder as ItemViewHolder
                if(current.receipient != null) {
                    itemHolder.detailsItemBinding.tvAccountName.text =
                        current.receipient.accountHolder
                    itemHolder.detailsItemBinding.tvAccount.text = current.receipient.accountNo
                }


                val price = current.amount
                val mContext = holder.itemView.context
                val formatter = mContext.getString(R.string.currency_formatter)
                val formatted = java.lang.String.format(Locale.getDefault(),formatter, price)
                itemHolder.detailsItemBinding.tvAmount.text = formatted
                if(price > 0) itemHolder.detailsItemBinding.tvAmount.setTextColor(Color.parseColor(Constants.PRICE_COLOR))
                else itemHolder.detailsItemBinding.tvAmount.setTextColor(Color.BLACK)
            }
        }
    }

}