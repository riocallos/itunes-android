package com.riocallos.itunes.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Utility class for app settings.
 *
 */
class CacheUtil(context: Context) {

    var cacheSharedPreferences: SharedPreferences

    companion object {

        @Volatile private var instance: CacheUtil? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: getInstance(context).also { instance = it}
        }

        private fun getInstance(context: Context) = CacheUtil(context)

        const val CACHE = "Cache"
        const val RETRIEVED = "Retrieved"
        const val SEARCH_RESULT = "SearchResult"

    }

    init {
        cacheSharedPreferences = context.getSharedPreferences(CACHE, Context.MODE_PRIVATE)
    }

    fun saveRetrieved(retrieved: String) {
        val editor = cacheSharedPreferences.edit()
        editor.putString(RETRIEVED, retrieved)
        editor.apply()
    }

    fun getRetrieved(): String {
        val retrieved = cacheSharedPreferences.getString(RETRIEVED, "")
        retrieved?.let {
            return it
        }
        return ""
    }

    fun saveSearchResult(searchResult: String) {
        val editor = cacheSharedPreferences.edit()
        editor.putString(SEARCH_RESULT, searchResult)
        editor.apply()
    }

    fun getSearchResult(): String {
        val searchResult = cacheSharedPreferences.getString(SEARCH_RESULT, "")
        searchResult?.let {
            return it
        }
        return ""
    }

}
