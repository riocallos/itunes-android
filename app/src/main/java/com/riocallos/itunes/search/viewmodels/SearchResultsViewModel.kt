package com.riocallos.itunes.search.viewmodels

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.riocallos.itunes.api.ApiCallback
import com.riocallos.itunes.database.AppDatabase
import com.riocallos.itunes.models.SearchResponse
import com.riocallos.itunes.models.SearchResult
import com.riocallos.itunes.search.SearchResultsRecyclerViewAdapter
import com.riocallos.itunes.search.SearchResultsRepository
import com.riocallos.itunes.utils.AppLogger
import com.riocallos.itunes.utils.CacheUtil
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

/**
 * Two way data binding ViewModel for displaying and updating notifications
 * list on property change.
 *
 */
class SearchResultsViewModel @Inject constructor(application: Application): AndroidViewModel(application) {

    lateinit var adapter: SearchResultsRecyclerViewAdapter

    @Inject
    lateinit var searchResultsRepository: SearchResultsRepository

    private val disposable = CompositeDisposable()

    var searchResults: ObservableField<List<SearchResult>> = ObservableField()

    init {
        searchResults.set(listOf())
    }

    /**
     * Get search results from API.
     *
     */
    fun search(term: String, country: String, media: String) {

        disposable.add(searchResultsRepository.search(term, country, media, object : ApiCallback<SearchResponse> {
            override fun onStart() {
                val subscriber = AppDatabase.invoke(adapter.context).searchResultDao().getAll().subscribe(
                    { it ->
                        searchResults.set(it.toList())
                    }, { error -> AppLogger.error(error.message!!)
                        searchResults.get()?.let {
                            if(it.isEmpty()) {
                                searchResults.set(listOf())
                                searchResults.notifyChange()
                            }
                        }
                    } )
                disposable.add(subscriber)
            }

            override fun onSuccess(obj: SearchResponse?) {
                obj?.let {
                    if(it.results.isNotEmpty()) {
                        CacheUtil.invoke(getApplication()).saveRetrieved(Date().time.toString())
                        val subscriber = AppDatabase.invoke(getApplication()).searchResultDao().insertAll(it.results).subscribe()
                        disposable.add(subscriber)
                    }
                    searchResults.set(obj.results.toList())
                    searchResults.notifyChange()
                }
            }

            override fun onError(error: String?) {
                error?.let{
                    AppLogger.error(it)
                }
                searchResults.get()?.let {
                    if(it.isEmpty()) {
                        searchResults.set(listOf())
                        searchResults.notifyChange()
                    }
                }
            }

        }))

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}