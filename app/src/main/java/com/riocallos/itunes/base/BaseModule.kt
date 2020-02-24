package com.riocallos.itunes.base

import com.riocallos.itunes.api.ApiService
import com.riocallos.itunes.search.views.SearchResultsActivity
import com.riocallos.itunes.search.SearchResultsRepository
import com.riocallos.itunes.search.views.SearchResultActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BaseModule {

    @ContributesAndroidInjector
    abstract fun contributeSearchResultsActivity(): SearchResultsActivity

    @ContributesAndroidInjector
    abstract fun contributeSearchResultActivity(): SearchResultActivity

    @ContributesAndroidInjector
    abstract fun contributeApiService(): ApiService

    @ContributesAndroidInjector
    abstract fun contributeSearchResultsRepository(): SearchResultsRepository

}