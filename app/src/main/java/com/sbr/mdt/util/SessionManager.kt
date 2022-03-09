package com.sbr.mdt.util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.sbr.mdt.R

/**
 * Session manager to save and fetch data from SharedPreferences
 */
object SessionManager  {
//    @SuppressLint("NewApi")
//    var masterKeyAlias : String = getOrCreate(AES256_GCM_SPEC)

//    private var prefs: SharedPreferences = EncryptedSharedPreferences.create(
//        "secret_shared_prefs",
//        masterKeyAlias,
//        context,
//        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//    )
   // private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    //Shared Preference field used to save and retrieve JSON string
    lateinit var preferences: SharedPreferences


    /**
     * Call this first before retrieving or saving object.
     *
     * @param application Instance of application class
     */
    fun with(application: Application) {
        preferences = application.getSharedPreferences(
            application.getString(R.string.app_name), Context.MODE_PRIVATE)
    }


    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String) {
        val editor = preferences.edit()
        editor.putString(Constants.AUTH_KEY, token)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return preferences.getString(Constants.AUTH_KEY, null)
    }
    /**
     * Saves object into the Preferences.
     *
     * @param `object` Object of model class (of type [T]) to save
     * @param key Key with which Shared preferences to
     **/
    fun <T> put(`object`: T, key: String) {
        //Convert object to JSON String.
        val jsonString = GsonBuilder().create().toJson(`object`)
        //Save that String in SharedPreferences
        preferences.edit().putString(key, jsonString).apply()
    }

    /**
     * Used to retrieve object from the Preferences.
     *
     * @param key Shared Preference key with which object was saved.
     **/
    inline fun <reified T> get(key: String): T? {
        //We read JSON String which was saved.
        val value = preferences.getString(key, null)
        //JSON String was found which means object can be read.
        //We convert this JSON String to model object. Parameter "c" (of
        //type Class < T >" is used to cast.
        return GsonBuilder().create().fromJson(value, T::class.java)
    }
    fun clearData(){
        preferences.edit().clear().apply()
    }
}