package com.riocallos.itunes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.riocallos.itunes.search.viewmodels.SearchHeaderViewModel
import com.riocallos.itunes.search.viewmodels.SearchResultViewModel
import com.riocallos.itunes.search.viewmodels.SearchResultsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SearchResultsViewModel::class)
    internal abstract fun searchResultsViewModel(viewModel: SearchResultsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchHeaderViewModel::class)
    internal abstract fun searchHeaderViewModel(viewModel: SearchHeaderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchResultViewModel::class)
    internal abstract fun searchResultViewModel(viewModel: SearchResultViewModel): ViewModel

}