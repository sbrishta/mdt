package com.sbr.mdt.dashboard.data.adapter_helper

import com.sbr.mdt.dashboard.data.transactions.TransactionInfo
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

class PopulateTransactionData {
    fun getConsolidatedHeader(listOfItems : List<TransactionInfo>):List<ListItem>{
        val sortedMap = groupDataIntoHashMap(listOfItems)
        val listOfTransactionHeaders:MutableList<ListItem> = ArrayList()
        if (sortedMap != null) {

            sortedMap.forEach { mapItem ->
                listOfTransactionHeaders.add(TransactionDateHeader(mapItem.key))
                mapItem.value.forEach { transactionDetailItem -> listOfTransactionHeaders.add(transactionDetailItem) }
            }
        }
        return listOfTransactionHeaders
    }
    private fun groupDataIntoHashMap(listOfItems : List<TransactionInfo>) : LinkedHashMap<String, MutableList<ListItem>>? {
        val groupedHashMap : LinkedHashMap<String, MutableList<ListItem>> = LinkedHashMap()
        for (eachItem in listOfItems) {
            val hashMapKey : String = formatDate(eachItem.transactionDate)
            if (groupedHashMap.containsKey(hashMapKey)) {
                // The key is already in the HashMap; add the pojo object
                // against the existing key.
                groupedHashMap[hashMapKey]?.add(eachItem)
            } else {
                // The key is not there in the HashMap; create a new key-value pair
                val list : MutableList<ListItem> = ArrayList()
                list.add(eachItem)
                groupedHashMap[hashMapKey] = list
            }
        }
        //groupedHashMap.toSortedMap()
        return groupedHashMap
    }


    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun formatDate(source:String):String{

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",Locale.getDefault())
        return SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(simpleDateFormat.parse(source))

    }

}