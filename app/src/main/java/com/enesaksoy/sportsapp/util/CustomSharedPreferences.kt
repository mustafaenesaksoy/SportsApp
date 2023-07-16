package com.enesaksoy.sportsapp.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

class CustomSharedPreferences {

    companion object{
        private val CUSTOM_PREFERENCES = "Preferences_time"
        private var SharedPreferences : SharedPreferences? = null
        @Volatile private var instance :CustomSharedPreferences? = null
        private val lock = Any()
        operator fun invoke(context : Context) : CustomSharedPreferences = instance ?: synchronized(lock){
            instance ?: makeCustomSharedPreferences(context).also {
                instance = it
            }
        }

        private fun makeCustomSharedPreferences(context : Context) : CustomSharedPreferences{
            SharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }
    }
    fun savetime(time : Long){
        SharedPreferences?.edit(commit = true){
            putLong(CUSTOM_PREFERENCES,time)
        }
    }

    fun gettime() = SharedPreferences?.getLong(CUSTOM_PREFERENCES,0)
}