package com.riocallos.itunes.base

import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.multidex.MultiDex
import androidx.room.Room
import com.riocallos.itunes.database.AppDatabase
import com.riocallos.itunes.databinding.BaseDataBindingComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Base class that extends DaggerApplication
 * to handle app initialization.
 *
 */
class BaseApplication: DaggerApplication() {

    override fun onCreate() {

        super.onCreate()

        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "itunes.db").build()

        DataBindingUtil.setDefaultComponent(BaseDataBindingComponent())

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerBaseComponent.builder().application(this).build()
    }

}