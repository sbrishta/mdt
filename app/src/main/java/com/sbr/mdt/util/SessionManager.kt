package com.sbr.mdt.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.sbr.mdt.R

/**
 * Session manager to save and fetch data from SharedPreferences
 */
class SessionManager (context: Context) {
//    @SuppressLint("NewApi")
//    var masterKeyAlias : String = getOrCreate(AES256_GCM_SPEC)

//    private var prefs: SharedPreferences = EncryptedSharedPreferences.create(
//        "secret_shared_prefs",
//        masterKeyAlias,
//        context,
//        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//    )
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = Constants.AUTH_KEY
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }
}