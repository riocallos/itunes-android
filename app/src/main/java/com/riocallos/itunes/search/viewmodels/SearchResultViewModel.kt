package com.riocallos.itunes.search.viewmodels

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.riocallos.itunes.database.AppDatabase
import com.riocallos.itunes.models.SearchResult
import com.riocallos.itunes.utils.AppLogger
import com.riocallos.itunes.utils.CacheUtil
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Two way data binding ViewModel for displaying search result
 * details on property change.
 *
 */
class SearchResultViewModel @Inject constructor(application: Application): AndroidViewModel(application) {

    private val disposable = CompositeDisposable()

    var searchResult: SearchResult = SearchResult()

    fun get() {
        val id = CacheUtil.invoke(getApplication()).getSearchResult()
        if(id.isNotEmpty()) {
            val subscriber = AppDatabase.invoke(getApplication()).searchResultDao()
                .get(id).subscribe(
                { it ->
                    it?.let {
                        searchResult = it
                    }
                }, { error -> AppLogger.error(error.message!!) })

            disposable.add(subscriber)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }



}