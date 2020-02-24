package com.riocallos.itunes.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.riocallos.itunes.database.daos.SearchResultDao
import com.riocallos.itunes.models.SearchResult

@Database(version = 1, exportSchema = false, entities = [(SearchResult::class)])
abstract class AppDatabase : RoomDatabase() {

    abstract fun searchResultDao(): SearchResultDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "itunes.db")
            .allowMainThreadQueries()
            .build()
    }

}
