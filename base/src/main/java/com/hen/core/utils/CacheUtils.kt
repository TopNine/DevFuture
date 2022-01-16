package com.hen.core.utils

import android.annotation.SuppressLint
import android.content.Context
import com.example.core.R
import com.hen.core.BaseApplication

@SuppressLint("StaticFieldLeak")
object CacheUtils {
    private val context = BaseApplication.currentApplication

    private val SP =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    fun save(key: String?, value: String?) = SP.edit().putString(key, value).apply()

    operator fun get(key: String?) = SP.getString(key, null)

}