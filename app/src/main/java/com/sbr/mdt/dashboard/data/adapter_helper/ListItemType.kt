package com.sbr.mdt.dashboard.data.adapter_helper

abstract class ListItem {
    abstract val type : Int

    companion object {
        const val TYPE_DATE = 0
        const val TYPE_GENERAL = 1
    }
}