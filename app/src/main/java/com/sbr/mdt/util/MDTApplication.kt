package com.sbr.mdt.util

import android.app.Application
import java.lang.Appendable

class MDTApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SessionManager.with(this)
        NetworkStatus.with(this)
    }
}